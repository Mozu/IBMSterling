package com.mozu.sterling.service;

import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;
import java.util.Map.Entry;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mozu.api.MozuApiContext;
import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.jmsUtil.TenantSiteMessageListener;
import com.mozu.sterling.model.Setting;

/**
 * Receives jms messages for processing. A new class is needed so that it can
 * create a unique durable subscription. Not thread safe as it is tied to a
 * tenant.
 *
 */
public class SterlingToMozuInventoryMessageListener implements TenantSiteMessageListener {
	private static final Logger logger = LoggerFactory
			.getLogger(SterlingToMozuInventoryMessageListener.class);
	private static JAXBContext jaxbContext = null;

	private Integer tenantId;

	private Set<Integer> siteSet;

	private InventoryService inventoryService;

	private ConfigHandler configHandler;

	static {
		try {
			jaxbContext = JAXBContext
					.newInstance(com.mozu.sterling.model.inventory.AvailabilityChange.class);
		} catch (JAXBException jaxbEx) {
			logger.error("Error getting jaxb context.");
		}
	}

	public SterlingToMozuInventoryMessageListener(Integer tenantId,
			Integer siteId, ConfigHandler configHandler,
			InventoryService inventoryService) {
		this.tenantId = tenantId;
		this.siteSet = new HashSet<Integer>();
		this.siteSet.add(siteId);
		this.configHandler = configHandler;
		this.inventoryService = inventoryService;
	}

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {
				Setting setting = configHandler.getSetting(tenantId);

				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader messageReader = new StringReader(
						((TextMessage) message).getText());
				com.mozu.sterling.model.inventory.AvailabilityChange sterlingInventoryChange = (com.mozu.sterling.model.inventory.AvailabilityChange) unmarshaller
						.unmarshal(messageReader);

				Set<Integer> theSiteId = new HashSet<Integer>();

				for (Entry<String, String> entry : setting.getSiteMap().entrySet()) {
					if (entry.getValue().equals(sterlingInventoryChange.getInventoryItem().getInventoryOrganizationCode())) {
						theSiteId.add(Integer.valueOf(entry.getKey()));
					}
				}

				theSiteId.retainAll(siteSet);

				if (!theSiteId.isEmpty() && theSiteId.size() == 1) {
				inventoryService.updateInventory(new MozuApiContext(tenantId,
						theSiteId.iterator().next()), configHandler.getSetting(tenantId),
						sterlingInventoryChange);
				} else {
					logger.warn("No sites or more than one site matched for the sterling order.");
				}

			} catch (JMSException e) {
				logger.error("Failed to read message.", e);
			} catch (JAXBException jaxbEx) {
				logger.error("Failed to unmarshall.", jaxbEx);
			} catch (Exception mozuEx) {
				logger.error("Failed to complete mozu call.", mozuEx);
			}
		} else {
			logger.info("I don't know what kind of jms message this is.");
			logger.info(message.getClass().getName());
		}
	}

	@Override
	public void addToSites(Integer siteId) {
		this.siteSet.add(siteId);
	}
}

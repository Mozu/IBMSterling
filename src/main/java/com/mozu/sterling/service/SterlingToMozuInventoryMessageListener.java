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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.mozu.api.ApiContext;
import com.mozu.api.MozuApiContext;
import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.jmsUtil.TenantSiteMessageListener;
import com.mozu.sterling.model.Setting;

/**
 * Receives jms messages for processing. A new class is needed so that it can
 * create a unique durable subscription. Not thread safe as it is tied to a
 * tenant.
 *
 * <pre>
 * <InventoryItem ItemID="AuroraWMDRS-018" ProductClass="" UnitOfMeasure="EACH">
 *    <AvailabilityChanges>
 *        <AvailabilityChange AgentCriteriaId="REALTIME_ATP_MONITOR_OP1"
 *            AlertLevel="0" AlertQuantity="2147483647.00"
 *            AlertRaisedOn="2016-04-05T07:50:50-04:00"
 *            AlertType="REALTIME_ONHAND"
 *            FirstFutureAvailableDate="2500-01-01"
 *            FutureAvailableDate="2500-01-01"
 *            FutureAvailableQuantity="0.00" MonitorOption="1" Node=""
 *            OnhandAvailableDate="2016-04-05" OnhandAvailableQuantity="400.00"/>
 *    </AvailabilityChanges>
 * </InventoryItem>
 * </pre>
 */
public class SterlingToMozuInventoryMessageListener implements
		TenantSiteMessageListener {
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
					.newInstance(com.mozu.sterling.model.inventory.InventoryItem.class);
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
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder;
				Document document = null;
				builder = factory.newDocumentBuilder();
				document = builder.parse(new InputSource(new StringReader(
						((TextMessage) message).getText())));
				com.mozu.sterling.model.inventory.InventoryItem sterlingInventoryItem = (com.mozu.sterling.model.inventory.InventoryItem) unmarshaller
						.unmarshal(document);

				String organizationCode = sterlingInventoryItem
						.getOrganizationCode();

				Set<Integer> theSiteId = new HashSet<Integer>();

				for (Entry<String, String> entry : setting.getSiteMap()
						.entrySet()) {
					if (entry.getValue().equals(organizationCode)) {
						theSiteId.add(Integer.valueOf(entry.getKey()));
					}
				}

				if (theSiteId.isEmpty() && setting.getSiteMap() != null
						&& !setting.getSiteMap().isEmpty()) {
					theSiteId.add(Integer.valueOf(setting.getSiteMap().keySet()
							.iterator().next()));
				}

				theSiteId.retainAll(siteSet);

				if (!theSiteId.isEmpty() && theSiteId.size() == 1) {
					ApiContext mozuApiContext = new MozuApiContext(tenantId,
							theSiteId.iterator().next());

					if (sterlingInventoryItem.getAvailabilityChanges() != null) {
						for (com.mozu.sterling.model.inventory.AvailabilityChange availabilityChange : sterlingInventoryItem
								.getAvailabilityChanges()
								.getAvailabilityChange()) {
							inventoryService.updateInventory(mozuApiContext,
									configHandler.getSetting(tenantId),
									sterlingInventoryItem.getItemID(),
									availabilityChange);
						}
					}
				} else {
					logger.warn("No sites or more than one site matched for the sterling inventory adjustment.");
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

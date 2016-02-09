package com.mozu.sterling.service;

import java.io.StringReader;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mozu.api.MozuApiContext;
import com.mozu.sterling.handler.ConfigHandler;

/**
 * Receives jms messages for processing. A new class is needed so that it can
 * create a unique durable subscription. Not thread safe as it is tied to a
 * tenant.
 *
 */
public class SterlingToMozuInventoryMessageListener implements MessageListener {
	private static final Logger logger = LoggerFactory
			.getLogger(SterlingToMozuInventoryMessageListener.class);
	private static JAXBContext jaxbContext = null;

	private Integer tenantId;

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
			ConfigHandler configHandler, InventoryService inventoryService) {
		this.tenantId = tenantId;
		this.configHandler = configHandler;
		this.inventoryService = inventoryService;
	}

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {

				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader messageReader = new StringReader(
						((TextMessage) message).getText());
				com.mozu.sterling.model.inventory.AvailabilityChange sterlingInventoryChange = (com.mozu.sterling.model.inventory.AvailabilityChange) unmarshaller
						.unmarshal(messageReader);

				inventoryService.updateInventory(new MozuApiContext(),
						configHandler.getSetting(tenantId),
						sterlingInventoryChange);

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
}

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.JmsException;
import org.springframework.stereotype.Component;

import com.mozu.api.MozuApiContext;
import com.mozu.sterling.handler.ConfigHandler;

/**
 * Receives jms messages for processing.  Not thread safe as it is tied to a tenant.
 *
 */
@Component(value="newSterlingToMozuOrderMessageListener")
@Scope("prototype")
public class NewSterlingToMozuOrderMessageListener implements MessageListener {
	private static final Logger logger = LoggerFactory
			.getLogger(NewSterlingToMozuOrderMessageListener.class);
	private static JAXBContext jaxbContext = null;

	private Integer tenantId;

	@Autowired
	OrderService orderService;

	@Autowired
    ConfigHandler configHandler;


	static {
		try {
		jaxbContext = JAXBContext.newInstance(com.mozu.sterling.model.order.Order.class);
		} catch (JAXBException jaxbEx) {
			logger.error("Error getting jaxb context.");
		}
	}

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {

				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader messageReader = new StringReader(((TextMessage) message).getText());
				com.mozu.sterling.model.order.Order sterlingOrder = (com.mozu.sterling.model.order.Order) unmarshaller
						.unmarshal(messageReader);

				orderService.importSterlingOrder(new MozuApiContext(), configHandler.getSetting(tenantId), sterlingOrder);

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

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}
}

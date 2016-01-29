package com.mozu.sterling.service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.mozu.sterling.jmsUtil.JmsConnectionCache;

/**
 * A service that sends and receives JMS messages.
 *
 */
@Component
public class MessageService {
	@Autowired
	JmsConnectionCache jmsConnectionCache;

//	@Autowired
//	private JmsTemplate jmsTemplate;
//	@Value("${queue}")
//	private String destination;
//
//	/**
//	 * Sends a message to a queue.
//	 *
//	 * @param text
//	 *            Message text.
//	 */
//	public void sendMessage(final String text) {
//		jmsTemplate.send(destination, new MessageCreator() {
//			public Message createMessage(Session session) throws JMSException {
//				return session.createTextMessage(text);
//			}
//		});
//	}

	/**
	 * Receives a message from a queue.
	 *
	 * @return Message text.
	 * @throws JMSException
	 */
	public String readMessage(Integer tenantId) throws Exception {
		String message = null;
		JmsTemplate jmsTemplate = jmsConnectionCache.getTemplate(tenantId);

		Message msg = jmsTemplate.receive(jmsConnectionCache
				.getInboundDestination(tenantId));
		if (msg instanceof TextMessage) {
			message = ((TextMessage) msg).getText();
		}

		return message;
	}

	/**
	 * Enables listening via a listener
	 *
	 * @param tenantId
	 * @return True if the listener is started, otherwise false.
	 * @throws Exception
	 */
	public boolean toggleMessageQueueListener(Integer tenantId)
			throws Exception {
		return jmsConnectionCache.toggleListener(tenantId);
	}
}
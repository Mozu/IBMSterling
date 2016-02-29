package com.mozu.sterling.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
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

	/**
	 * Receives a message from a queue.
	 *
	 * @return Message text.
	 * @throws JMSException
	 */
	public String readMessage(Integer tenantId, Integer siteId) throws Exception {
		String message = null;
		JmsTemplate jmsTemplate = jmsConnectionCache.getTemplate(tenantId, siteId);

		Message msg = jmsTemplate.receive(jmsConnectionCache
				.getDefaultDestination(tenantId, siteId));
		if (msg instanceof TextMessage) {
			message = ((TextMessage) msg).getText();
		}

		return message;
	}

	/**
	 * Enables listening via a listener
	 *
	 * @param tenantId
	 * @param siteId
	 * @return True if the listener is started, otherwise false.
	 * @throws Exception
	 */
	public boolean turnOnMessageQueueListener(Integer tenantId, Integer siteId)
			throws Exception {
		return jmsConnectionCache.turnOnListener(tenantId, siteId);
	}
    /**
     * Disables listening via a listener
     *
     * @param tenantId
     * @param siteId
     * @return True if the listener is started, otherwise false.
     * @throws Exception
     */
    public boolean turnOffMessageQueueListener(Integer tenantId, Integer siteId)
            throws Exception {
        return jmsConnectionCache.turnOffListener(tenantId, siteId);
    }}
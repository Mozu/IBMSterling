package com.mozu.sterling.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.JmsException;
import org.springframework.stereotype.Component;

/**
 * Receives jms messages for processing. Must be thread safe.
 *
 */
@Component(value="defaultMessageListener")
public class DefaultMessageListener implements MessageListener {
	private static final Logger logger = LoggerFactory
			.getLogger(DefaultMessageListener.class);

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {
				logger.info(((TextMessage) message).getText());
			} catch (JMSException e) {
				logger.error("Failed to read message.", e);
			}
		}
	}
}

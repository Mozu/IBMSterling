package com.mozu.sterling.jmsUtil;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageListener;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * Contains jms types needed to read and write to a single location.
 *
 */
public class JmsResource {

	private JmsTemplate jmsTemplate;
	private ConnectionFactory connectionFactory;
	private Destination defaultDestination;
	private DefaultMessageListenerContainer listenerContainer;

	public JmsResource(ConnectionFactory connectionFactory,
			Destination destination) {
		jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(connectionFactory);
		jmsTemplate.setDefaultDestination(destination);

		this.connectionFactory = connectionFactory;
		this.defaultDestination = destination;
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void startListening(MessageListener messageListener) {
		if (listenerContainer == null) {
			listenerContainer = new DefaultMessageListenerContainer();
			listenerContainer.setConnectionFactory(connectionFactory);
			listenerContainer.setDestination(defaultDestination);
			listenerContainer.setAutoStartup(false);
		}

		if (!listenerContainer.isRunning()) {
			listenerContainer.setMessageListener(messageListener);
			listenerContainer.start();
		}
	}

	public void stopListening() {
		if (listenerContainer != null && listenerContainer.isRunning()) {
			listenerContainer.stop();
		}
	}

	public boolean isListening() {
		return listenerContainer != null && listenerContainer.isRunning();
	}

	public void close() {
		// Interfaces don't expose explicit release of connections.
		jmsTemplate = null;
		connectionFactory = null;
		defaultDestination = null;

		if (listenerContainer != null) {
			listenerContainer.shutdown();
		}
	}
}

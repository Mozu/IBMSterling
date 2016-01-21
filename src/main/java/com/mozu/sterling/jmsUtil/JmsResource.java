package com.mozu.sterling.jmsUtil;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import org.springframework.jms.core.JmsTemplate;

/**
 * Contains jms types needed to read and write to a single location.
 *
 */
public class JmsResource {

	private JmsTemplate jmsTemplate;
	private ConnectionFactory connectionFactory;
	private Destination defaultDestination;

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

	public void close() {
		// Interfaces don't expose explicit release of connections.
		jmsTemplate = null;
		connectionFactory = null;
		defaultDestination = null;
	}
}

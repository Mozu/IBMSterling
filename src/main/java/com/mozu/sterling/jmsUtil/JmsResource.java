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
	private Destination readDestination;
	private DefaultMessageListenerContainer listenerContainer;

	public JmsResource(ConnectionFactory connectionFactory,
			Destination destination, Destination readDestination, MessageListener listener) {
		jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(connectionFactory);
		jmsTemplate.setDefaultDestination(destination);

		this.connectionFactory = connectionFactory;
		this.defaultDestination = destination;
		this.readDestination = readDestination;

		listenerContainer = new DefaultMessageListenerContainer();
		listenerContainer.setConnectionFactory(connectionFactory);
		listenerContainer
				.setDestination(readDestination == null ? defaultDestination
						: readDestination);
		listenerContainer.setAutoStartup(false);
		listenerContainer.setMessageListener(listener);
	}

	public JmsResource(ConnectionFactory connectionFactory, Destination destination, MessageListener listener) {
		this(connectionFactory, destination, null, listener);
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public Destination getReadDestination() {
		return readDestination == null ? defaultDestination : readDestination;
	}

	public void startListening() {
		if (!listenerContainer.isRunning()) {
			listenerContainer.start();
		}
	}

	public void stopListening() {
		if (listenerContainer.isRunning()) {
			listenerContainer.stop();
		}
	}

	public boolean isListening() {
		return listenerContainer != null && listenerContainer.isRunning();
	}

	public void close() {
		if (listenerContainer != null) {
			listenerContainer.shutdown();
		}

		// Interfaces don't expose explicit release of connections.
		jmsTemplate = null;
		connectionFactory = null;
		defaultDestination = null;
		readDestination = null;
	}
}

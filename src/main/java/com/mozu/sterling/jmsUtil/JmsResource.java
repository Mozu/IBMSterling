package com.mozu.sterling.jmsUtil;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

/**
 * Contains jms types needed to read and write to a single location.
 *
 */
public class JmsResource {
	private static final Logger logger = LoggerFactory.getLogger(JmsResource.class);

	private JmsTemplate jmsTemplate;
	private ConnectionFactory connectionFactory;
	private Destination defaultDestination;
	private Destination readDestination;
	private DefaultMessageListenerContainer listenerContainer;

	public JmsResource(ConnectionFactory connectionFactory,
			Destination destination, Destination readDestination,
			MessageListener listener, DestinationTypeEnum destinationType) {
		jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(connectionFactory);
		jmsTemplate.setDefaultDestination(destination);

		this.connectionFactory = connectionFactory;
		this.defaultDestination = destination;
		this.readDestination = readDestination == null ? defaultDestination
				: readDestination;

		listenerContainer = new DefaultMessageListenerContainer();
		listenerContainer.setConnectionFactory(connectionFactory);
		listenerContainer.setDestination(this.readDestination);
		listenerContainer
				.setPubSubDomain(destinationType == DestinationTypeEnum.TOPIC);
		listenerContainer.setAutoStartup(false);
		listenerContainer.setCacheLevel(DefaultMessageListenerContainer.CACHE_NONE);
		listenerContainer.setMessageListener(listener);
	}

	public JmsResource(ConnectionFactory connectionFactory,
			Destination destination, MessageListener listener,
			DestinationTypeEnum destinationType) {
		this(connectionFactory, destination, null, listener, destinationType);
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
			logger.info("JMS message listener container started!");
		}
	}

	public void stopListening() {
		if (listenerContainer.isRunning()) {
			listenerContainer.stop();
			logger.info("JMS message listener container stopped!");
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

package com.mozu.sterling.jmsUtil;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;

import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.stereotype.Component;

import com.ibm.websphere.sib.api.jms.JmsConnectionFactory;
import com.ibm.websphere.sib.api.jms.JmsFactoryFactory;
import com.mozu.sterling.model.Setting;

/**
 * In the simplest websphere install, it has a default message queue provider
 * that is accessed directly by a known provider endpoint.  Uses SIB jars.
 *
 */
@Component(value = "directJmsStrategy")
public class DirectWebsphereJmsStrategy implements JmsConnectionStrategy {

	@Override
	public ConnectionFactory getConnectionFactory(Setting setting)
			throws JMSException {

		JmsFactoryFactory jmsFactoryFactory = JmsFactoryFactory.getInstance();
		JmsConnectionFactory connectionFactory = jmsFactoryFactory
				.createConnectionFactory();
		connectionFactory.setProviderEndpoints(setting.getProviderEndpoint());
		connectionFactory.setBusName(setting.getBusName());

		return new SingleConnectionFactory(connectionFactory);
	}

	@Override
	public Destination getOutboundDestination(Setting setting) throws JMSException {
		JmsFactoryFactory jmsFactoryFactory = JmsFactoryFactory.getInstance();

		DestinationTypeEnum destinationType = DestinationTypeEnum.from(setting
				.getDestinationType());
		Destination destination = null;

		switch (destinationType) {
		case QUEUE:
			destination = jmsFactoryFactory.createQueue(setting
					.getDestinationName());
			break;
		case TOPIC:
			destination = jmsFactoryFactory.createTopic(setting
					.getDestinationName());
			break;
		}

		return destination;
	}

	@Override
	public Destination getInboundDestination(Setting setting)
			throws JMSException {
		JmsFactoryFactory jmsFactoryFactory = JmsFactoryFactory.getInstance();

		DestinationTypeEnum destinationType = DestinationTypeEnum.from(setting
				.getDestinationType());
		Destination destination = null;

		switch (destinationType) {
		case QUEUE:
			destination = jmsFactoryFactory.createQueue(setting
					.getInboundDestinationName());
			break;
		case TOPIC:
			destination = jmsFactoryFactory.createTopic(setting
					.getInboundDestinationName());
			break;
		}

		return destination;
	}
}

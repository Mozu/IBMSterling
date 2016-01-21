package com.mozu.sterling.jmsUtil;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;

import com.mozu.sterling.model.Setting;

public interface JmsConnectionStrategy {
	ConnectionFactory getConnectionFactory(Setting setting) throws JMSException;
	Destination getDestination(Setting setting) throws JMSException;
}
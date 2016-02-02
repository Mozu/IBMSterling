package com.mozu.sterling.jmsUtil;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageListener;

/**
 * A convenient way of passing values to a jms resource class.
 *
 */
public class JmsResourceSetting {
	private ConnectionFactory connectionFactory;
	private Destination createOrderDestination;
	private Destination updateOrderDestination;
	private MessageListener createOrderMessageListener;
	private MessageListener updateOrderMessageListener;
	private DestinationTypeEnum destinationType;

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	public Destination getCreateOrderDestination() {
		return createOrderDestination;
	}
	public void setCreateOrderDestination(Destination createOrderDestination) {
		this.createOrderDestination = createOrderDestination;
	}
	public Destination getUpdateOrderDestination() {
		return updateOrderDestination;
	}
	public void setUpdateOrderDestination(Destination updateOrderDestination) {
		this.updateOrderDestination = updateOrderDestination;
	}
	public MessageListener getCreateOrderMessageListener() {
		return createOrderMessageListener;
	}
	public void setCreateOrderMessageListener(
			MessageListener createOrderMessageListener) {
		this.createOrderMessageListener = createOrderMessageListener;
	}
	public MessageListener getUpdateOrderMessageListener() {
		return updateOrderMessageListener;
	}
	public void setUpdateOrderMessageListener(
			MessageListener updateOrderMessageListener) {
		this.updateOrderMessageListener = updateOrderMessageListener;
	}
	public DestinationTypeEnum getDestinationType() {
		return destinationType;
	}
	public void setDestinationType(DestinationTypeEnum destinationType) {
		this.destinationType = destinationType;
	}
}

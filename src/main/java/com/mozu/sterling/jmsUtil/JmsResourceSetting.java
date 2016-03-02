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
	private Destination inventoryDestination;
	private TenantSiteMessageListener createOrderMessageListener;
	private TenantSiteMessageListener updateOrderMessageListener;
	private TenantSiteMessageListener inventoryMessageListener;
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
	public TenantSiteMessageListener getCreateOrderMessageListener() {
		return createOrderMessageListener;
	}
	public void setCreateOrderMessageListener(
			TenantSiteMessageListener createOrderMessageListener) {
		this.createOrderMessageListener = createOrderMessageListener;
	}
	public TenantSiteMessageListener getUpdateOrderMessageListener() {
		return updateOrderMessageListener;
	}
	public void setUpdateOrderMessageListener(
			TenantSiteMessageListener updateOrderMessageListener) {
		this.updateOrderMessageListener = updateOrderMessageListener;
	}
	public DestinationTypeEnum getDestinationType() {
		return destinationType;
	}
	public void setDestinationType(DestinationTypeEnum destinationType) {
		this.destinationType = destinationType;
	}
	public Destination getInventoryDestination() {
		return inventoryDestination;
	}
	public void setInventoryDestination(Destination inventoryDestination) {
		this.inventoryDestination = inventoryDestination;
	}
	public MessageListener getInventoryMessageListener() {
		return inventoryMessageListener;
	}
	public void setInventoryMessageListener(TenantSiteMessageListener inventoryMessageListener) {
		this.inventoryMessageListener = inventoryMessageListener;
	}
}

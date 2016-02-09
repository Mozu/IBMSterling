package com.mozu.sterling.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mozu.sterling.jmsUtil.DestinationTypeEnum;
import com.mozu.sterling.jmsUtil.JmsConnectionStrategyEnum;

/**
 * This object contains the configuration items needed to connect and synchronize with the Sterling system.
 * @author bob_hewett
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Setting {
    protected String id;
    protected String sterlingUrl;
    protected String sterlingUserId;
    protected String sterlingPassword;
    protected String sterlingEnterpriseCode;
    protected Map<String, String> siteMap;
    protected Map<String, String> locationMap;
    protected Map<String, String> shipMethodMap;
    // jms settings
    protected String connectionStrategy = JmsConnectionStrategyEnum.DIRECT.strategyName();
    protected String providerEndpoint;
    protected String busName;
    protected String destinationType = DestinationTypeEnum.QUEUE.destinationName();
    protected String subscriptionHome;
    protected String createOrderDestinationName;
    protected String updateOrderDestinationName;
    protected String inventoryDestinationName;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getSterlingUrl() {
        return sterlingUrl;
    }
    public void setSterlingUrl(String sterlingUrl) {
        this.sterlingUrl = sterlingUrl;
    }
    public String getSterlingUserId() {
        return sterlingUserId;
    }
    public void setSterlingUserId(String sterlingUserId) {
        this.sterlingUserId = sterlingUserId;
    }
    public String getSterlingPassword() {
        return sterlingPassword;
    }
    public void setSterlingPassword(String sterlingPassword) {
        this.sterlingPassword = sterlingPassword;
    }
    public String getSterlingEnterpriseCode() {
        return sterlingEnterpriseCode;
    }
    public void setSterlingEnterpriseCode(String sterlingEnterpriseCode) {
        this.sterlingEnterpriseCode = sterlingEnterpriseCode;
    }
    public Map<String, String> getSiteMap() {
        return siteMap;
    }
    public void setSiteMap(Map<String, String> siteMap) {
        this.siteMap = siteMap;
    }
    public Map<String, String> getLocationMap() {
        return locationMap;
    }
    public void setLocationMap(Map<String, String> locationMap) {
        this.locationMap = locationMap;
    }
    public Map<String, String> getShipMethodMap() {
        return shipMethodMap;
    }
    public void setShipMethodMap(Map<String, String> shipMethodMap) {
        this.shipMethodMap = shipMethodMap;
    }
	public String getConnectionStrategy() {
		return connectionStrategy;
	}
	public void setConnectionStrategy(String connectionStrategy) {
		this.connectionStrategy = connectionStrategy;
	}
	public String getProviderEndpoint() {
		return providerEndpoint;
	}
	public void setProviderEndpoint(String providerEndpoint) {
		this.providerEndpoint = providerEndpoint;
	}
	public String getBusName() {
		return busName;
	}
	public void setBusName(String busName) {
		this.busName = busName;
	}
	public String getDestinationType() {
		return destinationType;
	}
	public void setDestinationType(String destinationType) {
		this.destinationType = destinationType;
	}
	public String getCreateOrderDestinationName() {
		return createOrderDestinationName;
	}
	public void setCreateOrderDestinationName(String destinationName) {
		this.createOrderDestinationName = destinationName;
	}
	public String getUpdateOrderDestinationName() {
		return updateOrderDestinationName;
	}
	public void setUpdateOrderDestinationName(String destinationName) {
		this.updateOrderDestinationName = destinationName;
	}
	public String getSubscriptionHome() {
		return subscriptionHome;
	}
	public void setSubscriptionHome(String subscriptionHome) {
		this.subscriptionHome = subscriptionHome;
	}
	public String getInventoryDestinationName() {
		return inventoryDestinationName;
	}
	public void setInventoryDestinationName(String inventoryDestinationName) {
		this.inventoryDestinationName = inventoryDestinationName;
	}
}

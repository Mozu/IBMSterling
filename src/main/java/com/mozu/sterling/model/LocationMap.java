package com.mozu.sterling.model;

public class LocationMap {
    protected String locationCode;
    protected String shippingNodeCode;
    protected String shippingNodeName;
    
    public LocationMap() {
    }

    public LocationMap (String shippingNodeCode, String shippingNodeName, String locationCode) {
        this.shippingNodeCode = shippingNodeCode;
        this.shippingNodeName = shippingNodeName;
        this.locationCode = locationCode;
    }
    
    public String getLocationCode() {
        return locationCode;
    }
    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }
    public String getShippingNodeCode() {
        return shippingNodeCode;
    }
    public void setShippingNodeCode(String shippingNodeCode) {
        this.shippingNodeCode = shippingNodeCode;
    }
    public String getShippingNodeName() {
        return shippingNodeName;
    }
    public void setShippingNodeName(String shippingNodeName) {
        this.shippingNodeName = shippingNodeName;
    }
}

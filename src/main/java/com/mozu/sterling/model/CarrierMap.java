package com.mozu.sterling.model;

public class CarrierMap {
    String sterlingCarrierCode;
    String sterlingCarrierName;
    String mozuShipCode;

    public CarrierMap() {
    }
    
    public CarrierMap (String sterlingCarrierName, String sterlingCarrierCode, String mozuShipCode) {
        this.sterlingCarrierCode = sterlingCarrierCode;
        this.sterlingCarrierName = sterlingCarrierName;
        this.mozuShipCode = mozuShipCode;
    }
    
    public String getSterlingCarrierCode() {
        return sterlingCarrierCode;
    }
    public void setSterlingCarrierCode(String sterlingCarrierCode) {
        this.sterlingCarrierCode = sterlingCarrierCode;
    }
    public String getSterlingCarrierName() {
        return sterlingCarrierName;
    }
    public void setSterlingCarrierName(String sterlingCarrierName) {
        this.sterlingCarrierName = sterlingCarrierName;
    }
    public String getMozuShipCode() {
        return mozuShipCode;
    }
    public void setMozuShipCode(String mozuCode) {
        this.mozuShipCode = mozuCode;
    }
}

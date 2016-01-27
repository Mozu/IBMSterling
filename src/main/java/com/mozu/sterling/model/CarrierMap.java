package com.mozu.sterling.model;

public class CarrierMap {
    String sterlingScacAndService;
 	String sterlingCarrierName;
    String mozuShipCode;

    public CarrierMap() {
    }
    
    public CarrierMap (String sterlingCarrierName, String sterlingScacAndService, String mozuShipCode) {
        this.sterlingScacAndService = sterlingScacAndService;
        this.sterlingCarrierName = sterlingCarrierName;
        this.mozuShipCode = mozuShipCode;
    }
    
    public String getSterlingScacAndService() {
		return sterlingScacAndService;
	}

	public void setSterlingScacAndService(String sterlingScacAndService) {
		this.sterlingScacAndService = sterlingScacAndService;
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

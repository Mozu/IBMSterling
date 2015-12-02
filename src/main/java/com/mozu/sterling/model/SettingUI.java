package com.mozu.sterling.model;

import java.util.List;

public class SettingUI extends Setting {
    protected List<OptionUI> locations;
    protected List<OptionUI> sites;
    protected List<OptionUI> enterprises;
    protected List<OptionUI> mozuCarriers;
    protected List<SiteMap> sellerMap;
    protected List<CarrierMap> carrierMap;
    protected List<LocationMap> shipNodeMap;
    
    protected Boolean isConnected;
    protected String errorMsg;
    public List<OptionUI> getLocations() {
        return locations;
    }
    public void setLocations(List<OptionUI> locations) {
        this.locations = locations;
    }
    public List<OptionUI> getSites() {
        return sites;
    }
    public void setSites(List<OptionUI> sites) {
        this.sites = sites;
    }
    public List<OptionUI> getEnterprises() {
        return enterprises;
    }
    public void setEnterprises(List<OptionUI> enterprises) {
        this.enterprises = enterprises;
    }
    public List<OptionUI> getMozuCarriers() {
        return mozuCarriers;
    }
    public void setMozuCarriers(List<OptionUI> mozuCarriers) {
        this.mozuCarriers = mozuCarriers;
    }
    public List<SiteMap> getSellerMap() {
        return sellerMap;
    }
    public void setSellerMap(List<SiteMap> sellerMap) {
        this.sellerMap = sellerMap;
    }
    public List<LocationMap> getShipNodeMap() {
        return shipNodeMap;
    }
    public void setShipNodeMap(List<LocationMap> shipNodeMap) {
        this.shipNodeMap = shipNodeMap;
    }
    public Boolean getIsConnected() {
        return isConnected;
    }
    public void setIsConnected(Boolean isConnected) {
        this.isConnected = isConnected;
    }
    public String getErrorMsg() {
        return errorMsg;
    }
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public List<CarrierMap> getCarrierMap() {
        return carrierMap;
    }
    public void setCarrierMap(List<CarrierMap> carrierMap) {
        this.carrierMap = carrierMap;
    }
}

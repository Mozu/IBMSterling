package com.mozu.sterling.model;

import java.util.List;

public class SettingUI extends Setting {
    protected List<OptionUI> locations;
    protected List<OptionUI> sites;
    protected List<OptionUI> sellers;
    protected List<OptionUI> shippingNodes;
    protected List<OptionUI> enterprises;
    protected List<OptionUI> carriers;
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
    public List<OptionUI> getSellers() {
        return sellers;
    }
    public void setSellers(List<OptionUI> organizations) {
        this.sellers = organizations;
    }
    public List<OptionUI> getShippingNodes() {
        return shippingNodes;
    }
    public void setShippingNodes(List<OptionUI> shippingNodes) {
        this.shippingNodes = shippingNodes;
    }
    public List<OptionUI> getEnterprises() {
        return enterprises;
    }
    public void setEnterprises(List<OptionUI> enterprises) {
        this.enterprises = enterprises;
    }
    public List<OptionUI> getCarriers() {
        return carriers;
    }
    public void setCarriers(List<OptionUI> carriers) {
        this.carriers = carriers;
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
}

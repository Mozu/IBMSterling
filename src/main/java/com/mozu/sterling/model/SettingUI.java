package com.mozu.sterling.model;

import java.util.List;

public class SettingUI extends Setting {
    protected List<OptionUI> locations;
    protected List<OptionUI> sites;
    protected List<OptionUI> organizations;
    protected List<OptionUI> shippingNodes;
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
    public List<OptionUI> getOrganizations() {
        return organizations;
    }
    public void setOrganizations(List<OptionUI> organizations) {
        this.organizations = organizations;
    }
    public List<OptionUI> getShippingNodes() {
        return shippingNodes;
    }
    public void setShippingNodes(List<OptionUI> shippingNodes) {
        this.shippingNodes = shippingNodes;
    }
}

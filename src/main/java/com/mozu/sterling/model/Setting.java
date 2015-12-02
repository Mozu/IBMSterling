package com.mozu.sterling.model;

import java.util.Map;

/**
 * This object contains the configuration items needed to connect and synchronize with the Sterling system. 
 * @author bob_hewett
 *
 */
public class Setting {
    protected String id;
    protected String sterlingUrl;
    protected String sterlingUserId;
    protected String sterlingPassword;
    protected String sterlingEnterpriseCode;
    protected Map<String, String> siteMap;
    protected Map<String, String> locationMap;
    protected Map<String, String> shipMethodMap;
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
}

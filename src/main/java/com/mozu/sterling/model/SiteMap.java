package com.mozu.sterling.model;

public class SiteMap {
    String mozuSiteId;
    String  sterlingOrgCode;
    String  sterlingOrgName;
    public SiteMap () {
        
    }
    
    public SiteMap (String sterlingOrgCode, String sterlingOrgName, String mozuSiteId) {
        this.sterlingOrgCode = sterlingOrgCode;
        this.sterlingOrgName = sterlingOrgName;
        this.mozuSiteId = mozuSiteId;
    }
    
    public String getMozuSiteId() {
        return mozuSiteId;
    }
    public void setMozuSiteId(String mozuSiteId) {
        this.mozuSiteId = mozuSiteId;
    }
    public String getSterlingOrgCode() {
        return sterlingOrgCode;
    }
    public void setSterlingOrgCode(String sterlingOrgCode) {
        this.sterlingOrgCode = sterlingOrgCode;
    }
    public String getSterlingOrgName() {
        return sterlingOrgName;
    }
    public void setSterlingOrgName(String sterlingOrgName) {
        this.sterlingOrgName = sterlingOrgName;
    }
}

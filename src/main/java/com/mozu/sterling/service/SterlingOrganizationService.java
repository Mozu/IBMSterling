package com.mozu.sterling.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.organization.Organization;
import com.mozu.sterling.model.organization.OrganizationList;
import com.mozu.sterling.model.organization.ShipNode;
import com.mozu.sterling.model.organization.ShipNodeList;

@Service
/**
 * This class is responsible for getting the organization and ship node information from Sterling.
 * 
 * @author bob_hewett
 *
 */
public class SterlingOrganizationService extends SterlingClient {
    private static final Logger logger = LoggerFactory.getLogger(SterlingOrganizationService.class);

    public final static String SHIP_NODE_SERVICE_NAME = "getShipNodeList";
    public final static String ORGANIZATION_LIST_SERVICE_NAME = "getOrganizationList";

    @Autowired
    ConfigHandler configHandler;

    public SterlingOrganizationService() throws Exception {
        super();
    }
   
    /** 
     * Get list of 
     * @param tenantId
     * @return
     * @throws Exception
     */
    public List<ShipNode> getShipNodes (Integer tenantId) throws Exception {
        Setting setting = configHandler.getSetting(tenantId);
        return getShipNodes(setting);
    }
    
    public List<ShipNode> getShipNodes (Setting setting) throws Exception {
        ShipNodeList shipNodeList = null;
        if (StringUtils.isNotBlank(setting.getSterlingUrl())) {
            Document inDoc = convertObjectToXml(new ShipNode(), ShipNode.class);
            Document outDoc = this.invoke(SHIP_NODE_SERVICE_NAME, inDoc, setting);
            
            shipNodeList = (ShipNodeList) convertXmlToObject(outDoc, ShipNodeList.class);
        } else {
            logger.warn ("Cannot get Sterling ship nodes because the settings aren't set.");
        }
        return shipNodeList != null ? shipNodeList.getShipNode() : null; 
    }
    
    public List<Organization> getOrganizationList (Integer tenantId) throws Exception {
        Setting setting = configHandler.getSetting(tenantId);
        return getOrganizationList(setting);
        
    }
    public List<Organization> getOrganizationList (Setting setting) throws Exception {
        OrganizationList organizationList = null;
        if (StringUtils.isNotBlank(setting.getSterlingUrl())) {
            Document inDoc = convertObjectToXml(new ShipNode(), ShipNode.class);
            Document outDoc = this.invoke(ORGANIZATION_LIST_SERVICE_NAME, inDoc, setting);
            
            organizationList = (OrganizationList) convertXmlToObject(outDoc, OrganizationList.class);
        } else {
            logger.warn ("Cannot get Sterling ship nodes because the settings aren't set.");
        }
        return organizationList != null ? organizationList.getOrganization() : null; 
    }

}

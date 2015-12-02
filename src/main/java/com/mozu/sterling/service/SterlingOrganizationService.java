package com.mozu.sterling.service;

import java.util.ArrayList;
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
import com.mozu.sterling.model.shipping.CarrierService;
import com.mozu.sterling.model.shipping.CarrierServiceList;



@Service("sterlingOrganizationService")
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
    public final static String CARRIER_SERVICE_NAME = "getCarrierServiceList";

    @Autowired
    ConfigHandler configHandler;

    public SterlingOrganizationService() throws Exception {
        super();
    }
    
    /** 
     * Get list of Ship Nodes from Sterling.   Ship Nodes are similar to Mozu locations. 
     * @param tenantId the tenant Id to get the ship nodes for.
     * @return a list of ShipNode objects
     * @throws Exception
     */
    public List<ShipNode> getShipNodes (Integer tenantId) throws Exception {
        Setting setting = configHandler.getSetting(tenantId);
        return getShipNodes(setting);
    }
    
    /**
     * Get shipnodes using the settings values to connect to the Sterling service.
     * Ship Nodes are similar to Mozu locations.
     * @param setting the settings to use to lookup up the Sterling ship nodes.
     * @return a list of ShipNodes
     * @throws Exception
     */
    public List<ShipNode> getShipNodes (Setting setting) throws Exception {
        ShipNodeList shipNodeList = null;
        if (StringUtils.isNotBlank(setting.getSterlingUrl())) {
            ShipNode inShipNode = new ShipNode();
            if (StringUtils.isNotBlank(setting.getSterlingEnterpriseCode())) {
                inShipNode.setOwnerKey(setting.getSterlingEnterpriseCode());
            }

            Document inDoc = convertObjectToXml(inShipNode, ShipNode.class);
            Document outDoc = null;
            try {
                outDoc = this.invoke(SHIP_NODE_SERVICE_NAME, inDoc, setting);
                shipNodeList = (ShipNodeList) convertXmlToObject(outDoc, ShipNodeList.class);
            } catch (Exception e) {
                logger.warn("Unable to get ship node list from Sterling: " + e.getMessage());
            }
            
        } else {
            logger.warn ("Cannot get Sterling ship nodes because the settings aren't set.");
        }
        return shipNodeList != null ? shipNodeList.getShipNode() : new ArrayList<ShipNode>(); 
    }
    
    /**
     * Get all organizations for the settings saved for the given tenant id.
     * @param tenantId 
     * @return a list of all organizations in the Sterling system
     * @throws Exception
     */
    public List<Organization> getOrganizationList (Integer tenantId) throws Exception {
        Setting setting = configHandler.getSetting(tenantId);
        return getOrganizationList(setting, null);
    }
    
    /**
     * Get all organizations for the settings saved for the given tenant id.
     * @param setting
     * @return a list of organizations
     * @throws Exception
     */
    public List<Organization> getAllOrganizations (Setting setting) throws Exception {
        return getOrganizationList(setting, new Organization()); 
    }
    
    /**
     * Get all HUB organizations.  This is the top level organization in the Sterling system...much like a Mozu tenant.
     * @param setting the connection settings
     * @return a list of organizations with type Hub
     * @throws Exception
     */
    public List<Organization> getHubOrganizations (Setting setting) throws Exception {
        Organization organization = new Organization();
        organization.setIsHubOrganization("Y");
        return getOrganizationList(setting, organization); 
    }

    /**
     * Get the enterprise organizations.   This is a list of distinct sub-organizations or business units 
     * of the enterprise mapped in Sterling
     * @param setting the connection settings to use to connect to the Sterling server
     * @return a list of enterprise organizations
     * @throws Exception
     */
    public List<Organization> getEnterpriseOrganizations (Setting setting) throws Exception {
        Organization organization = new Organization();
        organization.setIsEnterprise("Y");
        return getOrganizationList(setting, organization); 
    }

    /**
     * Get seller organizations.  These can be mapped to sites in Mozu
     * @param setting
     * @return
     * @throws Exception
     */
    public List<Organization> getSellerOrganizations (Setting setting) throws Exception {
        Organization organization = new Organization();
        organization.setIsSeller("Y");
        if (StringUtils.isNotBlank(setting.getSterlingEnterpriseCode())) {
            organization.setCatalogOrganizationCode(setting.getSterlingEnterpriseCode());
        }
        return getOrganizationList(setting, organization); 
    }

    /**
     * General purpose organization lookup.  Organizations returned is based on what is set in the organizationLookup object
     * @param setting
     * @param organizationLookup
     * @return
     * @throws Exception
     */
    public List<Organization> getOrganizationList (Setting setting, Organization organizationLookup) throws Exception {
        OrganizationList organizationList = null;
        if (organizationLookup == null) {
            organizationLookup = new Organization();
        }
        if (StringUtils.isNotBlank(setting.getSterlingUrl())) {
            Document inDoc = convertObjectToXml(organizationLookup, Organization.class);
            Document outDoc = null;
            
            try {
                outDoc = this.invoke(ORGANIZATION_LIST_SERVICE_NAME, inDoc, setting);
                organizationList = (OrganizationList) convertXmlToObject(outDoc, OrganizationList.class);
            } catch (Exception e) {
                logger.warn("Unable to get the organization list from Sterling: " + e.getMessage());
                throw e;
            }
            
        } else {
            logger.warn ("Cannot get Sterling ship nodes because the settings aren't set.");
        }
        return organizationList != null ? organizationList.getOrganization() : new ArrayList<Organization>(); 
    }
 
    /**
     * Get the list of carriers fro the given organization code.
     * @param setting
     * @param ownerOrganizationCode
     * @return
     * @throws Exception
     */
    public List<CarrierService> getCarrierList (Setting setting) throws Exception {
        CarrierServiceList carrierList = null;

        CarrierService carrierService = new CarrierService();
        String ownerOrganziationCode = setting.getSterlingEnterpriseCode();
        if (StringUtils.isNotBlank(ownerOrganziationCode)) {
                carrierService.setOrganizationCode(ownerOrganziationCode);
        }
        if (StringUtils.isNotBlank(setting.getSterlingUrl())) {
            Document inDoc = convertObjectToXml(carrierService, CarrierService.class);
            Document outDoc = null;
            
            try {
                outDoc = this.invoke(CARRIER_SERVICE_NAME, inDoc, setting);
                carrierList = (CarrierServiceList) convertXmlToObject(outDoc, CarrierServiceList.class);
            } catch (Exception e) {
                logger.warn("Unable to get the shipping carriers list from Sterling: " + e.getMessage());
                throw e;
            }
            
        } else {
            logger.warn ("Cannot get shipping carriers nodes because the Sterling connection settings aren't available.");
        }
        return carrierList != null ? carrierList.getCarrierService(): new ArrayList<CarrierService>(); 
    }
}

package com.mozu.sterling.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import com.mozu.api.ApiContext;
import com.mozu.api.contracts.location.LocationType;
import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.organization.Organization;
import com.mozu.sterling.model.organization.OrganizationList;
import com.mozu.sterling.model.organization.ShipNode;
import com.mozu.sterling.model.organization.ShipNodeList;
import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.core.Address;
import com.mozu.api.contracts.location.Coordinates;
import com.mozu.api.contracts.location.Location;
import com.mozu.api.contracts.location.ShippingOriginContact;
import com.mozu.api.resources.commerce.admin.LocationResource;
import com.mozu.api.resources.commerce.admin.LocationTypeResource;
import com.mozu.sterling.model.LocationMapping;



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
    private List<String> locationTypeList;

    @Autowired
    ConfigHandler configHandler;

    public SterlingOrganizationService() throws Exception {
        super();
    }
    
    @PostConstruct
    public void loadLocationTypes(){
    	locationTypeList= new ArrayList<String>();
    	locationTypeList.add("Store");
    	locationTypeList.add("Warehouse");
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
    
    public List<Organization> getOrganizationList (Integer tenantId) throws Exception {
        Setting setting = configHandler.getSetting(tenantId);
        return getOrganizationList(setting, null);
    }
    
    public List<Organization> getAllOrganizations (Setting setting) throws Exception {
        return getOrganizationList(setting, new Organization()); 
    }
    
    public List<Organization> getHubOrganizations (Setting setting) throws Exception {
        Organization organization = new Organization();
        organization.setIsHubOrganization("Y");
        return getOrganizationList(setting, organization); 
    }

    public List<Organization> getEnterpriseOrganizations (Setting setting) throws Exception {
        Organization organization = new Organization();
        organization.setIsEnterprise("Y");
        return getOrganizationList(setting, organization); 
    }

    public List<Organization> getSellerOrganizations (Setting setting) throws Exception {
        Organization organization = new Organization();
        organization.setIsSeller("Y");
        return getOrganizationList(setting, organization); 
    }

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
     * @param apiContext
     * @param locationTypeCode
     * @return
     * @throws Exception
     */
    public LocationType createLocationType(ApiContext apiContext, String locationTypeCode) throws Exception{
       	LocationType locationType = getLocationType(apiContext, locationTypeCode);
       	if(locationType==null){
       		locationType= new LocationType();
       		locationType.setCode(locationTypeCode);
       		locationType.setName(locationTypeCode);
       		locationType = addLocationType(apiContext, locationType);
       	}
    	return locationType;
    }
    
    /**
     * @param shipNode
     * @param apiContext
     * @return
     * @throws Exception
     */
    public Location createLocation(ShipNode shipNode, ApiContext apiContext ) throws Exception {
    	Location location = getMozuLocation(shipNode.getNodeOrgCode(), apiContext);
		if(location == null){
    		mapLocation(apiContext,shipNode, new Location());	
    		location=addMozuLocation(location,apiContext );
		}else{
			logger.info("Location with code "+location.getCode()+" already exists. So skipping the record");
		}
		return location;
    	
    }
    
    public Location mapLocation(ApiContext apiContext ,ShipNode shipNode, Location location) throws Exception{
    	logger.info("Map Sterling location to Mozu location");
    	
    	location.setName(shipNode.getShipNode());
		location.setDescription(shipNode.getDescription());
		location.setCode(shipNode.getNodeOrgCode());
		
		Coordinates coordinates = new Coordinates();
		coordinates.setLat(0);
		coordinates.setLng(0);
		location.setGeo(coordinates);
		
		Address locationAddress= new Address();
		if(shipNode.getContactPersonInfo()!=null){
			locationAddress.setAddress1(shipNode.getContactPersonInfo().getAddressLine1());
			locationAddress.setAddress2(shipNode.getContactPersonInfo().getAddressLine2());
			locationAddress.setAddress3(shipNode.getContactPersonInfo().getAddressLine3());
			locationAddress.setAddress4(shipNode.getContactPersonInfo().getAddressLine4());
			locationAddress.setCityOrTown(shipNode.getContactPersonInfo().getCity());
			locationAddress.setCountryCode(shipNode.getContactPersonInfo().getCountry());
			locationAddress.setPostalOrZipCode(shipNode.getContactPersonInfo().getZipCode());
			locationAddress.setStateOrProvince(shipNode.getContactPersonInfo().getState());
		}
		location.setAddress(locationAddress);
		
		ShippingOriginContact shippingOriginContact = new ShippingOriginContact();
		if(shipNode.getShipNodePersonInfo()!=null){
			shippingOriginContact.setCompanyOrOrganization(shipNode.getContactPersonInfo().getCompany());
			shippingOriginContact.setEmail(shipNode.getContactPersonInfo().getEMailID());
			shippingOriginContact.setFirstName(shipNode.getContactPersonInfo().getFirstName());
			shippingOriginContact.setLastNameOrSurname(shipNode.getContactPersonInfo().getLastName());
			shippingOriginContact.setMiddleNameOrInitial(shipNode.getContactPersonInfo().getMiddleName());
			String phoneNumber = shipNode.getContactPersonInfo().getDayPhone();
			if(phoneNumber==null){
				phoneNumber=shipNode.getContactPersonInfo().getEveningPhone();
			}
			if(phoneNumber==null){
				phoneNumber = shipNode.getContactPersonInfo().getMobilePhone();
			}
			if(phoneNumber==null){
				phoneNumber = shipNode.getContactPersonInfo().getOtherPhone();
			}
			
			shippingOriginContact.setPhoneNumber(phoneNumber);
		}
		location.setShippingOriginContact(shippingOriginContact);
		
		List<LocationType> locationTypes = new ArrayList<LocationType>();
		if(shipNode.getNodeType().equalsIgnoreCase("Store")){
			LocationType locationType=new LocationType();
			locationType.setCode("Store");
	    	locationTypes.add(locationType);
		}else{
			LocationType locationType=new LocationType();
			locationType.setCode("Warehouse");
			locationTypes.add(locationType);
		}
		location.setLocationTypes(locationTypes);
		return location;
    }
    
    public void createAndAutoMapLocations(Integer tenantId) throws Exception{
    	List<ShipNode> shipNodes = getShipNodes(tenantId);
    	ApiContext apiContext = new MozuApiContext(tenantId);
    	Setting setting = configHandler.getSetting(apiContext.getTenantId());
    	List<LocationMapping> locationMappings = setting.getLocationMappings();
    	for (ShipNode shipNode : shipNodes) {
    		boolean mappingExists=false;
    		Location location=createLocation(shipNode, apiContext);
    		
    		if(locationMappings == null){
    			locationMappings = new ArrayList<LocationMapping>();
    		}
    		for (LocationMapping locationMapping : locationMappings) {
				if(locationMapping.getLocationCode()!=null && locationMapping.getLocationCode().equalsIgnoreCase(location.getCode())){
					mappingExists=true;
					break;
				}
			}
    		if(!mappingExists){
	    		LocationMapping locationMapping = autoMapLocation(location);
	    		locationMappings.add(locationMapping);
    		}
   	    }
    	if(setting!=null){
    		setting.setLocationMappings(locationMappings);
    		configHandler.saveSettings(tenantId, setting);
    	}
    }
    
    public Location addMozuLocation(Location location , ApiContext apiContext) throws Exception{
    	logger.info("Add location with code "+location.getCode()+" to Mozu");
    	LocationResource locationResource = new LocationResource(apiContext);
    	return locationResource.addLocation(location);
    	
    }
    
    public Location getMozuLocation(String locationCode , ApiContext apiContext) throws Exception{
    	LocationResource locationResource = new LocationResource(apiContext);
    	return locationResource.getLocation(locationCode);
    	
    }
    
    public LocationMapping autoMapLocation(Location location){
    	LocationMapping locationMapping=new LocationMapping();
    	locationMapping.setLocationCode(location.getCode());
    	locationMapping.setShippingNodeCode(location.getCode());
		return locationMapping;
    }
    
    public LocationType addLocationType(ApiContext apiContext, LocationType locationType) throws Exception{
    	LocationTypeResource locationTypeResource = new LocationTypeResource(apiContext);
    	return locationTypeResource.addLocationType(locationType);
    }
    
    public LocationType getLocationType(ApiContext apiContext, String locationTypeCode) throws Exception{
    	LocationTypeResource locationTypeResource = new LocationTypeResource(apiContext);
    	return locationTypeResource.getLocationType(locationTypeCode);
    }
    
    public List<String> getLocationTypeList() {
		return locationTypeList;
	}
    

}

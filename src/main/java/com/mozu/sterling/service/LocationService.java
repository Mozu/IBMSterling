package com.mozu.sterling.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mozu.api.ApiContext;
import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.core.Address;
import com.mozu.api.contracts.location.Coordinates;
import com.mozu.api.contracts.location.Location;
import com.mozu.api.contracts.location.LocationType;
import com.mozu.api.contracts.location.ShippingOriginContact;
import com.mozu.api.resources.commerce.admin.LocationResource;
import com.mozu.api.resources.commerce.admin.LocationTypeResource;
import com.mozu.sterling.model.LocationMap;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.organization.ShipNode;

@Service("locationService")
public class LocationService extends SterlingOrganizationService {
    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);
    private List<String> locationTypeList;

    public LocationService () throws Exception {
        super();
    }

    @PostConstruct
    public void loadLocationTypes(){
        locationTypeList= new ArrayList<String>();
        locationTypeList.add("Store");
        locationTypeList.add("Warehouse");
    }
   
    /**
     * Add locationType to Mozu
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
     * Add Location to Mozu
     * @param shipNode
     * @param apiContext
     * @return
     * @throws Exception
     */
    public Location createOrUpdateLocation(ShipNode shipNode, ApiContext apiContext ) throws Exception {
        Location location = getMozuLocation(shipNode.getNodeOrgCode(), apiContext);
        Location mappedLocation=mapLocation(apiContext,shipNode);  
        if(location == null){
            location=addMozuLocation(mappedLocation,apiContext );
        }else{
            location=updateMozuLocation(mappedLocation, apiContext);
        }
        return location;
        
    }
    
    /**
     * Map Sterling ShipNode to Mozu Location
     * @param apiContext
     * @param shipNode
     * @param location
     * @return Location
     * @throws Exception
     */
    
    public Location mapLocation(ApiContext apiContext ,ShipNode shipNode) throws Exception{
        logger.info("Map Sterling location to Mozu location");
        Location location=new Location();
        location.setName(shipNode.getDescription());
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
    
    public Setting createAndAutoMapLocations(Integer tenantId) throws Exception{
        List<ShipNode> shipNodes = getShipNodes(tenantId);
        ApiContext apiContext = new MozuApiContext(tenantId);
        Setting setting = configHandler.getSetting(apiContext.getTenantId());
        if(setting!=null){
            Map<String, String> locationMappings = setting.getLocationMap();
            for (ShipNode shipNode : shipNodes) {
                Location location=createOrUpdateLocation(shipNode, apiContext);
                
                locationMappings.put(location.getCode(), shipNode.getShipnodeKey());
            }
            setting.setLocationMap(locationMappings);
            logger.info("Save locationMappings to setting");
            configHandler.saveSettings(tenantId, setting);
        } else {
            
        }
        return setting;
    }
    
    public Location addMozuLocation(Location location , ApiContext apiContext) throws Exception{
        logger.info("Add location with code "+location.getCode()+" to Mozu");
        LocationResource locationResource = new LocationResource(apiContext);
        return locationResource.addLocation(location);
        
    }
    
    public Location updateMozuLocation(Location location , ApiContext apiContext) throws Exception{
        logger.info("Update location with code "+location.getCode()+" in Mozu");
        LocationResource locationResource = new LocationResource(apiContext);
        return locationResource.updateLocation(location, location.getCode());
        
    }

    public Location getMozuLocation(String locationCode , ApiContext apiContext) throws Exception{
        LocationResource locationResource = new LocationResource(apiContext);
        return locationResource.getLocation(locationCode);
        
    }
    
    public LocationMap autoMapLocation(Location location){
        logger.info("Auto map location with code "+location.getCode());
        LocationMap locationMapping=new LocationMap();
        locationMapping.setLocationCode(location.getCode());
        locationMapping.setShippingNodeCode(location.getCode());
        return locationMapping;
    }
    
    public LocationType addLocationType(ApiContext apiContext, LocationType locationType) throws Exception{
        logger.info("Add location type with code "+locationType.getCode()+" to Mozu");
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

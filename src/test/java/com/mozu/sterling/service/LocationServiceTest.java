package com.mozu.sterling.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import com.mozu.api.MozuApiContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mozu.api.contracts.location.Location;
import com.mozu.api.ApiContext;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.organization.ShipNode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/servlet-context.xml"})
public class LocationServiceTest {
	
	protected static final int TENANT_ID = 419;
	
    @Autowired
    LocationService locationService;
    
    @Test
    public void getShipNodesTest () throws Exception {
        List<ShipNode> shipNodes = locationService.getShipNodes(TENANT_ID);
        
        assertTrue (shipNodes.size() > 0);
    }
    
    @Test
    public void createLocationTest () throws Exception {
    	ApiContext apiContext = new MozuApiContext(TENANT_ID);
    	
    	List<String> locationTypeList = locationService.getLocationTypeList();
        for (String locationTypeCode : locationTypeList) {
        	assertNotNull(locationService.createLocationType(apiContext, locationTypeCode));
		}
    	List<ShipNode> shipNodes = locationService.getShipNodes(TENANT_ID);
    	Location location=null;
    	for (ShipNode shipNode : shipNodes) {
    		location=locationService.createOrUpdateLocation(shipNode, apiContext);
		 }
    	assertNotNull(location);
    }
    
    @Test
    public void createAndAutoMapLocationsTest() throws Exception{
    	Setting setting = locationService.createAndAutoMapLocations(TENANT_ID);
    	assertNotNull(setting.getLocationMap());
    }
    
}

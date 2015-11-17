package com.mozu.sterling.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mozu.sterling.model.organization.ShipNode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/servlet-context.xml"})
public class LocationServiceTest {
    @Autowired
    SterlingOrganizationService locationService;
    
    @Test
    public void getShipNodesTest () throws Exception {
        List<ShipNode> shipNodes = locationService.getShipNodes(419);
        
        assertTrue (shipNodes.size() > 0);
    }
    
}

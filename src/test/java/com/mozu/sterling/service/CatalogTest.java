package com.mozu.sterling.service;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.catalog.MasterCatalog;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/servlet-context.xml"})
public class CatalogTest {
	
	protected static final int TENANT_ID = 419;
    protected static final int SITE_ID = 12825;
	
    @Autowired
    CatalogService catalogService;
    
    @Autowired 
    ConfigHandler configHandler;
    
    @Test
    public void getCatalogsTest () throws Exception {
        Setting setting = configHandler.getSetting(TENANT_ID);
        
        List<MasterCatalog> items = catalogService.getSterlingMasterCatalogs(setting);
        assertTrue (items.size() > 0);


    }

}

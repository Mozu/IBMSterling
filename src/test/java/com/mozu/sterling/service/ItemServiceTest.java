package com.mozu.sterling.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.item.Item;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/servlet-context.xml"})
public class ItemServiceTest {
	
	protected static final int TENANT_ID = 419;
    protected static final int SITE_ID = 1223;
	
    @Autowired
    SterlingItemService itemService;
    
    @Autowired 
    ConfigHandler configHandler;
    
   @Ignore
   @Test
    public void getItemTest () throws Exception {
        Setting setting = configHandler.getSetting(TENANT_ID);
        
        List<Item> items = itemService.getItemsPaged(setting, SITE_ID, 1, 10);
        assertTrue (items.size() == 10);

        items = itemService.getItemsPaged(setting, SITE_ID, 2, 25);
        assertTrue (items.size() == 25);

    }
    
    @Ignore
    @Test
    public void getAllItemTest () throws Exception {
        Setting setting = configHandler.getSetting(TENANT_ID);
        
        List<Item> items = null;
        int pageSize = 100;
        int pageNo = 1;
        do {
            items = itemService.getItemsPaged(setting, SITE_ID, pageNo++, pageSize);
            if (items != null) {
                Item item = items.get(0);
                assertNotNull(item.getItemID());
            }
        } while (items != null && items.size() == pageSize);
    }
}

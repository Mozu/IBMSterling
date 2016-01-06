package com.mozu.sterling.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.order.Order;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/servlet-context.xml"})
public class OrderServiceTest {
    
    protected static final int TENANT_ID = 419;
    
    @Autowired
    OrderService orderService;
    
    @Autowired 
    ConfigHandler configHandler;
    
    @Test
    public void getAllOrdersTest () throws Exception {
        Setting setting = configHandler.getSetting(TENANT_ID);
        
        List<Order> items = null;
        items = orderService.getSterlingOrders(setting);
        assertNotNull(items);
        assertTrue(items.size() > 0);
    }
    
    @Test 
    public void getOrderDetail() throws Exception {
        Setting setting = configHandler.getSetting(TENANT_ID);
        Order sterlingOrder = orderService.getSterlingOrderDetail(setting, "315");
        assertNotNull(sterlingOrder);
    }
}

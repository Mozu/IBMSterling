package com.mozu.sterling.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mozu.api.ApiContext;
import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.commerceruntime.orders.Order;
import com.mozu.api.contracts.commerceruntime.orders.OrderItem;
import com.mozu.api.resources.commerce.OrderResource;
import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.model.Setting;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/servlet-context.xml"})
public class OrderSyncServiceTest {
	 @Autowired
	 OrderService orderService;
	 
	 @Autowired 
	 ConfigHandler configHandler;
	 
	 protected static final int TENANT_ID = 4449;
	 protected static final int SITE_ID = 7280;
	 String orderId="07c699b9157c283accca391600001161";
	 
	 @Test
	 public void exportOrderToSterlingTest() throws Exception{
		 ApiContext apiContext = new MozuApiContext(TENANT_ID);
	     apiContext.setSiteId(SITE_ID);
	     OrderResource orderResource = new OrderResource(apiContext);
	     Order mozuOrder = orderResource.getOrder(orderId);
		 boolean status = orderService.createOrder(apiContext, mozuOrder);
		 assertTrue(status);
	 }
	 
	 @Test
	 public void cancelSterlingOrderTest() throws Exception{
		 ApiContext apiContext = new MozuApiContext(TENANT_ID);
	     apiContext.setSiteId(SITE_ID);
	     OrderResource orderResource = new OrderResource(apiContext);
	     Order mozuOrder = orderResource.getOrder(orderId);
	     Setting setting = configHandler.getSetting(apiContext.getTenantId());
	     com.mozu.sterling.model.order.Order sterlingOrder = orderService.getSterlingOrderDetail(setting, mozuOrder.getOrderNumber().toString());
		 boolean status = orderService.cancelSterlingOrder(setting, mozuOrder, sterlingOrder);
		 assertTrue(status);
	 }
	 
	 @Test
	 public void completeSterlingOrderTest() throws Exception{
		 ApiContext apiContext = new MozuApiContext(TENANT_ID);
	     apiContext.setSiteId(SITE_ID);
	     OrderResource orderResource = new OrderResource(apiContext);
	     boolean status=false;
	     Setting setting = configHandler.getSetting(apiContext.getTenantId());
	     Order mozuOrder = orderResource.getOrder(orderId);
	     com.mozu.sterling.model.order.Order sterlingOrder = orderService.getSterlingOrderDetail(setting, mozuOrder.getOrderNumber().toString());
	     if(sterlingOrder !=null){
		        for(OrderItem orderItem : mozuOrder.getItems()){
		          	if(orderItem.getFulfillmentMethod().equalsIgnoreCase("Digital")){
		           		status= orderService.updateSterlingOrderStatus(setting, mozuOrder, sterlingOrder,orderItem);
		           		assertTrue(status);
		           	}
		        }
	     }
	 }

}

package com.mozu.sterling.jobtask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mozu.api.ApiContext;
import com.mozu.api.MozuApiContext;
import com.mozu.api.contracts.commerceruntime.orders.Order;
import com.mozu.jobs.jobtasks.BaseBatchJob;
import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.handler.SterlingOrderToMozuMapper;
import com.mozu.sterling.model.Setting;

@Service("orderImportProcessor")
@Scope("step")
public class OrderImportProcessor extends BaseBatchJob implements ItemProcessor<com.mozu.sterling.model.order.Order, Order> {
    private static final Logger logger = LoggerFactory.getLogger(OrderImportProcessor.class);

    @Autowired
    SterlingOrderToMozuMapper orderMapper;
    
    @Autowired
    ConfigHandler configHandler;
    
    public Order process(com.mozu.sterling.model.order.Order sterlingOrder) throws Exception {
        logger.debug("OrderImportProcessor");
        ApiContext apiContext = new MozuApiContext(tenantId, siteId);
        Setting setting = configHandler.getSetting(tenantId); 
        Order order = orderMapper.saleToOrder(sterlingOrder,null, apiContext, setting);
        return order;
    }
}

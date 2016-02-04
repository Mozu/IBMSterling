package com.mozu.sterling.jobtask;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mozu.api.ApiContext;
import com.mozu.api.MozuApiContext;
import com.mozu.jobs.jobtasks.BaseBatchJob;
import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.order.Order;
import com.mozu.sterling.service.OrderService;

@Service("orderImportWriter")
@Scope("step")
public class OrderImportWriter extends BaseBatchJob implements ItemWriter<Order> {

    @Autowired
    OrderService orderService;

    @Autowired
    ConfigHandler configHandler;
    
    @Override
    public void write(List<? extends Order> items) throws Exception {
        ApiContext apiContext = new MozuApiContext(tenantId, siteId);
        Setting setting = configHandler.getSetting(tenantId);
        for (Order sterlingOrder : items) {
            orderService.importSterlingOrder(apiContext, setting, sterlingOrder.getOrderNo());
        }
    }
}

package com.mozu.sterling.jobtask;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mozu.jobs.dao.JobExecutionDao;
import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.order.Order;
import com.mozu.sterling.service.OrderService;

@Service("orderImportReader")
@Scope("step")
public class OrderImportReader extends AbstractPagingItemReader<Order> {
    private static final Logger logger = LoggerFactory.getLogger(OrderImportReader.class);


    private Integer tenantId;
    private Long orderDate;

    @Autowired
    OrderService orderService;
    
    @Autowired
    JobExecutionDao jobExecutionDao;

    @Autowired
    ConfigHandler configHandler;
    
    @Override
    protected void doReadPage() {
    
        Setting setting = null;
        try {
            setting = configHandler.getSetting(tenantId);
        } catch (Exception e) {
            String msg = String.format("Unable to get the settings for tenant ID: %d", tenantId);
            logger.error(msg);
            throw new RuntimeException (msg);
        }
        
        logger.debug("SalesImportReader.doPage" );
        // no paging so we can only read this once.....
        if (results == null) {
            results = new CopyOnWriteArrayList<Order>();
            List<Order> sales;
            try {
                logger.debug("Reading all order, no last runtime");
                sales = orderService.getSterlingOrders(setting, orderDate);
                results.addAll(sales);
                setPageSize(results.size() + 1);
            } catch (Exception e) {
                throw new RuntimeException("Error while getting sales from LightSpeed:", e);
            }
        }
    }

    @Override
    protected void doJumpToPage(int itemIndex) {
        logger.debug("SalesImportReader.doJumpToPage " + itemIndex);
    }

    @Value("#{jobParameters['tenantId']}")
    public void setTenantId(final Long tenantId) {
        this.tenantId = tenantId !=null ? tenantId.intValue() : null;
    }

    @Value("#{jobParameters['orderDateQuery']}")
    public void setOrderDateQuery(final Long orderDate) {
        this.orderDate = orderDate != null ? orderDate : null;
    }
}

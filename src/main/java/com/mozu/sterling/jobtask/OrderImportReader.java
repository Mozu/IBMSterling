package com.mozu.sterling.jobtask;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mozu.api.ApiContext;
import com.mozu.api.MozuApiContext;
import com.mozu.jobs.dao.JobExecutionDao;
import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.order.Order;
import com.mozu.sterling.service.OrderService;

@Service("orderImportReader")
@Scope("step")
public class OrderImportReader extends AbstractPagingItemReader<Order> {
    private static final Logger logger = LoggerFactory.getLogger(OrderImportReader.class);

    private static final long FIVE_MINUTES = 5 * 60 * 1000;

    private Integer tenantId;
    private Integer siteId;
    private Long lastRunTime;

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
        if (results == null) {
            results = new CopyOnWriteArrayList<Order>();
        } else {
            results.clear();
        }
        
        List<Order> sales;
        try {
        	if (lastRunTime==null) {
        		logger.debug("Reading all order, no last runtime");
                sales = orderService.getSterlingOrders(setting);
        	} else {
        		logger.debug("Read sales since " + new DateTime(lastRunTime - FIVE_MINUTES));
                sales = orderService.getSterlingOrders(setting);
        	}
            results.addAll(sales);
        } catch (Exception e) {
            throw new RuntimeException("Error while getting sales from LightSpeed:", e);
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

    @Value("#{jobParameters['siteId']}")
    public void setSiteId(final Long siteId) {
        this.siteId = siteId != null ? siteId.intValue() : null;
    }

    @Value("#{jobParameters['lastRunTime']}")
    public void setLastRunTime(final Long lastRunTime) {
        this.lastRunTime = lastRunTime != null ? lastRunTime : null;
    }
}
package com.mozu.sterling.event;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mozu.api.ApiContext;
import com.mozu.api.contracts.event.Event;
import com.mozu.api.events.EventManager;
import com.mozu.api.events.handlers.ApplicationEventHandler;
import com.mozu.api.events.model.EventHandlerStatus;
import com.mozu.base.utils.ApplicationUtils;
import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.service.MessageService;

@Component
public class ApplicationEventHandlerImpl implements ApplicationEventHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationEventHandlerImpl.class);

    @Autowired
    ConfigHandler configHandler;

    @Autowired
    MessageService messageService;

    @PostConstruct
    public void initialize() {
        EventManager.getInstance().registerHandler(this);
        logger.info("Application event handler initialized");
    }

    @Override
    public EventHandlerStatus disabled(ApiContext apiContext, Event event) {
	try {
		if (messageService.toggleMessageQueueListener(apiContext.getTenantId(), apiContext.getSiteId())) {
			messageService.toggleMessageQueueListener(apiContext.getTenantId(), apiContext.getSiteId());
		}
	} catch (Exception e) {
		logger.error("An error occurred starting the jms listener for tenant " + apiContext.getTenantId(), e);
	}

        return new EventHandlerStatus(HttpStatus.SC_OK);
    }

    @Override
    public EventHandlerStatus enabled(ApiContext apiContext, Event event) {
	try {
		if (!messageService.toggleMessageQueueListener(apiContext.getTenantId(), apiContext.getSiteId())) {
			messageService.toggleMessageQueueListener(apiContext.getTenantId(), apiContext.getSiteId());
		}
	} catch (Exception e) {
		logger.error("An error occurred starting the jms listener for tenant " + apiContext.getTenantId(), e);
	}

        return new EventHandlerStatus(HttpStatus.SC_OK);
    }

    @Override
    public EventHandlerStatus installed(ApiContext apiContext, Event event) {
        logger.debug("Application installed event");
        try {
            configHandler.installSchema(apiContext.getTenantId());
        } catch (Exception e) {
            logger.error(String.format("An error occurred installing settings schema for tenant %d: %s", apiContext.getTenantId(), e.getMessage()));
            new EventHandlerStatus(e.getMessage(), HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }
        return enableApplication(apiContext);
    }

    @Override
    public EventHandlerStatus uninstalled(ApiContext apiContext, Event event) {
        return new EventHandlerStatus(HttpStatus.SC_OK);
    }

    @Override
    public EventHandlerStatus upgraded(ApiContext apiContext, Event event) {
        logger.debug("Application upgraded event");
        try {
            configHandler.installSchema(apiContext.getTenantId());
        } catch (Exception e) {
            logger.error(String.format("An error occurred installing settings schema for tenant %d: %s", apiContext.getTenantId(), e.getMessage()));
            new EventHandlerStatus(e.getMessage(), HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }
        return enableApplication(apiContext);
    }
    
    private EventHandlerStatus enableApplication(ApiContext apiContext) {
        EventHandlerStatus status = new EventHandlerStatus(HttpStatus.SC_OK);
        
        return status;
    }
    
    @PreDestroy
    public void cleanup() {
        EventManager.getInstance().unregisterHandler(this.getClass());
        logger.debug("Application event handler unregistered");
    }
}

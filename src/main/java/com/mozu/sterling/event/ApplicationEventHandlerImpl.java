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
import com.mozu.jobs.scheduler.JobScheduler;
import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.job.JmsStartJob;
import com.mozu.sterling.service.MessageService;

@Component
public class ApplicationEventHandlerImpl implements ApplicationEventHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationEventHandlerImpl.class);
    private static final String JMS_START_JOB = "sterlingJmsStartJob";

    @Autowired
    ConfigHandler configHandler;

    @Autowired
    MessageService messageService;

    @Autowired
    JobScheduler jobScheduler;

    @PostConstruct
    public void initialize() {
        EventManager.getInstance().registerHandler(this);
        logger.info("Application event handler initialized");
    }

    @Override
    public EventHandlerStatus disabled(ApiContext apiContext, Event event) {
	try {
        jobScheduler.delJob(apiContext.getTenantId(), apiContext.getSiteId(), JMS_START_JOB);
        logger.debug("Scheduled job for JMS Start removed for tenant ID: " + apiContext.getTenantId());
		if (!messageService.turnOffMessageQueueListener(apiContext.getTenantId(), apiContext.getSiteId())) {
	        logger.error("An error occurred stopping the jms listener for tenant " + apiContext.getTenantId());
		}
	} catch (Exception e) {
		logger.error("An error occurred stoping the jms listener for tenant " + apiContext.getTenantId(), e);
	}

        return new EventHandlerStatus(HttpStatus.SC_OK);
    }

    @Override
    public EventHandlerStatus enabled(ApiContext apiContext, Event event) {
	try {
		if (!messageService.turnOnMessageQueueListener(apiContext.getTenantId(), apiContext.getSiteId())) {
            logger.error("An error occurred starting the jms listener for tenant " + apiContext.getTenantId());
		}
        logger.info("Scheduling JMS Start Job for tenant ID: " + apiContext.getTenantId());

        jobScheduler.updateJobFrequencySeconds(apiContext.getTenantId(), 
                apiContext.getSiteId(), 180,  
                JMS_START_JOB, 
                JmsStartJob.class);

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
        try {
			configHandler.addLocationType(apiContext);
		} catch (Exception e) {
			 logger.error(String.format("An error occurred while adding location type for tenant %d: %s", apiContext.getTenantId(), e.getMessage()));
	         new EventHandlerStatus(e.getMessage(), HttpStatus.SC_INTERNAL_SERVER_ERROR);
		}
        return enableApplication(apiContext);
    }

    @Override
    public EventHandlerStatus uninstalled(ApiContext apiContext, Event event) {
        try {
            jobScheduler.delJob(apiContext.getTenantId(), apiContext.getSiteId(), JMS_START_JOB);
            logger.debug("Scheduled job for JMS Start removed for tenant ID: " + apiContext.getTenantId());
        } catch (Exception e) {
            logger.info("Exception stopping JMS Start job: " + e.getMessage());
        }

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
        try {
			configHandler.addLocationType(apiContext);
		} catch (Exception e) {
			 logger.error(String.format("An error occurred while adding location type for tenant %d: %s", apiContext.getTenantId(), e.getMessage()));
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

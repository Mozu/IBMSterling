package com.mozu.sterling.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mozu.jobs.scheduler.ScheduledJob;
import com.mozu.sterling.model.ApplicationContextProvider;
import com.mozu.sterling.service.MessageService;

@DisallowConcurrentExecution
public class JmsStartJob extends ScheduledJob {
    private static final Logger logger = LoggerFactory.getLogger(JmsStartJob.class);
    
    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        logger.info("JMS Listener Watchdog Job executing...");
        try {
            int retries = 0;
            while ((ApplicationContextProvider.getApplicationContext() == null) && retries < 20) {
                try {
                    logger.debug("Waiting to start JMS Listener job until server starts...");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                retries++;
            }
            if (ApplicationContextProvider.getApplicationContext() == null) {
                throw new RuntimeException("Unable to  start \"JMS Listener\" job because it took too long to start...");
            }
            MessageService messageService = ApplicationContextProvider.getApplicationContext().getBean("messageService", MessageService.class);
            
            messageService.turnOnMessageQueueListener(tenantId, siteId);
        } catch (Exception e1) {
            logger.error("Error executing JMS Listener job: " + e1.getMessage());
        }
        logger.info ("...JMS Listener Watchdog Job Finished");
    }
}

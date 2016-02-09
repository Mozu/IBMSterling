package com.mozu.sterling.job;

import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.stereotype.Component;

import com.mozu.jobs.dao.JobExecutionDao;
import com.mozu.jobs.scheduler.ScheduledJob;
import com.mozu.sterling.model.ApplicationContextProvider;
import com.mozu.sterling.model.JobInfoUI;

@Component
public class SterlingJob extends ScheduledJob {
    private static final Logger logger = LoggerFactory.getLogger(SterlingJob.class);

    @Override
    public JobParameters buildJobParams() {
        logger.debug("Build job parameters");

        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addLong("tenantId", tenantId.longValue());
        builder.addLong("siteId", siteId.longValue());
        builder.addLong("timestamp", new Date().getTime());
        Timestamp lastSuccessfulRunDate = null;
        
        // check to make sure we're not trying to start a job before the application context is set
        int retries = 0;
        while ((ApplicationContextProvider.getApplicationContext() == null) && retries < 20) {
            try {
                logger.debug("Waiting to start quartz job until server starts...");
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            retries++;
        }
        
        if (ApplicationContextProvider.getApplicationContext() == null) {
            throw new RuntimeException("Unable to start quartz job because it took too long to start...");
        }

        JobExecutionDao jobExecutionDao = ApplicationContextProvider.getApplicationContext()
                .getBean("jobExecutionDao", JobExecutionDao.class);
        lastSuccessfulRunDate = jobExecutionDao.getLastExecutionDate(tenantId.longValue(), jobName);
        if (lastSuccessfulRunDate != null) {
            builder.addLong(JobInfoUI.ORDER_DATE_QUERY_PARAM, lastSuccessfulRunDate.getTime());
        }

        return builder.toJobParameters();
    }
}

/*
 * COPYRIGHT (C) 2014 Volusion Inc. All Rights Reserved.
 */

package com.mozu.sterling.handler;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mozu.jobs.dao.JobExecutionDao;
import com.mozu.jobs.handlers.JobHandler;
import com.mozu.sterling.model.JobInfoUI;

/**
 * Handler for Spring Batch Jobs. Glue class between QRTZ scheduled
 * jobs and Spring Batch jobs, allows for the autowired Spring batch
 * data structures.
 * @author john_gatti
 *
 */
@Service
public class BatchJobHandler {

    private static final Logger logger = LoggerFactory.getLogger(BatchJobHandler.class);

    @Autowired
    JobExplorer jobExplorer;

    @Autowired
    JobExecutionDao jobExecutionDao;
    
    @Autowired
    JobHandler jobHandler;

    @Autowired
    ConfigHandler configHandler;

    /**
     * Execute the job and do a full sync. 
     * processing of the file
     * @param tenantId
     * @param siteId
     * @param jobName
     * @throws IOException 
     */
    public JobExecution executeJob(Integer tenantId, Integer siteId, String jobName) 
            throws JobExecutionException {
        return executeJob(tenantId, siteId, jobName, null);
    }
    
    /**
     * Execute the job and do a sync from the fromRunDate. 
     * processing of the file
     * @param tenantId
     * @param siteId
     * @param jobName
     * @throws IOException 
     */
    public JobExecution executeJob(Integer tenantId, Integer siteId, String jobName, Timestamp fromRundate) 
                throws JobExecutionException {
        JobParameters jobParams = null;
        Timestamp lastSuccessfulRunDate = null;

        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addLong("tenantId", tenantId.longValue());
        if (siteId != null) {
            builder.addLong("siteId", siteId.longValue());
        }
        builder.addLong("timestamp", new Date().getTime());
        
        if (jobName.equals(JobInfoUI.ORDER_IMPORT_JOB)) {
            if (fromRundate == null) {
                lastSuccessfulRunDate = jobExecutionDao.getLastExecutionDate(tenantId.longValue(), siteId.longValue(), JobInfoUI.ORDER_IMPORT_JOB);
            } else {
                lastSuccessfulRunDate  = fromRundate;
            }
            if (lastSuccessfulRunDate != null) {
                builder.addLong(JobInfoUI.LAST_RUN_TIME_PARAM, lastSuccessfulRunDate.getTime());
            }
        }

        jobParams = builder.toJobParameters();

        return executeJob(tenantId, siteId, jobParams, jobName, true);
    }
    
    /**
     * 
     * @param tenantId the tenant ID to run the job for
     * @param siteId the site ID to run the job for
     * @param jobName the name of the job to run
     * @param isFullSync if false, a incremental job run will be run based on any updates from last successful job run.
     * @return the job execution record for the job.
     * @throws JobExecutionException
     */
    public JobExecution executeJob(Integer tenantId, Integer siteId, JobParameters jobParameters, String jobName, boolean isFullSync)
            throws JobExecutionException {
        return jobHandler.executeJob(tenantId, siteId, jobParameters, jobName);
    }
    
    public List <JobInfoUI> getJobList (Integer tenantId, List<String> jobNames) {
        if (logger.isDebugEnabled()) {
            logger.debug("Refreshing Job List for tenantId: " + tenantId);
        }
        List<Long> jobExecutionIds = jobExecutionDao.getRecentJobExecutionIds(Long.valueOf(tenantId), jobNames);
        List <JobInfoUI> recentJobs = new ArrayList<>();
        for (Long jobExecutionId : jobExecutionIds) {
            JobExecution jobExecution = jobExplorer.getJobExecution(jobExecutionId);
            recentJobs.add(new JobInfoUI(jobExecution));
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Done - Refreshing Job List for tenantId: " + tenantId);
        }

        return recentJobs;

    }
}

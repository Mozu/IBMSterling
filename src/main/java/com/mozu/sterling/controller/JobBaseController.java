package com.mozu.sterling.controller;

import java.sql.Timestamp;

import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;

import com.mozu.base.utils.ApplicationUtils;
import com.mozu.sterling.handler.BatchJobHandler;
import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.model.JobInfoUI;

public class JobBaseController {
    private static final Logger logger = LoggerFactory.getLogger(JobBaseController.class);

    @Autowired
    protected BatchJobHandler jobHandler;

    @Autowired
    protected ConfigHandler configHandler;

    protected JobInfoUI runJob(Integer tenantId, Integer siteId, String jobName) {
       return runJob(tenantId, siteId, jobName, null);
    }
    
    protected JobInfoUI runJob(Integer tenantId, Integer siteId, String jobName, Timestamp fromRunDate) {
        JobInfoUI jobInfo = null;

        if (ApplicationUtils.isAppEnabled(tenantId)) {
            JobExecution jobExecution = null;
            try {
                jobExecution = jobHandler.executeJob(tenantId, siteId, jobName, fromRunDate);
            } catch (JobExecutionException e) {
                throw new RuntimeException("Unable to run job: " + e.getMessage());
            }
            if (jobExecution != null) {
                jobInfo = new JobInfoUI(jobExecution);
                logger.info("Job Started for siteId: " + siteId);
            }
        } else {
            throw new RuntimeException(
                    "Unable to run job.  Application is disabled. Enable application and try again.");
        }
        return jobInfo;

    }

}

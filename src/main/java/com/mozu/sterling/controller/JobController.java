package com.mozu.sterling.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpException;
import org.joda.time.format.ISODateTimeFormat;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mozu.api.contracts.tenant.Site;
import com.mozu.api.contracts.tenant.Tenant;
import com.mozu.api.resources.platform.TenantResource;
import com.mozu.base.controllers.AdminControllerHelper;
import com.mozu.base.utils.ApplicationUtils;
import com.mozu.jobs.dao.JobExecutionDao;
import com.mozu.jobs.dao.SkipItemsDao;
import com.mozu.jobs.models.SkipItems;
import com.mozu.jobs.scheduler.JobScheduler;
import com.mozu.sterling.handler.BatchJobHandler;
import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.model.JobInfoUI;

@Controller
@RequestMapping("/api/job")
public class JobController {
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);
    @Autowired
    JobRepository jobRepository;

    @Autowired
    JobExplorer jobExplorer;

    @Autowired
    JobExecutionDao jobExecutionDao;

    @Autowired
    JobRegistry jobRegistry;

    @Autowired
    SkipItemsDao skipItemsDao;

    @Autowired
    BatchJobHandler jobHandler;

    @Autowired
    JobScheduler scheduler;

    @Autowired
    ConfigHandler configHandler;

    @RequestMapping(value = "status", method = RequestMethod.GET)
    public @ResponseBody
    List<JobInfoUI> getSyncStatus(@RequestParam(value = "ids", required = true) String executionIds)
            throws Exception {
        logger.info("Get Job Status");
        List<JobInfoUI> jobInfoList = new ArrayList<>();

        String[] jobIdStrs = executionIds.split(",");

        for (String execIdStr : jobIdStrs) {
            if (StringUtils.isNotBlank(execIdStr)) {
                Long executionId = Long.decode(execIdStr);
                jobInfoList.add(new JobInfoUI(jobExplorer.getJobExecution(executionId)));
            }
        }

        return jobInfoList;
    }

    @RequestMapping(value = "errors/{jobExecutionId}", method = RequestMethod.GET)
    public @ResponseBody
    List<String> getJobErrors(@PathVariable int jobExecutionId) throws Exception {
        logger.debug("Get errors for " + jobExecutionId);
        Long executionIdLong = Long.valueOf(jobExecutionId);

        List<SkipItems> skippedItems = skipItemsDao.getByJobExecutionId(executionIdLong);
        List<String> errorMsgs = new ArrayList<>();

        for (SkipItems skipItems : skippedItems) {
            errorMsgs.add(skipItems.getMsg());
        }

        return errorMsgs;
    }

    /**
     * Stop the given job from continuing execution.
     * 
     * @param jobExecutionId
     * @throws Exception
     */
    @RequestMapping(value = "{jobExecutionId}", method = RequestMethod.DELETE)
    public @ResponseBody
    void stopJob(@PathVariable int jobExecutionId) throws Exception {
        logger.info("Stop Job " + jobExecutionId);
        Long executionIdLong = Long.valueOf(jobExecutionId);
        JobExecution jobExecution = jobExplorer.getJobExecution(executionIdLong);
        if (jobExecution == null) {
            logger.warn("No running execution found for job=" + executionIdLong);
        } else {
            jobExecution.setStatus(BatchStatus.STOPPING);
            jobRepository.update(jobExecution);
        }
    }

    @RequestMapping(value="nextruntime", method = RequestMethod.GET)
    public @ResponseBody String getNextRunTime(@CookieValue (AdminControllerHelper.TENANT_ID_COOKIE) int tenantId, @RequestParam(value = "jobname", required = true) String jobIdentity) throws HttpException {
        if (ApplicationUtils.isAppEnabled(tenantId)) {
            TenantResource tenantResource = new TenantResource();
            List<Site> sites;
            Tenant tenant = null;
            try {
                tenant = tenantResource.getTenant(tenantId);
            } catch (Exception e) {
                logger.error("Unable to retrieve tenant " + tenantId);
                return null;
            }
            sites = tenant.getSites();

            Date nextFireTime = null;
            
            for (Site site : sites) {
                Integer siteId = site.getId();
                Trigger trigger;
                try {
                    trigger = scheduler.getTrigger(tenantId, siteId, jobIdentity);
                    if (trigger!=null) {
                        if (nextFireTime==null || trigger.getNextFireTime().before(nextFireTime)) {
                            nextFireTime = trigger.getNextFireTime();
                        }
                    }
                } catch (SchedulerException e) {
                    throw new HttpException("Unable to retrieve next runtime, internal error");
                }
            }
            if (nextFireTime==null) {
                return null;

            } else {
                return ISODateTimeFormat.dateTime().print(nextFireTime.getTime());
            }
        } else {
        	return null;
        }
    }

}

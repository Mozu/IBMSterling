package com.mozu.sterling.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mozu.base.controllers.AdminControllerHelper;
import com.mozu.base.utils.ApplicationUtils;
import com.mozu.sterling.handler.BatchJobHandler;
import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.model.JobInfoUI;
import com.mozu.sterling.model.Setting;

@Controller
@RequestMapping("/api/job")
public class OrderImportController extends JobBaseController {
    private static final Logger logger = LoggerFactory.getLogger(OrderImportController.class);
    
    @Autowired
    BatchJobHandler jobHandler;

    @Autowired
    protected ConfigHandler configHandler;

    @RequestMapping(value = "importOrder", method = RequestMethod.GET)
    public @ResponseBody
    List<JobInfoUI> getJobList(@CookieValue (AdminControllerHelper.TENANT_ID_COOKIE) int tenantId) {

        List<JobInfoUI> jobs =  jobHandler.getJobList(tenantId, Arrays.asList(JobInfoUI.ORDER_IMPORT_JOB));

        return jobs;
    }

    @RequestMapping(value = "importOrder", method = RequestMethod.POST)
    public @ResponseBody
    List<JobInfoUI> importOrders(@CookieValue (AdminControllerHelper.TENANT_ID_COOKIE) int tenantId, @RequestParam("fromDate") String fromDate) {
        logger.info("Starting Order Import for tenantID: " + tenantId);
        Setting settings = null;
        try {
            settings = configHandler.getSetting(tenantId);
        } catch (Exception e) {
            throw new RuntimeException("Unable to get settings for tenant: " + tenantId);
        }
        List<JobInfoUI> jobs = new ArrayList<JobInfoUI>();
        
        Timestamp importFromTimestamp = null;
        if (StringUtils.isNotBlank(fromDate)) {
          importFromTimestamp = Timestamp.valueOf(fromDate + " 00:00:00");
        }
        
        if (ApplicationUtils.isAppEnabled(tenantId)) {
            Map <String, String> siteMap = settings.getSiteMap();
            for (Entry<String, String> entry : siteMap.entrySet()) {
                jobs.add(runJob(tenantId, Integer.valueOf(entry.getKey()), JobInfoUI.ORDER_IMPORT_JOB, importFromTimestamp));
            }
        }
        if (jobs.size() == 0) {
            throw new RuntimeException("No sites were properly configured to successfully import orders.  Please check your settings and try again.");
        }
        return jobs;
    }
}

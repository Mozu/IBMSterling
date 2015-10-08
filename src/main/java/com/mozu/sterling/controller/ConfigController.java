package com.mozu.sterling.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mozu.api.ApiException;
import com.mozu.base.controllers.AdminControllerHelper;
import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.model.Setting;

@Controller
@RequestMapping("/api/config")
public class ConfigController {
    private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);
    
    @Autowired
    ConfigHandler configHandler;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Setting getSettings(@CookieValue (AdminControllerHelper.TENANT_ID_COOKIE) int tenantId,
    		final HttpServletRequest request ) throws Exception {
        logger.info("Get Settings....");
        
        Setting setting = configHandler.getSetting(tenantId);
        
        return setting;
    }

    @RequestMapping(method=RequestMethod.POST)
    public @ResponseBody void saveSettings(@CookieValue (AdminControllerHelper.TENANT_ID_COOKIE) int tenantId, @RequestBody String settingStr) throws Exception 
    {
        ObjectMapper mapper = new ObjectMapper();
        logger.info("Saving settings for tenant : "+tenantId);
        logger.info(settingStr);
        Setting setting = mapper.readValue(settingStr, Setting.class);
        
        configHandler.saveSettings(tenantId, setting);
        logger.info("Saving settings..done");
    }
    
    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public String handleApiException(HttpServletResponse response, ApiException exception) {
        if (exception.getApiError().getErrorCode().equals("FORBIDDEN")) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        } else {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return exception.getApiError().getMessage();
    }
}

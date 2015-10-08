package com.mozu.sterling.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mozu.base.controllers.AdminControllerHelper;

@Controller
@RequestMapping({"/","/index"})
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Value("${SharedSecret}")
    String sharedSecret;
    @Value("${spice}")
    String keySpice;
    @RequestMapping(method = RequestMethod.POST)
    public String index(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
        throws IOException {
        logger.debug ("Authorizing configuration request from Mozu...");

        // validate request
        try {
            AdminControllerHelper adh = new AdminControllerHelper(keySpice, sharedSecret);
            if (!adh.securityCheck(httpRequest, httpResponse)) {
                logger.warn("Not authorized");
                return "unauthorized";
            }
        } catch (Exception e) {
            logger.warn("Validation exception: " + e.getMessage());
            return "unauthorized";
        }
        logger.debug ("Done authorizing configuration request from Mozu...");
        
        return "start";
    }

}

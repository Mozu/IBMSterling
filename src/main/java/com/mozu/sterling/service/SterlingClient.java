package com.mozu.sterling.service;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ibm.sterling.afc.xapiclient.japi.XApi;
import com.ibm.sterling.afc.xapiclient.japi.XApiClientFactory;
import com.ibm.sterling.afc.xapiclient.japi.XApiEnvironment;
import com.mozu.sterling.model.Setting;

@Service
public class SterlingClient {
    private static final Logger logger = LoggerFactory.getLogger(SterlingClient.class);

    private DocumentBuilder docBuilder;

    public SterlingClient () throws Exception {
        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        try {
            docBuilder = fac.newDocumentBuilder();
        } catch (ParserConfigurationException pce){
            logger.error("Unable to create DocumentBuilder: " + pce.getMessage());
            throw pce;
        }
    }
    
    /**
     * invoke the sterling service using the given service name.
     * @param serviceName
     * @param inputDoc
     * @return
     * @throws Exception
     */
    public Document invoke(String serviceName, Document inputDoc, Setting setting) throws Exception {
        Document outputDocument = null;
        // create url, you can also put yif.httpapi.url in your
        // yifclient.properties in the resources.jar
        Map<String, String> props = new HashMap<>();
        props.put("yif.httpapi.url", setting.getSterlingUrl());
        XApi api = XApiClientFactory.getInstance().getApi("HTTP", props);

        Document environmentDoc = docBuilder.newDocument();
        Element envElement = environmentDoc.createElement("YFSEnvironment");
        envElement.setAttribute("userId", setting.getSterlingUserId());
        envElement.setAttribute("progId", setting.getSterlingUserId());
        environmentDoc.appendChild(envElement);

        XApiEnvironment env = null;
        String sessionId = null;
        try {
            env = api.createEnvironment(environmentDoc);
            Document loginInput = docBuilder.newDocument();
            Element loginElement = loginInput.createElement("Login");
            loginElement.setAttribute("LoginID", setting.getSterlingUserId());
            loginElement.setAttribute("Password", setting.getSterlingPassword());
            loginInput.appendChild(loginElement);

            // Using api.invoke to call login api
            Document loginDoc = api.invoke(env, "login", loginInput);

            env.setTokenID(loginDoc.getDocumentElement().getAttribute("UserToken"));
            sessionId = loginDoc.getDocumentElement().getAttribute("SessionId");
            outputDocument = api.invoke(env, serviceName, inputDoc);
        } catch (Exception e) {
            logger.error("Unable to complete transaction to Sterling: " + e.getMessage());
            throw e;
        } finally {
            if (env != null) {
                Document logoutDoc = docBuilder.newDocument();
                Element logoutElement = logoutDoc.createElement("registerLogout");
                logoutElement.setAttribute("UserId", env.getUserId());
                logoutElement.setAttribute("SessionId", sessionId);
                logoutDoc.appendChild(logoutElement);
    
                // Using api.invoke to call registerLogout api
                api.invoke(env, "registerLogout", logoutDoc);
                api.releaseEnvironment(env);
            }
        }

        return outputDocument;
    }
    
    public DocumentBuilder getDocumentBuilder () {
        return this.docBuilder;
    }
}

/*
 * IBM Confidential 
 *
 * OCO Source Materials
 *
 * IBM Sterling Selling and Fullfillment Suite
 *
 * (c) Copyright IBM Corp. 2001, 2013 All Rights Reserved.
 *
 * The source code for this program is not published or otherwise divested of its trade secrets,
 * irrespective of what has been deposited with the U.S. Copyright Office.
 */

package com.mozu.sterling;

import static org.junit.Assert.assertNotNull;

import java.io.File;

/**
 * TestJAXClient.java
 *
 *
 * Created: Mon Sep 24 16:42:54 2001
 *
 * @author <a href="mailto: "</a>
 * @version
 */

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ibm.sterling.afc.xapiclient.japi.XApi;
import com.ibm.sterling.afc.xapiclient.japi.XApiClientFactory;
import com.ibm.sterling.afc.xapiclient.japi.XApiEnvironment;

/**
 * This class is an example of how to invoke business apis exposed by Sterling
 * This class is written as a junit test case. This test case invokes a
 * createOrder API using the sample order_Create.xml supplied within this
 * directory.
 *
 */

public class TestXApiClient {

    /**
     * SApi is the primary interface to invoke Sterling APIs. Refer to the
     * javadocs of this interface for a documented list of APIs supported by
     * this interface, and the sample input XMLs.
     *
     */

    private XApi api;

    /**
     * The document builder is used to create an instance of org.w3c.Document
     *
     */
    private DocumentBuilder docBuilder;

    /**
     * This is the environment that is needed to invoke any Sterling APIS. The
     * environment handle is obtained by calling the createEnvironment method on
     * SApi. The createEnvironment method takes a Document as the sole input
     * parameter.
     */

    private XApiEnvironment env;

    private String sessionId;

    /**
     * This function initializes the api handle and the environment handle.
     * <p>
     * The api handle is initialized by calling the getApi function on the
     * SAPIClientFactory. The getApi function shields the programmer from the
     * underlying transport details (such as HTTP, EJB). The getApi function
     * inspects the yifclient.properties file to determine the appropriate
     * protocol handler to instantiate. For instance, if the client needs to
     * communicate with the Sterling application via HTTP, he simply needs to
     * set the yif.apifactory.protocol to HTTP and the the yif.httpapi.url to
     * the appropriate value in yifclient.properties The details of opening an
     * HTTP Connection and posting the relevant data to the application, and
     * returning the document as return value of the api invocation are handled
     * transparent to the programmer.
     * <p>
     * SImilarly, for EJB , the programmer simply needs to set up the protocol
     * value as EJB and set up the java.naming.provider.url, and the
     * java.naming.factory.initial properties appropriately in
     * yifclient.properties. The api handle will retrieve the appropriate EJB
     * interface by performing a JNDI lookup.
     * <p>
     * In either mode of transport, EJB or HTTP, the programmer is shielded from
     * the complexity of opening an HTTP connection, or doing a JNDI lookup to
     * retreive an EJB handle. As far as the programmer is concerned, it looks
     * as though the API is being executed in the same VM as the client's
     * program.
     * 
     * @exception Exception
     *                if an error occurs
     */
    @Before
    public void setUp() throws Exception {
        /*
         * Testing HTTP
         */

        // create url, you can also put yif.httpapi.url in your
        // yifclient.properties in the resources.jar
        Map<String, String> props = new HashMap<>();
        props.put("yif.httpapi.url", "http://50.23.47.110:9080/smcfs/interop/InteropHttpServlet");
        api = XApiClientFactory.getInstance().getApi("HTTP", props);

        DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
        docBuilder = fac.newDocumentBuilder();
        Document environmentDoc = docBuilder.newDocument();
        Element envElement = environmentDoc.createElement("YFSEnvironment");
        envElement.setAttribute("userId", "admin");
        envElement.setAttribute("progId", "password");
        environmentDoc.appendChild(envElement);

        env = api.createEnvironment(environmentDoc);

        Document loginInput = docBuilder.newDocument();
        Element loginElement = loginInput.createElement("Login");
        loginElement.setAttribute("LoginID", "admin");
        loginElement.setAttribute("Password", "password");
        loginInput.appendChild(loginElement);

        // Using api.invoke to call login api
        Document loginDoc = api.invoke(env, "login", loginInput);

        env.setTokenID(loginDoc.getDocumentElement().getAttribute("UserToken"));
        sessionId = loginDoc.getDocumentElement().getAttribute("SessionId");
    }

    @After
    public void tearDown() throws Exception {
        Document logoutDoc = docBuilder.newDocument();
        Element logoutElement = logoutDoc.createElement("registerLogout");
        logoutElement.setAttribute("UserId", env.getUserId());
        logoutElement.setAttribute("SessionId", sessionId);
        logoutDoc.appendChild(logoutElement);

        // Using api.invoke to call registerLogout api
        api.invoke(env, "registerLogout", logoutDoc);
        api.releaseEnvironment(env);
    }

    /**
     * This function invokes a getLocaleList API.
     *
     * @exception Exception
     *                if an error occurs
     */
    @Test
    public void testGetLocaleList() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("getLocaleList.xml").getFile());
        Document createOrderDoc = docBuilder.parse(file);
        // Using api.invoke to call createOrder api
        Document getLocaleListReturn = api.invoke(env, "getLocaleList", createOrderDoc);

        assertNotNull("GetLocaleList Cannot return a null document", getLocaleListReturn);
    }

    /**
     * This function invokes a getLocaleList API.
     *
     * @exception Exception
     *                if an error occurs
     */
    @Ignore
    public void testCreateOrderList() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("getExceptionList.xml").getFile());
        Document createOrderDoc = docBuilder.parse(file);

        // Using api.invoke to call createOrder api
        Document getCreateOrderReturn = api.invoke(env, "createOrder", createOrderDoc);

        assertNotNull("GetLocaleList Cannot return a null document", getCreateOrderReturn);
    }

    /**
     * This function invokes a getExceptionList api.
     *
     * @exception Exception
     *                if an error occurs
     */
    @Test
    public void testGetExceptionList() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("getExceptionList.xml").getFile());

        Document createOrderDoc = docBuilder.parse(file);

        // Using api.invoke to call createOrder api
        Document getExceptionListReturn = api.invoke(env, "getExceptionList", createOrderDoc);

        assertNotNull("GetExceptionList Cannot return a null document", getExceptionListReturn);
    }

}// TestJAXClient

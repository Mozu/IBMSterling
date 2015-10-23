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

package com.mozu.sterling.service;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import com.mozu.sterling.model.Setting;

/**
 * This class is an example of how to invoke business apis exposed by Sterling
 * This class is written as a junit test case. This test case invokes a
 * createOrder API using the sample order_Create.xml supplied within this
 * directory.
 *
 */

public class SterlingClientTest {
    private Setting setting;
    private SterlingClient sterlingClient;
    private DocumentBuilder docBuilder;
    /**
     * Setup
     */
    @Before
    public void setUp() throws Exception {
        setting = new Setting();
        setting.setSterlingUrl("http://50.23.47.110:9080/smcfs/interop/InteropHttpServlet");
        setting.setSterlingUserId("admin");
        setting.setSterlingPassword("password");
        
        sterlingClient = new SterlingClient();
        docBuilder = sterlingClient.getDocumentBuilder();
    }

    @After
    public void tearDown() throws Exception {
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
        Document getLocaleListReturn = sterlingClient.invoke("getLocaleList", createOrderDoc, setting);

        assertNotNull("GetLocaleList Cannot return a null document", getLocaleListReturn);
    }

    /**
     * This function invokes a getLocaleList API.
     *
     * @exception Exception
     *                if an error occurs
     */
    @Test
    public void testCreateOrder() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("order_Create2.xml").getFile());
        Document createOrderDoc = docBuilder.parse(file);

        // Using api.invoke to call createOrder api
        Document getCreateOrderReturn = sterlingClient.invoke("createOrder", createOrderDoc, setting);

        assertNotNull("CreateOrder Cannot return a null document", getCreateOrderReturn);
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
        Document getExceptionListReturn = sterlingClient.invoke("getExceptionList", createOrderDoc, setting);

        assertNotNull("GetExceptionList Cannot return a null document", getExceptionListReturn);
    }

    @Test
    public void testGetOrganizationHierarchy() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("organizationList.xml").getFile());

        Document createOrderDoc = docBuilder.parse(file);

        // Using api.invoke to call createOrder api
        Document getExceptionListReturn = sterlingClient.invoke("getOrganizationList", createOrderDoc, setting);

        assertNotNull("GetExceptionList Cannot return a null document", getExceptionListReturn);
    }

}// TestJAXClient

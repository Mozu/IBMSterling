package com.mozu.sterling.service;

import java.util.regex.Pattern;

import junit.framework.TestCase;

public class RegexTest extends TestCase{
    public void testRegex() throws Exception {
        String paragraph = " B .  This.is.crazy#@ Carp**BDx.com.     ";
        Pattern pattern = Pattern.compile("^[. ]+|([^a-zA-Z0-9\\-_.()])|[. ]+$");
        
        System.out.println("Regex removes everything but letters:'" + pattern.matcher(paragraph).replaceAll ("") + "'");
    }
}

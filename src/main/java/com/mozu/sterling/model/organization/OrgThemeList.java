//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.04 at 09:40:37 AM CST 
//


package com.mozu.sterling.model.organization;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "orgTheme"
})
@XmlRootElement(name = "OrgThemeList")
public class OrgThemeList {

    @XmlElement(name = "OrgTheme")
    protected List<OrgTheme> orgTheme;

    /**
     * Gets the value of the orgTheme property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orgTheme property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrgTheme().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrgTheme }
     * 
     * 
     */
    public List<OrgTheme> getOrgTheme() {
        if (orgTheme == null) {
            orgTheme = new ArrayList<OrgTheme>();
        }
        return this.orgTheme;
    }

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.04 at 09:40:37 AM CST 
//


package com.mozu.sterling.model.organization;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "orgProtocolReferenceList"
})
@XmlRootElement(name = "OrgProtocol")
public class OrgProtocol {

    @XmlAttribute(name = "OrgProtocolKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String orgProtocolKey;
    @XmlAttribute(name = "OrganizationKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String organizationKey;
    @XmlAttribute(name = "Password")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String password;
    @XmlAttribute(name = "ProtocolKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String protocolKey;
    @XmlAttribute(name = "ServerIp")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String serverIp;
    @XmlAttribute(name = "ServerPort")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String serverPort;
    @XmlAttribute(name = "UserId")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String userId;
    @XmlElement(name = "OrgProtocolReferenceList")
    protected OrgProtocolReferenceList orgProtocolReferenceList;

    /**
     * Gets the value of the orgProtocolKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgProtocolKey() {
        return orgProtocolKey;
    }

    /**
     * Sets the value of the orgProtocolKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgProtocolKey(String value) {
        this.orgProtocolKey = value;
    }

    /**
     * Gets the value of the organizationKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationKey() {
        return organizationKey;
    }

    /**
     * Sets the value of the organizationKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationKey(String value) {
        this.organizationKey = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the protocolKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProtocolKey() {
        return protocolKey;
    }

    /**
     * Sets the value of the protocolKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProtocolKey(String value) {
        this.protocolKey = value;
    }

    /**
     * Gets the value of the serverIp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServerIp() {
        return serverIp;
    }

    /**
     * Sets the value of the serverIp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServerIp(String value) {
        this.serverIp = value;
    }

    /**
     * Gets the value of the serverPort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServerPort() {
        return serverPort;
    }

    /**
     * Sets the value of the serverPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServerPort(String value) {
        this.serverPort = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserId(String value) {
        this.userId = value;
    }

    /**
     * Gets the value of the orgProtocolReferenceList property.
     * 
     * @return
     *     possible object is
     *     {@link OrgProtocolReferenceList }
     *     
     */
    public OrgProtocolReferenceList getOrgProtocolReferenceList() {
        return orgProtocolReferenceList;
    }

    /**
     * Sets the value of the orgProtocolReferenceList property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrgProtocolReferenceList }
     *     
     */
    public void setOrgProtocolReferenceList(OrgProtocolReferenceList value) {
        this.orgProtocolReferenceList = value;
    }

}

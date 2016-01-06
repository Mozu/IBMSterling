//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.31 at 01:35:41 PM CST 
//


package com.mozu.sterling.model.order;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "ChargeBreakup")
public class ChargeBreakup {

    @XmlAttribute(name = "AwardId")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String awardId;
    @XmlAttribute(name = "AwardType")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String awardType;
    @XmlAttribute(name = "ChargeAmount")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String chargeAmount;
    @XmlAttribute(name = "ChargeBreakupKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String chargeBreakupKey;
    @XmlAttribute(name = "DerivedFromOrderHeaderKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String derivedFromOrderHeaderKey;
    @XmlAttribute(name = "DerivedFromOrderLineKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String derivedFromOrderLineKey;
    @XmlAttribute(name = "OrderHeaderKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String orderHeaderKey;
    @XmlAttribute(name = "a-dtype")
    protected List<String> aDtype;

    /**
     * Gets the value of the awardId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwardId() {
        return awardId;
    }

    /**
     * Sets the value of the awardId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwardId(String value) {
        this.awardId = value;
    }

    /**
     * Gets the value of the awardType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwardType() {
        return awardType;
    }

    /**
     * Sets the value of the awardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwardType(String value) {
        this.awardType = value;
    }

    /**
     * Gets the value of the chargeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeAmount() {
        return chargeAmount;
    }

    /**
     * Sets the value of the chargeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeAmount(String value) {
        this.chargeAmount = value;
    }

    /**
     * Gets the value of the chargeBreakupKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeBreakupKey() {
        return chargeBreakupKey;
    }

    /**
     * Sets the value of the chargeBreakupKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeBreakupKey(String value) {
        this.chargeBreakupKey = value;
    }

    /**
     * Gets the value of the derivedFromOrderHeaderKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDerivedFromOrderHeaderKey() {
        return derivedFromOrderHeaderKey;
    }

    /**
     * Sets the value of the derivedFromOrderHeaderKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDerivedFromOrderHeaderKey(String value) {
        this.derivedFromOrderHeaderKey = value;
    }

    /**
     * Gets the value of the derivedFromOrderLineKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDerivedFromOrderLineKey() {
        return derivedFromOrderLineKey;
    }

    /**
     * Sets the value of the derivedFromOrderLineKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDerivedFromOrderLineKey(String value) {
        this.derivedFromOrderLineKey = value;
    }

    /**
     * Gets the value of the orderHeaderKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderHeaderKey() {
        return orderHeaderKey;
    }

    /**
     * Sets the value of the orderHeaderKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderHeaderKey(String value) {
        this.orderHeaderKey = value;
    }

    /**
     * Gets the value of the aDtype property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the aDtype property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getADtype().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getADtype() {
        if (aDtype == null) {
            aDtype = new ArrayList<String>();
        }
        return this.aDtype;
    }

}
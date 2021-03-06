//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.10 at 12:28:23 PM CST 
//


package com.mozu.sterling.model.shipment;

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
@XmlRootElement(name = "PricingRuleTargetItem")
public class PricingRuleTargetItem {

    @XmlAttribute(name = "IncludeOrExclude")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String includeOrExclude;
    @XmlAttribute(name = "PricingRuleKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String pricingRuleKey;
    @XmlAttribute(name = "PricingRuleTargetItemKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String pricingRuleTargetItemKey;
    @XmlAttribute(name = "Quantity")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String quantity;
    @XmlAttribute(name = "TargetItemDesc")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String targetItemDesc;
    @XmlAttribute(name = "TargetItemID")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String targetItemID;
    @XmlAttribute(name = "TargetItemShortDesc")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String targetItemShortDesc;
    @XmlAttribute(name = "TargetUnitOfMeasure")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String targetUnitOfMeasure;
    @XmlAttribute(name = "a-dtype")
    protected List<String> aDtype;

    /**
     * Gets the value of the includeOrExclude property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncludeOrExclude() {
        return includeOrExclude;
    }

    /**
     * Sets the value of the includeOrExclude property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncludeOrExclude(String value) {
        this.includeOrExclude = value;
    }

    /**
     * Gets the value of the pricingRuleKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPricingRuleKey() {
        return pricingRuleKey;
    }

    /**
     * Sets the value of the pricingRuleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPricingRuleKey(String value) {
        this.pricingRuleKey = value;
    }

    /**
     * Gets the value of the pricingRuleTargetItemKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPricingRuleTargetItemKey() {
        return pricingRuleTargetItemKey;
    }

    /**
     * Sets the value of the pricingRuleTargetItemKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPricingRuleTargetItemKey(String value) {
        this.pricingRuleTargetItemKey = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQuantity(String value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the targetItemDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetItemDesc() {
        return targetItemDesc;
    }

    /**
     * Sets the value of the targetItemDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetItemDesc(String value) {
        this.targetItemDesc = value;
    }

    /**
     * Gets the value of the targetItemID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetItemID() {
        return targetItemID;
    }

    /**
     * Sets the value of the targetItemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetItemID(String value) {
        this.targetItemID = value;
    }

    /**
     * Gets the value of the targetItemShortDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetItemShortDesc() {
        return targetItemShortDesc;
    }

    /**
     * Sets the value of the targetItemShortDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetItemShortDesc(String value) {
        this.targetItemShortDesc = value;
    }

    /**
     * Gets the value of the targetUnitOfMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetUnitOfMeasure() {
        return targetUnitOfMeasure;
    }

    /**
     * Sets the value of the targetUnitOfMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetUnitOfMeasure(String value) {
        this.targetUnitOfMeasure = value;
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

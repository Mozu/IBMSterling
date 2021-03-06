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
@XmlRootElement(name = "PricingRuleCategory")
public class PricingRuleCategory {

    @XmlAttribute(name = "IncludeOrExclude")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String includeOrExclude;
    @XmlAttribute(name = "PricingRuleCategoryKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String pricingRuleCategoryKey;
    @XmlAttribute(name = "Quantity")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String quantity;
    @XmlAttribute(name = "TriggerCategoryID")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String triggerCategoryID;
    @XmlAttribute(name = "TriggerCategoryPath")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String triggerCategoryPath;
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
     * Gets the value of the pricingRuleCategoryKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPricingRuleCategoryKey() {
        return pricingRuleCategoryKey;
    }

    /**
     * Sets the value of the pricingRuleCategoryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPricingRuleCategoryKey(String value) {
        this.pricingRuleCategoryKey = value;
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
     * Gets the value of the triggerCategoryID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTriggerCategoryID() {
        return triggerCategoryID;
    }

    /**
     * Sets the value of the triggerCategoryID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTriggerCategoryID(String value) {
        this.triggerCategoryID = value;
    }

    /**
     * Gets the value of the triggerCategoryPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTriggerCategoryPath() {
        return triggerCategoryPath;
    }

    /**
     * Sets the value of the triggerCategoryPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTriggerCategoryPath(String value) {
        this.triggerCategoryPath = value;
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

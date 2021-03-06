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
@XmlRootElement(name = "PricingRuleItem")
public class PricingRuleItem {

    @XmlAttribute(name = "Description")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String description;
    @XmlAttribute(name = "IncludeOrExclude")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String includeOrExclude;
    @XmlAttribute(name = "ItemID")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String itemID;
    @XmlAttribute(name = "PricingRuleItemKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String pricingRuleItemKey;
    @XmlAttribute(name = "PricingRuleKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String pricingRuleKey;
    @XmlAttribute(name = "Quantity")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String quantity;
    @XmlAttribute(name = "ShortDescription")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String shortDescription;
    @XmlAttribute(name = "UnitOfMeasure")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String unitOfMeasure;
    @XmlAttribute(name = "a-dtype")
    protected List<String> aDtype;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

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
     * Gets the value of the itemID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemID() {
        return itemID;
    }

    /**
     * Sets the value of the itemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemID(String value) {
        this.itemID = value;
    }

    /**
     * Gets the value of the pricingRuleItemKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPricingRuleItemKey() {
        return pricingRuleItemKey;
    }

    /**
     * Sets the value of the pricingRuleItemKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPricingRuleItemKey(String value) {
        this.pricingRuleItemKey = value;
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
     * Gets the value of the shortDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * Sets the value of the shortDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortDescription(String value) {
        this.shortDescription = value;
    }

    /**
     * Gets the value of the unitOfMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Sets the value of the unitOfMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitOfMeasure(String value) {
        this.unitOfMeasure = value;
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

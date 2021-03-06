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
    "itemServiceAssocSkillList"
})
@XmlRootElement(name = "ItemServiceAssoc")
public class ItemServiceAssoc {

    @XmlAttribute(name = "CategoryKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String categoryKey;
    @XmlAttribute(name = "HoldSchedTillCompletion")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String holdSchedTillCompletion;
    @XmlAttribute(name = "ItemKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String itemKey;
    @XmlAttribute(name = "ItemServiceAssocKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String itemServiceAssocKey;
    @XmlAttribute(name = "PricingProductQuantity")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String pricingProductQuantity;
    @XmlAttribute(name = "PricingServiceQuantity")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String pricingServiceQuantity;
    @XmlAttribute(name = "ProductQuantity")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String productQuantity;
    @XmlAttribute(name = "ServiceItemDesc")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String serviceItemDesc;
    @XmlAttribute(name = "ServiceItemGroupCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String serviceItemGroupCode;
    @XmlAttribute(name = "ServiceItemId")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String serviceItemId;
    @XmlAttribute(name = "ServiceQuantity")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String serviceQuantity;
    @XmlAttribute(name = "ServiceUOM")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String serviceUOM;
    @XmlAttribute(name = "TimeOffsetInMinutes")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String timeOffsetInMinutes;
    @XmlAttribute(name = "a-dtype")
    protected List<String> aDtype;
    @XmlElement(name = "ItemServiceAssocSkillList", required = true)
    protected ItemServiceAssocSkillList itemServiceAssocSkillList;

    /**
     * Gets the value of the categoryKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategoryKey() {
        return categoryKey;
    }

    /**
     * Sets the value of the categoryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategoryKey(String value) {
        this.categoryKey = value;
    }

    /**
     * Gets the value of the holdSchedTillCompletion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHoldSchedTillCompletion() {
        return holdSchedTillCompletion;
    }

    /**
     * Sets the value of the holdSchedTillCompletion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHoldSchedTillCompletion(String value) {
        this.holdSchedTillCompletion = value;
    }

    /**
     * Gets the value of the itemKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemKey() {
        return itemKey;
    }

    /**
     * Sets the value of the itemKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemKey(String value) {
        this.itemKey = value;
    }

    /**
     * Gets the value of the itemServiceAssocKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemServiceAssocKey() {
        return itemServiceAssocKey;
    }

    /**
     * Sets the value of the itemServiceAssocKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemServiceAssocKey(String value) {
        this.itemServiceAssocKey = value;
    }

    /**
     * Gets the value of the pricingProductQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPricingProductQuantity() {
        return pricingProductQuantity;
    }

    /**
     * Sets the value of the pricingProductQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPricingProductQuantity(String value) {
        this.pricingProductQuantity = value;
    }

    /**
     * Gets the value of the pricingServiceQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPricingServiceQuantity() {
        return pricingServiceQuantity;
    }

    /**
     * Sets the value of the pricingServiceQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPricingServiceQuantity(String value) {
        this.pricingServiceQuantity = value;
    }

    /**
     * Gets the value of the productQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductQuantity() {
        return productQuantity;
    }

    /**
     * Sets the value of the productQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductQuantity(String value) {
        this.productQuantity = value;
    }

    /**
     * Gets the value of the serviceItemDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceItemDesc() {
        return serviceItemDesc;
    }

    /**
     * Sets the value of the serviceItemDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceItemDesc(String value) {
        this.serviceItemDesc = value;
    }

    /**
     * Gets the value of the serviceItemGroupCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceItemGroupCode() {
        return serviceItemGroupCode;
    }

    /**
     * Sets the value of the serviceItemGroupCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceItemGroupCode(String value) {
        this.serviceItemGroupCode = value;
    }

    /**
     * Gets the value of the serviceItemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceItemId() {
        return serviceItemId;
    }

    /**
     * Sets the value of the serviceItemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceItemId(String value) {
        this.serviceItemId = value;
    }

    /**
     * Gets the value of the serviceQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceQuantity() {
        return serviceQuantity;
    }

    /**
     * Sets the value of the serviceQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceQuantity(String value) {
        this.serviceQuantity = value;
    }

    /**
     * Gets the value of the serviceUOM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceUOM() {
        return serviceUOM;
    }

    /**
     * Sets the value of the serviceUOM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceUOM(String value) {
        this.serviceUOM = value;
    }

    /**
     * Gets the value of the timeOffsetInMinutes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeOffsetInMinutes() {
        return timeOffsetInMinutes;
    }

    /**
     * Sets the value of the timeOffsetInMinutes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeOffsetInMinutes(String value) {
        this.timeOffsetInMinutes = value;
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

    /**
     * Gets the value of the itemServiceAssocSkillList property.
     * 
     * @return
     *     possible object is
     *     {@link ItemServiceAssocSkillList }
     *     
     */
    public ItemServiceAssocSkillList getItemServiceAssocSkillList() {
        return itemServiceAssocSkillList;
    }

    /**
     * Sets the value of the itemServiceAssocSkillList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemServiceAssocSkillList }
     *     
     */
    public void setItemServiceAssocSkillList(ItemServiceAssocSkillList value) {
        this.itemServiceAssocSkillList = value;
    }

}

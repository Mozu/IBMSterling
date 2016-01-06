//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.31 at 01:35:41 PM CST 
//


package com.mozu.sterling.model.order;

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
@XmlRootElement(name = "ChargeCategoryDetails")
public class ChargeCategoryDetails {

    @XmlAttribute(name = "ChargeCategoryKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String chargeCategoryKey;
    @XmlAttribute(name = "ConsiderForProfitMargin")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String considerForProfitMargin;
    @XmlAttribute(name = "Description")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String description;
    @XmlAttribute(name = "IsBillable")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String isBillable;
    @XmlAttribute(name = "IsDiscount")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String isDiscount;
    @XmlAttribute(name = "IsRefundable")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String isRefundable;

    /**
     * Gets the value of the chargeCategoryKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeCategoryKey() {
        return chargeCategoryKey;
    }

    /**
     * Sets the value of the chargeCategoryKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeCategoryKey(String value) {
        this.chargeCategoryKey = value;
    }

    /**
     * Gets the value of the considerForProfitMargin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsiderForProfitMargin() {
        return considerForProfitMargin;
    }

    /**
     * Sets the value of the considerForProfitMargin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsiderForProfitMargin(String value) {
        this.considerForProfitMargin = value;
    }

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
     * Gets the value of the isBillable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsBillable() {
        return isBillable;
    }

    /**
     * Sets the value of the isBillable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsBillable(String value) {
        this.isBillable = value;
    }

    /**
     * Gets the value of the isDiscount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsDiscount() {
        return isDiscount;
    }

    /**
     * Sets the value of the isDiscount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsDiscount(String value) {
        this.isDiscount = value;
    }

    /**
     * Gets the value of the isRefundable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsRefundable() {
        return isRefundable;
    }

    /**
     * Sets the value of the isRefundable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsRefundable(String value) {
        this.isRefundable = value;
    }

}

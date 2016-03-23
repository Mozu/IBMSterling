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
@XmlRootElement(name = "AdditionalDate")
public class AdditionalDate {

    @XmlAttribute(name = "ActualDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String actualDate;
    @XmlAttribute(name = "AdditionalDateKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String additionalDateKey;
    @XmlAttribute(name = "DateTypeId")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String dateTypeId;
    @XmlAttribute(name = "ExpectedDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String expectedDate;
    @XmlAttribute(name = "FromActualDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String fromActualDate;
    @XmlAttribute(name = "FromExpectedDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String fromExpectedDate;
    @XmlAttribute(name = "FromRequestedDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String fromRequestedDate;
    @XmlAttribute(name = "ReferenceKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String referenceKey;
    @XmlAttribute(name = "ReferenceType")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String referenceType;
    @XmlAttribute(name = "RequestedDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String requestedDate;
    @XmlAttribute(name = "ToActualDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String toActualDate;
    @XmlAttribute(name = "ToExpectedDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String toExpectedDate;
    @XmlAttribute(name = "ToRequestedDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String toRequestedDate;
    @XmlAttribute(name = "a-dtype")
    protected List<String> aDtype;

    /**
     * Gets the value of the actualDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActualDate() {
        return actualDate;
    }

    /**
     * Sets the value of the actualDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActualDate(String value) {
        this.actualDate = value;
    }

    /**
     * Gets the value of the additionalDateKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalDateKey() {
        return additionalDateKey;
    }

    /**
     * Sets the value of the additionalDateKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalDateKey(String value) {
        this.additionalDateKey = value;
    }

    /**
     * Gets the value of the dateTypeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateTypeId() {
        return dateTypeId;
    }

    /**
     * Sets the value of the dateTypeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateTypeId(String value) {
        this.dateTypeId = value;
    }

    /**
     * Gets the value of the expectedDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpectedDate() {
        return expectedDate;
    }

    /**
     * Sets the value of the expectedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpectedDate(String value) {
        this.expectedDate = value;
    }

    /**
     * Gets the value of the fromActualDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromActualDate() {
        return fromActualDate;
    }

    /**
     * Sets the value of the fromActualDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromActualDate(String value) {
        this.fromActualDate = value;
    }

    /**
     * Gets the value of the fromExpectedDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromExpectedDate() {
        return fromExpectedDate;
    }

    /**
     * Sets the value of the fromExpectedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromExpectedDate(String value) {
        this.fromExpectedDate = value;
    }

    /**
     * Gets the value of the fromRequestedDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFromRequestedDate() {
        return fromRequestedDate;
    }

    /**
     * Sets the value of the fromRequestedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFromRequestedDate(String value) {
        this.fromRequestedDate = value;
    }

    /**
     * Gets the value of the referenceKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceKey() {
        return referenceKey;
    }

    /**
     * Sets the value of the referenceKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceKey(String value) {
        this.referenceKey = value;
    }

    /**
     * Gets the value of the referenceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceType() {
        return referenceType;
    }

    /**
     * Sets the value of the referenceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceType(String value) {
        this.referenceType = value;
    }

    /**
     * Gets the value of the requestedDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequestedDate() {
        return requestedDate;
    }

    /**
     * Sets the value of the requestedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequestedDate(String value) {
        this.requestedDate = value;
    }

    /**
     * Gets the value of the toActualDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToActualDate() {
        return toActualDate;
    }

    /**
     * Sets the value of the toActualDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToActualDate(String value) {
        this.toActualDate = value;
    }

    /**
     * Gets the value of the toExpectedDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToExpectedDate() {
        return toExpectedDate;
    }

    /**
     * Sets the value of the toExpectedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToExpectedDate(String value) {
        this.toExpectedDate = value;
    }

    /**
     * Gets the value of the toRequestedDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToRequestedDate() {
        return toRequestedDate;
    }

    /**
     * Sets the value of the toRequestedDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToRequestedDate(String value) {
        this.toRequestedDate = value;
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
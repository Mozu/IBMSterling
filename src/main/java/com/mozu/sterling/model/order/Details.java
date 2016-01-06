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
    "kitDetails"
})
@XmlRootElement(name = "Details")
public class Details {

    @XmlAttribute(name = "ExpectedDeliveryDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String expectedDeliveryDate;
    @XmlAttribute(name = "ExpectedShipmentDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String expectedShipmentDate;
    @XmlAttribute(name = "OverrideItemID")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String overrideItemID;
    @XmlAttribute(name = "OverrideProductClass")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String overrideProductClass;
    @XmlAttribute(name = "OverrideUnitOfMeasure")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String overrideUnitOfMeasure;
    @XmlAttribute(name = "ReceivingNode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String receivingNode;
    @XmlAttribute(name = "ShipByDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String shipByDate;
    @XmlAttribute(name = "TagNumber")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String tagNumber;
    @XmlAttribute(name = "a-dtype")
    protected List<String> aDtype;
    @XmlElement(name = "KitDetails")
    protected KitDetails kitDetails;

    /**
     * Gets the value of the expectedDeliveryDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    /**
     * Sets the value of the expectedDeliveryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpectedDeliveryDate(String value) {
        this.expectedDeliveryDate = value;
    }

    /**
     * Gets the value of the expectedShipmentDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpectedShipmentDate() {
        return expectedShipmentDate;
    }

    /**
     * Sets the value of the expectedShipmentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpectedShipmentDate(String value) {
        this.expectedShipmentDate = value;
    }

    /**
     * Gets the value of the overrideItemID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOverrideItemID() {
        return overrideItemID;
    }

    /**
     * Sets the value of the overrideItemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOverrideItemID(String value) {
        this.overrideItemID = value;
    }

    /**
     * Gets the value of the overrideProductClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOverrideProductClass() {
        return overrideProductClass;
    }

    /**
     * Sets the value of the overrideProductClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOverrideProductClass(String value) {
        this.overrideProductClass = value;
    }

    /**
     * Gets the value of the overrideUnitOfMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOverrideUnitOfMeasure() {
        return overrideUnitOfMeasure;
    }

    /**
     * Sets the value of the overrideUnitOfMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOverrideUnitOfMeasure(String value) {
        this.overrideUnitOfMeasure = value;
    }

    /**
     * Gets the value of the receivingNode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReceivingNode() {
        return receivingNode;
    }

    /**
     * Sets the value of the receivingNode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReceivingNode(String value) {
        this.receivingNode = value;
    }

    /**
     * Gets the value of the shipByDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipByDate() {
        return shipByDate;
    }

    /**
     * Sets the value of the shipByDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipByDate(String value) {
        this.shipByDate = value;
    }

    /**
     * Gets the value of the tagNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTagNumber() {
        return tagNumber;
    }

    /**
     * Sets the value of the tagNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTagNumber(String value) {
        this.tagNumber = value;
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
     * Gets the value of the kitDetails property.
     * 
     * @return
     *     possible object is
     *     {@link KitDetails }
     *     
     */
    public KitDetails getKitDetails() {
        return kitDetails;
    }

    /**
     * Sets the value of the kitDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link KitDetails }
     *     
     */
    public void setKitDetails(KitDetails value) {
        this.kitDetails = value;
    }

}
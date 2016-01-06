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
@XmlRootElement(name = "SafetyFactorDefinition")
public class SafetyFactorDefinition {

    @XmlAttribute(name = "DeliveryMethod")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String deliveryMethod;
    @XmlAttribute(name = "FutureSafetyFactorPercentage")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String futureSafetyFactorPercentage;
    @XmlAttribute(name = "OnhandSafetyFactorPercentage")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String onhandSafetyFactorPercentage;
    @XmlAttribute(name = "OnhandSafetyFactorQuantity")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String onhandSafetyFactorQuantity;
    @XmlAttribute(name = "a-dtype")
    protected List<String> aDtype;

    /**
     * Gets the value of the deliveryMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    /**
     * Sets the value of the deliveryMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryMethod(String value) {
        this.deliveryMethod = value;
    }

    /**
     * Gets the value of the futureSafetyFactorPercentage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFutureSafetyFactorPercentage() {
        return futureSafetyFactorPercentage;
    }

    /**
     * Sets the value of the futureSafetyFactorPercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFutureSafetyFactorPercentage(String value) {
        this.futureSafetyFactorPercentage = value;
    }

    /**
     * Gets the value of the onhandSafetyFactorPercentage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnhandSafetyFactorPercentage() {
        return onhandSafetyFactorPercentage;
    }

    /**
     * Sets the value of the onhandSafetyFactorPercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnhandSafetyFactorPercentage(String value) {
        this.onhandSafetyFactorPercentage = value;
    }

    /**
     * Gets the value of the onhandSafetyFactorQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOnhandSafetyFactorQuantity() {
        return onhandSafetyFactorQuantity;
    }

    /**
     * Sets the value of the onhandSafetyFactorQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOnhandSafetyFactorQuantity(String value) {
        this.onhandSafetyFactorQuantity = value;
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

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
    "filter"
})
@XmlRootElement(name = "RemainingFinancialTotals")
public class RemainingFinancialTotals {

    @XmlAttribute(name = "RemainingToAuthorize")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String remainingToAuthorize;
    @XmlAttribute(name = "RemainingToCharge")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String remainingToCharge;
    @XmlAttribute(name = "RemainingToRefund")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String remainingToRefund;
    @XmlAttribute(name = "a-dtype")
    protected List<String> aDtype;
    @XmlElement(name = "Filter")
    protected Filter filter;

    /**
     * Gets the value of the remainingToAuthorize property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemainingToAuthorize() {
        return remainingToAuthorize;
    }

    /**
     * Sets the value of the remainingToAuthorize property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemainingToAuthorize(String value) {
        this.remainingToAuthorize = value;
    }

    /**
     * Gets the value of the remainingToCharge property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemainingToCharge() {
        return remainingToCharge;
    }

    /**
     * Sets the value of the remainingToCharge property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemainingToCharge(String value) {
        this.remainingToCharge = value;
    }

    /**
     * Gets the value of the remainingToRefund property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemainingToRefund() {
        return remainingToRefund;
    }

    /**
     * Sets the value of the remainingToRefund property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemainingToRefund(String value) {
        this.remainingToRefund = value;
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
     * Gets the value of the filter property.
     * 
     * @return
     *     possible object is
     *     {@link Filter }
     *     
     */
    public Filter getFilter() {
        return filter;
    }

    /**
     * Sets the value of the filter property.
     * 
     * @param value
     *     allowed object is
     *     {@link Filter }
     *     
     */
    public void setFilter(Filter value) {
        this.filter = value;
    }

}

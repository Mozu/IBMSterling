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
    "priceTypeSummaryList"
})
@XmlRootElement(name = "LinePriceFrequency")
public class LinePriceFrequency {

    @XmlAttribute(name = "RecurringType")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String recurringType;
    @XmlAttribute(name = "Total")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String total;
    @XmlAttribute(name = "a-dtype")
    protected List<String> aDtype;
    @XmlElement(name = "PriceTypeSummaryList")
    protected PriceTypeSummaryList priceTypeSummaryList;

    /**
     * Gets the value of the recurringType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecurringType() {
        return recurringType;
    }

    /**
     * Sets the value of the recurringType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecurringType(String value) {
        this.recurringType = value;
    }

    /**
     * Gets the value of the total property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotal() {
        return total;
    }

    /**
     * Sets the value of the total property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotal(String value) {
        this.total = value;
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
     * Gets the value of the priceTypeSummaryList property.
     * 
     * @return
     *     possible object is
     *     {@link PriceTypeSummaryList }
     *     
     */
    public PriceTypeSummaryList getPriceTypeSummaryList() {
        return priceTypeSummaryList;
    }

    /**
     * Sets the value of the priceTypeSummaryList property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceTypeSummaryList }
     *     
     */
    public void setPriceTypeSummaryList(PriceTypeSummaryList value) {
        this.priceTypeSummaryList = value;
    }

}

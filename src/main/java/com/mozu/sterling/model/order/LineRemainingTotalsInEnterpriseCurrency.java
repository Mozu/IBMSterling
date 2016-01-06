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
@XmlRootElement(name = "LineRemainingTotalsInEnterpriseCurrency")
public class LineRemainingTotalsInEnterpriseCurrency {

    @XmlAttribute(name = "AdditionalLinePriceTotal")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String additionalLinePriceTotal;
    @XmlAttribute(name = "Charges")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String charges;
    @XmlAttribute(name = "Discount")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String discount;
    @XmlAttribute(name = "ExtendedPrice")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String extendedPrice;
    @XmlAttribute(name = "LineCost")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String lineCost;
    @XmlAttribute(name = "LineTotal")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String lineTotal;
    @XmlAttribute(name = "OptionPrice")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String optionPrice;
    @XmlAttribute(name = "PricingQty")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String pricingQty;
    @XmlAttribute(name = "Tax")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String tax;
    @XmlAttribute(name = "UnitPrice")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String unitPrice;
    @XmlAttribute(name = "a-dtype")
    protected List<String> aDtype;

    /**
     * Gets the value of the additionalLinePriceTotal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalLinePriceTotal() {
        return additionalLinePriceTotal;
    }

    /**
     * Sets the value of the additionalLinePriceTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalLinePriceTotal(String value) {
        this.additionalLinePriceTotal = value;
    }

    /**
     * Gets the value of the charges property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCharges() {
        return charges;
    }

    /**
     * Sets the value of the charges property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCharges(String value) {
        this.charges = value;
    }

    /**
     * Gets the value of the discount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscount() {
        return discount;
    }

    /**
     * Sets the value of the discount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscount(String value) {
        this.discount = value;
    }

    /**
     * Gets the value of the extendedPrice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtendedPrice() {
        return extendedPrice;
    }

    /**
     * Sets the value of the extendedPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtendedPrice(String value) {
        this.extendedPrice = value;
    }

    /**
     * Gets the value of the lineCost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineCost() {
        return lineCost;
    }

    /**
     * Sets the value of the lineCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineCost(String value) {
        this.lineCost = value;
    }

    /**
     * Gets the value of the lineTotal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineTotal() {
        return lineTotal;
    }

    /**
     * Sets the value of the lineTotal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineTotal(String value) {
        this.lineTotal = value;
    }

    /**
     * Gets the value of the optionPrice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOptionPrice() {
        return optionPrice;
    }

    /**
     * Sets the value of the optionPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOptionPrice(String value) {
        this.optionPrice = value;
    }

    /**
     * Gets the value of the pricingQty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPricingQty() {
        return pricingQty;
    }

    /**
     * Sets the value of the pricingQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPricingQty(String value) {
        this.pricingQty = value;
    }

    /**
     * Gets the value of the tax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTax() {
        return tax;
    }

    /**
     * Sets the value of the tax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTax(String value) {
        this.tax = value;
    }

    /**
     * Gets the value of the unitPrice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the value of the unitPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitPrice(String value) {
        this.unitPrice = value;
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

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
    "promotions",
    "awards"
})
@XmlRootElement(name = "AdditionalLinePrice")
public class AdditionalLinePrice {

    @XmlAttribute(name = "AdditionalLinePriceKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String additionalLinePriceKey;
    @XmlAttribute(name = "ExtendedPrice")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String extendedPrice;
    @XmlAttribute(name = "IsPriceLocked")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String isPriceLocked;
    @XmlAttribute(name = "IsPriceTypeForInformationOnly")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String isPriceTypeForInformationOnly;
    @XmlAttribute(name = "OrderLineKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String orderLineKey;
    @XmlAttribute(name = "Price")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String price;
    @XmlAttribute(name = "PriceTypeName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String priceTypeName;
    @XmlAttribute(name = "RecurringType")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String recurringType;
    @XmlAttribute(name = "a-dtype")
    protected List<String> aDtype;
    @XmlElement(name = "Promotions")
    protected Promotions promotions;
    @XmlElement(name = "Awards")
    protected Awards awards;

    /**
     * Gets the value of the additionalLinePriceKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalLinePriceKey() {
        return additionalLinePriceKey;
    }

    /**
     * Sets the value of the additionalLinePriceKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalLinePriceKey(String value) {
        this.additionalLinePriceKey = value;
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
     * Gets the value of the isPriceLocked property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsPriceLocked() {
        return isPriceLocked;
    }

    /**
     * Sets the value of the isPriceLocked property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsPriceLocked(String value) {
        this.isPriceLocked = value;
    }

    /**
     * Gets the value of the isPriceTypeForInformationOnly property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsPriceTypeForInformationOnly() {
        return isPriceTypeForInformationOnly;
    }

    /**
     * Sets the value of the isPriceTypeForInformationOnly property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsPriceTypeForInformationOnly(String value) {
        this.isPriceTypeForInformationOnly = value;
    }

    /**
     * Gets the value of the orderLineKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderLineKey() {
        return orderLineKey;
    }

    /**
     * Sets the value of the orderLineKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderLineKey(String value) {
        this.orderLineKey = value;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrice(String value) {
        this.price = value;
    }

    /**
     * Gets the value of the priceTypeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriceTypeName() {
        return priceTypeName;
    }

    /**
     * Sets the value of the priceTypeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriceTypeName(String value) {
        this.priceTypeName = value;
    }

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
     * Gets the value of the promotions property.
     * 
     * @return
     *     possible object is
     *     {@link Promotions }
     *     
     */
    public Promotions getPromotions() {
        return promotions;
    }

    /**
     * Sets the value of the promotions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Promotions }
     *     
     */
    public void setPromotions(Promotions value) {
        this.promotions = value;
    }

    /**
     * Gets the value of the awards property.
     * 
     * @return
     *     possible object is
     *     {@link Awards }
     *     
     */
    public Awards getAwards() {
        return awards;
    }

    /**
     * Sets the value of the awards property.
     * 
     * @param value
     *     allowed object is
     *     {@link Awards }
     *     
     */
    public void setAwards(Awards value) {
        this.awards = value;
    }

}

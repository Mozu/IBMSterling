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
    "productLine",
    "serviceLine"
})
@XmlRootElement(name = "ProductAssociation")
public class ProductAssociation {

    @XmlAttribute(name = "HoldSchedTillCompletion")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String holdSchedTillCompletion;
    @XmlAttribute(name = "OrderHeaderKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String orderHeaderKey;
    @XmlAttribute(name = "OrderProdSerAssocKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String orderProdSerAssocKey;
    @XmlAttribute(name = "PricingProductQuantity")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String pricingProductQuantity;
    @XmlAttribute(name = "PricingServiceQuantity")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String pricingServiceQuantity;
    @XmlAttribute(name = "ProductQuantity")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String productQuantity;
    @XmlAttribute(name = "ServiceQuantity")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String serviceQuantity;
    @XmlAttribute(name = "ServiceTimeOffset")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String serviceTimeOffset;
    @XmlAttribute(name = "a-dtype")
    protected List<String> aDtype;
    @XmlElement(name = "ProductLine")
    protected ProductLine productLine;
    @XmlElement(name = "ServiceLine")
    protected ServiceLine serviceLine;

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
     * Gets the value of the orderHeaderKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderHeaderKey() {
        return orderHeaderKey;
    }

    /**
     * Sets the value of the orderHeaderKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderHeaderKey(String value) {
        this.orderHeaderKey = value;
    }

    /**
     * Gets the value of the orderProdSerAssocKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderProdSerAssocKey() {
        return orderProdSerAssocKey;
    }

    /**
     * Sets the value of the orderProdSerAssocKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderProdSerAssocKey(String value) {
        this.orderProdSerAssocKey = value;
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
     * Gets the value of the serviceTimeOffset property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceTimeOffset() {
        return serviceTimeOffset;
    }

    /**
     * Sets the value of the serviceTimeOffset property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceTimeOffset(String value) {
        this.serviceTimeOffset = value;
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
     * Gets the value of the productLine property.
     * 
     * @return
     *     possible object is
     *     {@link ProductLine }
     *     
     */
    public ProductLine getProductLine() {
        return productLine;
    }

    /**
     * Sets the value of the productLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductLine }
     *     
     */
    public void setProductLine(ProductLine value) {
        this.productLine = value;
    }

    /**
     * Gets the value of the serviceLine property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceLine }
     *     
     */
    public ServiceLine getServiceLine() {
        return serviceLine;
    }

    /**
     * Sets the value of the serviceLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceLine }
     *     
     */
    public void setServiceLine(ServiceLine value) {
        this.serviceLine = value;
    }

}

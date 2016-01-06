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
    "orderLine"
})
@XmlRootElement(name = "OrderApprovalDetail")
public class OrderApprovalDetail {

    @XmlAttribute(name = "ApprovalRuleID")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String approvalRuleID;
    @XmlAttribute(name = "Message")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String message;
    @XmlAttribute(name = "MessageCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String messageCode;
    @XmlAttribute(name = "OrderApprovalDetailKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String orderApprovalDetailKey;
    @XmlAttribute(name = "OrderApprovalKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String orderApprovalKey;
    @XmlAttribute(name = "OrderHeaderKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String orderHeaderKey;
    @XmlAttribute(name = "OrderLineKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String orderLineKey;
    @XmlElement(name = "OrderLine")
    protected OrderLine orderLine;

    /**
     * Gets the value of the approvalRuleID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApprovalRuleID() {
        return approvalRuleID;
    }

    /**
     * Sets the value of the approvalRuleID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApprovalRuleID(String value) {
        this.approvalRuleID = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the messageCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageCode() {
        return messageCode;
    }

    /**
     * Sets the value of the messageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageCode(String value) {
        this.messageCode = value;
    }

    /**
     * Gets the value of the orderApprovalDetailKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderApprovalDetailKey() {
        return orderApprovalDetailKey;
    }

    /**
     * Sets the value of the orderApprovalDetailKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderApprovalDetailKey(String value) {
        this.orderApprovalDetailKey = value;
    }

    /**
     * Gets the value of the orderApprovalKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderApprovalKey() {
        return orderApprovalKey;
    }

    /**
     * Sets the value of the orderApprovalKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderApprovalKey(String value) {
        this.orderApprovalKey = value;
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
     * Gets the value of the orderLine property.
     * 
     * @return
     *     possible object is
     *     {@link OrderLine }
     *     
     */
    public OrderLine getOrderLine() {
        return orderLine;
    }

    /**
     * Sets the value of the orderLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderLine }
     *     
     */
    public void setOrderLine(OrderLine value) {
        this.orderLine = value;
    }

}
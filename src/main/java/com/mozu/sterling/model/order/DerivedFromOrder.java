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
@XmlRootElement(name = "DerivedFromOrder")
public class DerivedFromOrder {

    @XmlAttribute(name = "DocumentType")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String documentType;
    @XmlAttribute(name = "EnterpriseCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String enterpriseCode;
    @XmlAttribute(name = "OrderNo")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String orderNo;

    /**
     * Gets the value of the documentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * Sets the value of the documentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentType(String value) {
        this.documentType = value;
    }

    /**
     * Gets the value of the enterpriseCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnterpriseCode() {
        return enterpriseCode;
    }

    /**
     * Sets the value of the enterpriseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnterpriseCode(String value) {
        this.enterpriseCode = value;
    }

    /**
     * Gets the value of the orderNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderNo() {
        return orderNo;
    }

    /**
     * Sets the value of the orderNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderNo(String value) {
        this.orderNo = value;
    }

}

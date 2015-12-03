//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.16 at 12:31:15 PM CDT 
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
    "parentLine",
    "childLine"
})
@XmlRootElement(name = "OrderLineRelationship")
public class OrderLineRelationship {

    @XmlAttribute(name = "RelationshipType", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String relationshipType;
    @XmlElement(name = "ParentLine")
    protected ParentLine parentLine;
    @XmlElement(name = "ChildLine")
    protected ChildLine childLine;

    /**
     * Gets the value of the relationshipType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelationshipType() {
        return relationshipType;
    }

    /**
     * Sets the value of the relationshipType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelationshipType(String value) {
        this.relationshipType = value;
    }

    /**
     * Gets the value of the parentLine property.
     * 
     * @return
     *     possible object is
     *     {@link ParentLine }
     *     
     */
    public ParentLine getParentLine() {
        return parentLine;
    }

    /**
     * Sets the value of the parentLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParentLine }
     *     
     */
    public void setParentLine(ParentLine value) {
        this.parentLine = value;
    }

    /**
     * Gets the value of the childLine property.
     * 
     * @return
     *     possible object is
     *     {@link ChildLine }
     *     
     */
    public ChildLine getChildLine() {
        return childLine;
    }

    /**
     * Sets the value of the childLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChildLine }
     *     
     */
    public void setChildLine(ChildLine value) {
        this.childLine = value;
    }

}
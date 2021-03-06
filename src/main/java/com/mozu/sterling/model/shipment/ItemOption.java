//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.10 at 12:28:23 PM CST 
//


package com.mozu.sterling.model.shipment;

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
@XmlRootElement(name = "ItemOption")
public class ItemOption {

    @XmlAttribute(name = "ItemKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String itemKey;
    @XmlAttribute(name = "ItemOptionKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String itemOptionKey;
    @XmlAttribute(name = "OptionItemId")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String optionItemId;
    @XmlAttribute(name = "OptionUOM")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String optionUOM;

    /**
     * Gets the value of the itemKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemKey() {
        return itemKey;
    }

    /**
     * Sets the value of the itemKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemKey(String value) {
        this.itemKey = value;
    }

    /**
     * Gets the value of the itemOptionKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemOptionKey() {
        return itemOptionKey;
    }

    /**
     * Sets the value of the itemOptionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemOptionKey(String value) {
        this.itemOptionKey = value;
    }

    /**
     * Gets the value of the optionItemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOptionItemId() {
        return optionItemId;
    }

    /**
     * Sets the value of the optionItemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOptionItemId(String value) {
        this.optionItemId = value;
    }

    /**
     * Gets the value of the optionUOM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOptionUOM() {
        return optionUOM;
    }

    /**
     * Sets the value of the optionUOM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOptionUOM(String value) {
        this.optionUOM = value;
    }

}

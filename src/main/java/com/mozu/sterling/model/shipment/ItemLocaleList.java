//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.10 at 12:28:23 PM CST 
//


package com.mozu.sterling.model.shipment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "itemLocale"
})
@XmlRootElement(name = "ItemLocaleList")
public class ItemLocaleList {

    @XmlElement(name = "ItemLocale")
    protected ItemLocale itemLocale;

    /**
     * Gets the value of the itemLocale property.
     * 
     * @return
     *     possible object is
     *     {@link ItemLocale }
     *     
     */
    public ItemLocale getItemLocale() {
        return itemLocale;
    }

    /**
     * Sets the value of the itemLocale property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemLocale }
     *     
     */
    public void setItemLocale(ItemLocale value) {
        this.itemLocale = value;
    }

}
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.31 at 01:35:41 PM CST 
//


package com.mozu.sterling.model.order;

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
    "capacityAllocation"
})
@XmlRootElement(name = "CapacityAllocations")
public class CapacityAllocations {

    @XmlElement(name = "CapacityAllocation")
    protected CapacityAllocation capacityAllocation;

    /**
     * Gets the value of the capacityAllocation property.
     * 
     * @return
     *     possible object is
     *     {@link CapacityAllocation }
     *     
     */
    public CapacityAllocation getCapacityAllocation() {
        return capacityAllocation;
    }

    /**
     * Sets the value of the capacityAllocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link CapacityAllocation }
     *     
     */
    public void setCapacityAllocation(CapacityAllocation value) {
        this.capacityAllocation = value;
    }

}
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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "orderLineSourcingCntrl"
})
@XmlRootElement(name = "OrderLineSourcingControls")
public class OrderLineSourcingControls {

    @XmlElement(name = "OrderLineSourcingCntrl")
    protected List<OrderLineSourcingCntrl> orderLineSourcingCntrl;

    /**
     * Gets the value of the orderLineSourcingCntrl property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderLineSourcingCntrl property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderLineSourcingCntrl().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrderLineSourcingCntrl }
     * 
     * 
     */
    public List<OrderLineSourcingCntrl> getOrderLineSourcingCntrl() {
        if (orderLineSourcingCntrl == null) {
            orderLineSourcingCntrl = new ArrayList<OrderLineSourcingCntrl>();
        }
        return this.orderLineSourcingCntrl;
    }

}

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
    "pricingRuleAttributeValue"
})
@XmlRootElement(name = "PricingRuleAttributeValueList")
public class PricingRuleAttributeValueList {

    @XmlElement(name = "PricingRuleAttributeValue")
    protected List<PricingRuleAttributeValue> pricingRuleAttributeValue;

    /**
     * Gets the value of the pricingRuleAttributeValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pricingRuleAttributeValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPricingRuleAttributeValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PricingRuleAttributeValue }
     * 
     * 
     */
    public List<PricingRuleAttributeValue> getPricingRuleAttributeValue() {
        if (pricingRuleAttributeValue == null) {
            pricingRuleAttributeValue = new ArrayList<PricingRuleAttributeValue>();
        }
        return this.pricingRuleAttributeValue;
    }

}

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
    "pricingRuleTargetAttributeValue"
})
@XmlRootElement(name = "PricingRuleTargetAttributeValueList")
public class PricingRuleTargetAttributeValueList {

    @XmlElement(name = "PricingRuleTargetAttributeValue")
    protected List<PricingRuleTargetAttributeValue> pricingRuleTargetAttributeValue;

    /**
     * Gets the value of the pricingRuleTargetAttributeValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pricingRuleTargetAttributeValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPricingRuleTargetAttributeValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PricingRuleTargetAttributeValue }
     * 
     * 
     */
    public List<PricingRuleTargetAttributeValue> getPricingRuleTargetAttributeValue() {
        if (pricingRuleTargetAttributeValue == null) {
            pricingRuleTargetAttributeValue = new ArrayList<PricingRuleTargetAttributeValue>();
        }
        return this.pricingRuleTargetAttributeValue;
    }

}

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.10 at 12:28:23 PM CST 
//


package com.mozu.sterling.model.shipment;

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
    "doNotMixAttributeForCase"
})
@XmlRootElement(name = "DoNotMixAttributesForCase")
public class DoNotMixAttributesForCase {

    @XmlElement(name = "DoNotMixAttributeForCase")
    protected List<DoNotMixAttributeForCase> doNotMixAttributeForCase;

    /**
     * Gets the value of the doNotMixAttributeForCase property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the doNotMixAttributeForCase property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDoNotMixAttributeForCase().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DoNotMixAttributeForCase }
     * 
     * 
     */
    public List<DoNotMixAttributeForCase> getDoNotMixAttributeForCase() {
        if (doNotMixAttributeForCase == null) {
            doNotMixAttributeForCase = new ArrayList<DoNotMixAttributeForCase>();
        }
        return this.doNotMixAttributeForCase;
    }

}
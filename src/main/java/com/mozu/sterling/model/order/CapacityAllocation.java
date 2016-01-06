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
@XmlRootElement(name = "CapacityAllocation")
public class CapacityAllocation {

    @XmlAttribute(name = "AllocatedCapacity")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String allocatedCapacity;
    @XmlAttribute(name = "CapacityReference")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String capacityReference;
    @XmlAttribute(name = "PromisedApptEndDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String promisedApptEndDate;
    @XmlAttribute(name = "PromisedApptStartDate")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String promisedApptStartDate;
    @XmlAttribute(name = "ResourcePoolKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String resourcePoolKey;
    @XmlAttribute(name = "a-dtype")
    protected List<String> aDtype;

    /**
     * Gets the value of the allocatedCapacity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAllocatedCapacity() {
        return allocatedCapacity;
    }

    /**
     * Sets the value of the allocatedCapacity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAllocatedCapacity(String value) {
        this.allocatedCapacity = value;
    }

    /**
     * Gets the value of the capacityReference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapacityReference() {
        return capacityReference;
    }

    /**
     * Sets the value of the capacityReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapacityReference(String value) {
        this.capacityReference = value;
    }

    /**
     * Gets the value of the promisedApptEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromisedApptEndDate() {
        return promisedApptEndDate;
    }

    /**
     * Sets the value of the promisedApptEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromisedApptEndDate(String value) {
        this.promisedApptEndDate = value;
    }

    /**
     * Gets the value of the promisedApptStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromisedApptStartDate() {
        return promisedApptStartDate;
    }

    /**
     * Sets the value of the promisedApptStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromisedApptStartDate(String value) {
        this.promisedApptStartDate = value;
    }

    /**
     * Gets the value of the resourcePoolKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourcePoolKey() {
        return resourcePoolKey;
    }

    /**
     * Sets the value of the resourcePoolKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourcePoolKey(String value) {
        this.resourcePoolKey = value;
    }

    /**
     * Gets the value of the aDtype property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the aDtype property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getADtype().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getADtype() {
        if (aDtype == null) {
            aDtype = new ArrayList<String>();
        }
        return this.aDtype;
    }

}

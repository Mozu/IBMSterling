//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.16 at 12:31:15 PM CDT 
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
@XmlRootElement(name = "Dependency")
public class Dependency {

    @XmlAttribute(name = "DependentOnPrimeLineNo")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String dependentOnPrimeLineNo;
    @XmlAttribute(name = "DependentOnSubLineNo")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String dependentOnSubLineNo;
    @XmlAttribute(name = "DependentOnTransactionalLineId")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String dependentOnTransactionalLineId;
    @XmlAttribute(name = "a-dtype")
    protected List<String> aDtype;

    /**
     * Gets the value of the dependentOnPrimeLineNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDependentOnPrimeLineNo() {
        return dependentOnPrimeLineNo;
    }

    /**
     * Sets the value of the dependentOnPrimeLineNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDependentOnPrimeLineNo(String value) {
        this.dependentOnPrimeLineNo = value;
    }

    /**
     * Gets the value of the dependentOnSubLineNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDependentOnSubLineNo() {
        return dependentOnSubLineNo;
    }

    /**
     * Sets the value of the dependentOnSubLineNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDependentOnSubLineNo(String value) {
        this.dependentOnSubLineNo = value;
    }

    /**
     * Gets the value of the dependentOnTransactionalLineId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDependentOnTransactionalLineId() {
        return dependentOnTransactionalLineId;
    }

    /**
     * Sets the value of the dependentOnTransactionalLineId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDependentOnTransactionalLineId(String value) {
        this.dependentOnTransactionalLineId = value;
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
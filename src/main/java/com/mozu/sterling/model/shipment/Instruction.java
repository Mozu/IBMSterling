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
@XmlRootElement(name = "Instruction")
public class Instruction {

    @XmlAttribute(name = "InstructionDetailKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String instructionDetailKey;
    @XmlAttribute(name = "InstructionText")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String instructionText;
    @XmlAttribute(name = "InstructionType")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String instructionType;
    @XmlAttribute(name = "InstructionURL")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String instructionURL;
    @XmlAttribute(name = "InstructionUsage")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String instructionUsage;
    @XmlAttribute(name = "ReferenceKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String referenceKey;
    @XmlAttribute(name = "SequenceNo")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String sequenceNo;
    @XmlAttribute(name = "TableName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String tableName;
    @XmlAttribute(name = "isHistory")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String isHistory;
    @XmlAttribute(name = "a-dtype")
    protected List<String> aDtype;

    /**
     * Gets the value of the instructionDetailKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstructionDetailKey() {
        return instructionDetailKey;
    }

    /**
     * Sets the value of the instructionDetailKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstructionDetailKey(String value) {
        this.instructionDetailKey = value;
    }

    /**
     * Gets the value of the instructionText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstructionText() {
        return instructionText;
    }

    /**
     * Sets the value of the instructionText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstructionText(String value) {
        this.instructionText = value;
    }

    /**
     * Gets the value of the instructionType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstructionType() {
        return instructionType;
    }

    /**
     * Sets the value of the instructionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstructionType(String value) {
        this.instructionType = value;
    }

    /**
     * Gets the value of the instructionURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstructionURL() {
        return instructionURL;
    }

    /**
     * Sets the value of the instructionURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstructionURL(String value) {
        this.instructionURL = value;
    }

    /**
     * Gets the value of the instructionUsage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstructionUsage() {
        return instructionUsage;
    }

    /**
     * Sets the value of the instructionUsage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstructionUsage(String value) {
        this.instructionUsage = value;
    }

    /**
     * Gets the value of the referenceKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceKey() {
        return referenceKey;
    }

    /**
     * Sets the value of the referenceKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceKey(String value) {
        this.referenceKey = value;
    }

    /**
     * Gets the value of the sequenceNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSequenceNo() {
        return sequenceNo;
    }

    /**
     * Sets the value of the sequenceNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSequenceNo(String value) {
        this.sequenceNo = value;
    }

    /**
     * Gets the value of the tableName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Sets the value of the tableName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTableName(String value) {
        this.tableName = value;
    }

    /**
     * Gets the value of the isHistory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsHistory() {
        return isHistory;
    }

    /**
     * Sets the value of the isHistory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsHistory(String value) {
        this.isHistory = value;
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
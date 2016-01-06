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
@XmlRootElement(name = "ItemInstruction")
public class ItemInstruction {

    @XmlAttribute(name = "InstructionCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String instructionCode;
    @XmlAttribute(name = "InstructionText")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String instructionText;
    @XmlAttribute(name = "InstructionType")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String instructionType;
    @XmlAttribute(name = "ItemInstructionKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String itemInstructionKey;
    @XmlAttribute(name = "ItemKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String itemKey;
    @XmlAttribute(name = "SeqNo")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String seqNo;
    @XmlAttribute(name = "a-dtype")
    protected List<String> aDtype;

    /**
     * Gets the value of the instructionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstructionCode() {
        return instructionCode;
    }

    /**
     * Sets the value of the instructionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstructionCode(String value) {
        this.instructionCode = value;
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
     * Gets the value of the itemInstructionKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemInstructionKey() {
        return itemInstructionKey;
    }

    /**
     * Sets the value of the itemInstructionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemInstructionKey(String value) {
        this.itemInstructionKey = value;
    }

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
     * Gets the value of the seqNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSeqNo() {
        return seqNo;
    }

    /**
     * Sets the value of the seqNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSeqNo(String value) {
        this.seqNo = value;
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

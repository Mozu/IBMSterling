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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "promotionReferences",
    "pricingRule"
})
@XmlRootElement(name = "Award")
public class Award {

    @XmlAttribute(name = "AdditionalLinePriceKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String additionalLinePriceKey;
    @XmlAttribute(name = "AwardAmount")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String awardAmount;
    @XmlAttribute(name = "AwardApplied")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String awardApplied;
    @XmlAttribute(name = "AwardId")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String awardId;
    @XmlAttribute(name = "AwardKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String awardKey;
    @XmlAttribute(name = "AwardQuantity")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String awardQuantity;
    @XmlAttribute(name = "AwardType")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String awardType;
    @XmlAttribute(name = "ChargeCategory")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String chargeCategory;
    @XmlAttribute(name = "ChargeName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String chargeName;
    @XmlAttribute(name = "DenialReason")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String denialReason;
    @XmlAttribute(name = "Description")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String description;
    @XmlAttribute(name = "IsFreeGiftAward")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String isFreeGiftAward;
    @XmlAttribute(name = "IsManual")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String isManual;
    @XmlAttribute(name = "IsPromotionOnOrder")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String isPromotionOnOrder;
    @XmlAttribute(name = "IsPromotionOnOrderLine")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String isPromotionOnOrderLine;
    @XmlAttribute(name = "PosReasonCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String posReasonCode;
    @XmlAttribute(name = "PromotionId")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String promotionId;
    @XmlAttribute(name = "PromotionKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String promotionKey;
    @XmlAttribute(name = "a-dtype")
    protected List<String> aDtype;
    @XmlElement(name = "PromotionReferences")
    protected PromotionReferences promotionReferences;
    @XmlElement(name = "PricingRule")
    protected PricingRule pricingRule;

    /**
     * Gets the value of the additionalLinePriceKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalLinePriceKey() {
        return additionalLinePriceKey;
    }

    /**
     * Sets the value of the additionalLinePriceKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalLinePriceKey(String value) {
        this.additionalLinePriceKey = value;
    }

    /**
     * Gets the value of the awardAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwardAmount() {
        return awardAmount;
    }

    /**
     * Sets the value of the awardAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwardAmount(String value) {
        this.awardAmount = value;
    }

    /**
     * Gets the value of the awardApplied property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwardApplied() {
        return awardApplied;
    }

    /**
     * Sets the value of the awardApplied property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwardApplied(String value) {
        this.awardApplied = value;
    }

    /**
     * Gets the value of the awardId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwardId() {
        return awardId;
    }

    /**
     * Sets the value of the awardId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwardId(String value) {
        this.awardId = value;
    }

    /**
     * Gets the value of the awardKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwardKey() {
        return awardKey;
    }

    /**
     * Sets the value of the awardKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwardKey(String value) {
        this.awardKey = value;
    }

    /**
     * Gets the value of the awardQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwardQuantity() {
        return awardQuantity;
    }

    /**
     * Sets the value of the awardQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwardQuantity(String value) {
        this.awardQuantity = value;
    }

    /**
     * Gets the value of the awardType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwardType() {
        return awardType;
    }

    /**
     * Sets the value of the awardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwardType(String value) {
        this.awardType = value;
    }

    /**
     * Gets the value of the chargeCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeCategory() {
        return chargeCategory;
    }

    /**
     * Sets the value of the chargeCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeCategory(String value) {
        this.chargeCategory = value;
    }

    /**
     * Gets the value of the chargeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeName() {
        return chargeName;
    }

    /**
     * Sets the value of the chargeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeName(String value) {
        this.chargeName = value;
    }

    /**
     * Gets the value of the denialReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenialReason() {
        return denialReason;
    }

    /**
     * Sets the value of the denialReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenialReason(String value) {
        this.denialReason = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the isFreeGiftAward property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsFreeGiftAward() {
        return isFreeGiftAward;
    }

    /**
     * Sets the value of the isFreeGiftAward property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsFreeGiftAward(String value) {
        this.isFreeGiftAward = value;
    }

    /**
     * Gets the value of the isManual property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsManual() {
        return isManual;
    }

    /**
     * Sets the value of the isManual property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsManual(String value) {
        this.isManual = value;
    }

    /**
     * Gets the value of the isPromotionOnOrder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsPromotionOnOrder() {
        return isPromotionOnOrder;
    }

    /**
     * Sets the value of the isPromotionOnOrder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsPromotionOnOrder(String value) {
        this.isPromotionOnOrder = value;
    }

    /**
     * Gets the value of the isPromotionOnOrderLine property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsPromotionOnOrderLine() {
        return isPromotionOnOrderLine;
    }

    /**
     * Sets the value of the isPromotionOnOrderLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsPromotionOnOrderLine(String value) {
        this.isPromotionOnOrderLine = value;
    }

    /**
     * Gets the value of the posReasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosReasonCode() {
        return posReasonCode;
    }

    /**
     * Sets the value of the posReasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosReasonCode(String value) {
        this.posReasonCode = value;
    }

    /**
     * Gets the value of the promotionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromotionId() {
        return promotionId;
    }

    /**
     * Sets the value of the promotionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromotionId(String value) {
        this.promotionId = value;
    }

    /**
     * Gets the value of the promotionKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromotionKey() {
        return promotionKey;
    }

    /**
     * Sets the value of the promotionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromotionKey(String value) {
        this.promotionKey = value;
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

    /**
     * Gets the value of the promotionReferences property.
     * 
     * @return
     *     possible object is
     *     {@link PromotionReferences }
     *     
     */
    public PromotionReferences getPromotionReferences() {
        return promotionReferences;
    }

    /**
     * Sets the value of the promotionReferences property.
     * 
     * @param value
     *     allowed object is
     *     {@link PromotionReferences }
     *     
     */
    public void setPromotionReferences(PromotionReferences value) {
        this.promotionReferences = value;
    }

    /**
     * Gets the value of the pricingRule property.
     * 
     * @return
     *     possible object is
     *     {@link PricingRule }
     *     
     */
    public PricingRule getPricingRule() {
        return pricingRule;
    }

    /**
     * Sets the value of the pricingRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link PricingRule }
     *     
     */
    public void setPricingRule(PricingRule value) {
        this.pricingRule = value;
    }

}

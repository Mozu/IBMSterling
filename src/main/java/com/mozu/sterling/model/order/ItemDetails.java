//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.31 at 01:35:41 PM CST 
//


package com.mozu.sterling.model.order;

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
    "primaryInformation",
    "safetyFactorDefinitions",
    "inventoryParameters",
    "classificationCodes",
    "hazmatInformation",
    "itemServiceSkillList",
    "itemAliasList",
    "itemExclusionList",
    "additionalAttributeList",
    "languageDescriptionList",
    "itemLocaleList",
    "components",
    "inventoryTagAttributes",
    "alternateUOMList",
    "itemInstructionList",
    "itemOptionList",
    "itemServiceAssocList",
    "serviceActivities",
    "categoryList",
    "childItemList"
})
@XmlRootElement(name = "ItemDetails")
public class ItemDetails {

    @XmlAttribute(name = "CanUseAsServiceTool")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String canUseAsServiceTool;
    @XmlAttribute(name = "GlobalItemID")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String globalItemID;
    @XmlAttribute(name = "IsItemSuperseded")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String isItemSuperseded;
    @XmlAttribute(name = "ItemGroupCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String itemGroupCode;
    @XmlAttribute(name = "ItemID")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String itemID;
    @XmlAttribute(name = "ItemKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String itemKey;
    @XmlAttribute(name = "OrganizationCode", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String organizationCode;
    @XmlAttribute(name = "SubCatalogOrganizationCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String subCatalogOrganizationCode;
    @XmlAttribute(name = "UnitOfMeasure")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String unitOfMeasure;
    @XmlElement(name = "PrimaryInformation")
    protected PrimaryInformation primaryInformation;
    @XmlElement(name = "SafetyFactorDefinitions")
    protected SafetyFactorDefinitions safetyFactorDefinitions;
    @XmlElement(name = "InventoryParameters")
    protected InventoryParameters inventoryParameters;
    @XmlElement(name = "ClassificationCodes")
    protected ClassificationCodes classificationCodes;
    @XmlElement(name = "HazmatInformation")
    protected HazmatInformation hazmatInformation;
    @XmlElement(name = "ItemServiceSkillList")
    protected ItemServiceSkillList itemServiceSkillList;
    @XmlElement(name = "ItemAliasList")
    protected ItemAliasList itemAliasList;
    @XmlElement(name = "ItemExclusionList")
    protected ItemExclusionList itemExclusionList;
    @XmlElement(name = "AdditionalAttributeList")
    protected AdditionalAttributeList additionalAttributeList;
    @XmlElement(name = "LanguageDescriptionList")
    protected LanguageDescriptionList languageDescriptionList;
    @XmlElement(name = "ItemLocaleList")
    protected ItemLocaleList itemLocaleList;
    @XmlElement(name = "Components")
    protected Components components;
    @XmlElement(name = "InventoryTagAttributes")
    protected InventoryTagAttributes inventoryTagAttributes;
    @XmlElement(name = "AlternateUOMList")
    protected AlternateUOMList alternateUOMList;
    @XmlElement(name = "ItemInstructionList")
    protected ItemInstructionList itemInstructionList;
    @XmlElement(name = "ItemOptionList")
    protected ItemOptionList itemOptionList;
    @XmlElement(name = "ItemServiceAssocList")
    protected ItemServiceAssocList itemServiceAssocList;
    @XmlElement(name = "ServiceActivities")
    protected ServiceActivities serviceActivities;
    @XmlElement(name = "CategoryList")
    protected CategoryList categoryList;
    @XmlElement(name = "ChildItemList")
    protected ChildItemList childItemList;

    /**
     * Gets the value of the canUseAsServiceTool property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCanUseAsServiceTool() {
        return canUseAsServiceTool;
    }

    /**
     * Sets the value of the canUseAsServiceTool property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCanUseAsServiceTool(String value) {
        this.canUseAsServiceTool = value;
    }

    /**
     * Gets the value of the globalItemID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGlobalItemID() {
        return globalItemID;
    }

    /**
     * Sets the value of the globalItemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGlobalItemID(String value) {
        this.globalItemID = value;
    }

    /**
     * Gets the value of the isItemSuperseded property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsItemSuperseded() {
        return isItemSuperseded;
    }

    /**
     * Sets the value of the isItemSuperseded property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsItemSuperseded(String value) {
        this.isItemSuperseded = value;
    }

    /**
     * Gets the value of the itemGroupCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemGroupCode() {
        return itemGroupCode;
    }

    /**
     * Sets the value of the itemGroupCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemGroupCode(String value) {
        this.itemGroupCode = value;
    }

    /**
     * Gets the value of the itemID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemID() {
        return itemID;
    }

    /**
     * Sets the value of the itemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemID(String value) {
        this.itemID = value;
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
     * Gets the value of the organizationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationCode() {
        return organizationCode;
    }

    /**
     * Sets the value of the organizationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationCode(String value) {
        this.organizationCode = value;
    }

    /**
     * Gets the value of the subCatalogOrganizationCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubCatalogOrganizationCode() {
        return subCatalogOrganizationCode;
    }

    /**
     * Sets the value of the subCatalogOrganizationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubCatalogOrganizationCode(String value) {
        this.subCatalogOrganizationCode = value;
    }

    /**
     * Gets the value of the unitOfMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Sets the value of the unitOfMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitOfMeasure(String value) {
        this.unitOfMeasure = value;
    }

    /**
     * Gets the value of the primaryInformation property.
     * 
     * @return
     *     possible object is
     *     {@link PrimaryInformation }
     *     
     */
    public PrimaryInformation getPrimaryInformation() {
        return primaryInformation;
    }

    /**
     * Sets the value of the primaryInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrimaryInformation }
     *     
     */
    public void setPrimaryInformation(PrimaryInformation value) {
        this.primaryInformation = value;
    }

    /**
     * Gets the value of the safetyFactorDefinitions property.
     * 
     * @return
     *     possible object is
     *     {@link SafetyFactorDefinitions }
     *     
     */
    public SafetyFactorDefinitions getSafetyFactorDefinitions() {
        return safetyFactorDefinitions;
    }

    /**
     * Sets the value of the safetyFactorDefinitions property.
     * 
     * @param value
     *     allowed object is
     *     {@link SafetyFactorDefinitions }
     *     
     */
    public void setSafetyFactorDefinitions(SafetyFactorDefinitions value) {
        this.safetyFactorDefinitions = value;
    }

    /**
     * Gets the value of the inventoryParameters property.
     * 
     * @return
     *     possible object is
     *     {@link InventoryParameters }
     *     
     */
    public InventoryParameters getInventoryParameters() {
        return inventoryParameters;
    }

    /**
     * Sets the value of the inventoryParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link InventoryParameters }
     *     
     */
    public void setInventoryParameters(InventoryParameters value) {
        this.inventoryParameters = value;
    }

    /**
     * Gets the value of the classificationCodes property.
     * 
     * @return
     *     possible object is
     *     {@link ClassificationCodes }
     *     
     */
    public ClassificationCodes getClassificationCodes() {
        return classificationCodes;
    }

    /**
     * Sets the value of the classificationCodes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassificationCodes }
     *     
     */
    public void setClassificationCodes(ClassificationCodes value) {
        this.classificationCodes = value;
    }

    /**
     * Gets the value of the hazmatInformation property.
     * 
     * @return
     *     possible object is
     *     {@link HazmatInformation }
     *     
     */
    public HazmatInformation getHazmatInformation() {
        return hazmatInformation;
    }

    /**
     * Sets the value of the hazmatInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link HazmatInformation }
     *     
     */
    public void setHazmatInformation(HazmatInformation value) {
        this.hazmatInformation = value;
    }

    /**
     * Gets the value of the itemServiceSkillList property.
     * 
     * @return
     *     possible object is
     *     {@link ItemServiceSkillList }
     *     
     */
    public ItemServiceSkillList getItemServiceSkillList() {
        return itemServiceSkillList;
    }

    /**
     * Sets the value of the itemServiceSkillList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemServiceSkillList }
     *     
     */
    public void setItemServiceSkillList(ItemServiceSkillList value) {
        this.itemServiceSkillList = value;
    }

    /**
     * Gets the value of the itemAliasList property.
     * 
     * @return
     *     possible object is
     *     {@link ItemAliasList }
     *     
     */
    public ItemAliasList getItemAliasList() {
        return itemAliasList;
    }

    /**
     * Sets the value of the itemAliasList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemAliasList }
     *     
     */
    public void setItemAliasList(ItemAliasList value) {
        this.itemAliasList = value;
    }

    /**
     * Gets the value of the itemExclusionList property.
     * 
     * @return
     *     possible object is
     *     {@link ItemExclusionList }
     *     
     */
    public ItemExclusionList getItemExclusionList() {
        return itemExclusionList;
    }

    /**
     * Sets the value of the itemExclusionList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemExclusionList }
     *     
     */
    public void setItemExclusionList(ItemExclusionList value) {
        this.itemExclusionList = value;
    }

    /**
     * Gets the value of the additionalAttributeList property.
     * 
     * @return
     *     possible object is
     *     {@link AdditionalAttributeList }
     *     
     */
    public AdditionalAttributeList getAdditionalAttributeList() {
        return additionalAttributeList;
    }

    /**
     * Sets the value of the additionalAttributeList property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdditionalAttributeList }
     *     
     */
    public void setAdditionalAttributeList(AdditionalAttributeList value) {
        this.additionalAttributeList = value;
    }

    /**
     * Gets the value of the languageDescriptionList property.
     * 
     * @return
     *     possible object is
     *     {@link LanguageDescriptionList }
     *     
     */
    public LanguageDescriptionList getLanguageDescriptionList() {
        return languageDescriptionList;
    }

    /**
     * Sets the value of the languageDescriptionList property.
     * 
     * @param value
     *     allowed object is
     *     {@link LanguageDescriptionList }
     *     
     */
    public void setLanguageDescriptionList(LanguageDescriptionList value) {
        this.languageDescriptionList = value;
    }

    /**
     * Gets the value of the itemLocaleList property.
     * 
     * @return
     *     possible object is
     *     {@link ItemLocaleList }
     *     
     */
    public ItemLocaleList getItemLocaleList() {
        return itemLocaleList;
    }

    /**
     * Sets the value of the itemLocaleList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemLocaleList }
     *     
     */
    public void setItemLocaleList(ItemLocaleList value) {
        this.itemLocaleList = value;
    }

    /**
     * Gets the value of the components property.
     * 
     * @return
     *     possible object is
     *     {@link Components }
     *     
     */
    public Components getComponents() {
        return components;
    }

    /**
     * Sets the value of the components property.
     * 
     * @param value
     *     allowed object is
     *     {@link Components }
     *     
     */
    public void setComponents(Components value) {
        this.components = value;
    }

    /**
     * Gets the value of the inventoryTagAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link InventoryTagAttributes }
     *     
     */
    public InventoryTagAttributes getInventoryTagAttributes() {
        return inventoryTagAttributes;
    }

    /**
     * Sets the value of the inventoryTagAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link InventoryTagAttributes }
     *     
     */
    public void setInventoryTagAttributes(InventoryTagAttributes value) {
        this.inventoryTagAttributes = value;
    }

    /**
     * Gets the value of the alternateUOMList property.
     * 
     * @return
     *     possible object is
     *     {@link AlternateUOMList }
     *     
     */
    public AlternateUOMList getAlternateUOMList() {
        return alternateUOMList;
    }

    /**
     * Sets the value of the alternateUOMList property.
     * 
     * @param value
     *     allowed object is
     *     {@link AlternateUOMList }
     *     
     */
    public void setAlternateUOMList(AlternateUOMList value) {
        this.alternateUOMList = value;
    }

    /**
     * Gets the value of the itemInstructionList property.
     * 
     * @return
     *     possible object is
     *     {@link ItemInstructionList }
     *     
     */
    public ItemInstructionList getItemInstructionList() {
        return itemInstructionList;
    }

    /**
     * Sets the value of the itemInstructionList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemInstructionList }
     *     
     */
    public void setItemInstructionList(ItemInstructionList value) {
        this.itemInstructionList = value;
    }

    /**
     * Gets the value of the itemOptionList property.
     * 
     * @return
     *     possible object is
     *     {@link ItemOptionList }
     *     
     */
    public ItemOptionList getItemOptionList() {
        return itemOptionList;
    }

    /**
     * Sets the value of the itemOptionList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemOptionList }
     *     
     */
    public void setItemOptionList(ItemOptionList value) {
        this.itemOptionList = value;
    }

    /**
     * Gets the value of the itemServiceAssocList property.
     * 
     * @return
     *     possible object is
     *     {@link ItemServiceAssocList }
     *     
     */
    public ItemServiceAssocList getItemServiceAssocList() {
        return itemServiceAssocList;
    }

    /**
     * Sets the value of the itemServiceAssocList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemServiceAssocList }
     *     
     */
    public void setItemServiceAssocList(ItemServiceAssocList value) {
        this.itemServiceAssocList = value;
    }

    /**
     * Gets the value of the serviceActivities property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceActivities }
     *     
     */
    public ServiceActivities getServiceActivities() {
        return serviceActivities;
    }

    /**
     * Sets the value of the serviceActivities property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceActivities }
     *     
     */
    public void setServiceActivities(ServiceActivities value) {
        this.serviceActivities = value;
    }

    /**
     * Gets the value of the categoryList property.
     * 
     * @return
     *     possible object is
     *     {@link CategoryList }
     *     
     */
    public CategoryList getCategoryList() {
        return categoryList;
    }

    /**
     * Sets the value of the categoryList property.
     * 
     * @param value
     *     allowed object is
     *     {@link CategoryList }
     *     
     */
    public void setCategoryList(CategoryList value) {
        this.categoryList = value;
    }

    /**
     * Gets the value of the childItemList property.
     * 
     * @return
     *     possible object is
     *     {@link ChildItemList }
     *     
     */
    public ChildItemList getChildItemList() {
        return childItemList;
    }

    /**
     * Sets the value of the childItemList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChildItemList }
     *     
     */
    public void setChildItemList(ChildItemList value) {
        this.childItemList = value;
    }

}

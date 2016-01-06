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
@XmlRootElement(name = "PersonInfo")
public class PersonInfo implements ContactInfo {

    @XmlAttribute(name = "AddressID")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String addressID;
    @XmlAttribute(name = "AddressLine1")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String addressLine1;
    @XmlAttribute(name = "AddressLine2")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String addressLine2;
    @XmlAttribute(name = "AddressLine3")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String addressLine3;
    @XmlAttribute(name = "AddressLine4")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String addressLine4;
    @XmlAttribute(name = "AddressLine5")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String addressLine5;
    @XmlAttribute(name = "AddressLine6")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String addressLine6;
    @XmlAttribute(name = "AddressType")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String addressType;
    @XmlAttribute(name = "AlternateEmailID")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String alternateEmailID;
    @XmlAttribute(name = "Beeper")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String beeper;
    @XmlAttribute(name = "City")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String city;
    @XmlAttribute(name = "Company")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String company;
    @XmlAttribute(name = "Country")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String country;
    @XmlAttribute(name = "DayFaxNo")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String dayFaxNo;
    @XmlAttribute(name = "DayPhone")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String dayPhone;
    @XmlAttribute(name = "Department")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String department;
    @XmlAttribute(name = "EMailID")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String eMailID;
    @XmlAttribute(name = "EveningFaxNo")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String eveningFaxNo;
    @XmlAttribute(name = "EveningPhone")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String eveningPhone;
    @XmlAttribute(name = "FirstName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String firstName;
    @XmlAttribute(name = "IsAddressVerified")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String isAddressVerified;
    @XmlAttribute(name = "IsCommercialAddress")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String isCommercialAddress;
    @XmlAttribute(name = "JobTitle")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String jobTitle;
    @XmlAttribute(name = "LastName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String lastName;
    @XmlAttribute(name = "Latitude")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String latitude;
    @XmlAttribute(name = "Longitude")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String longitude;
    @XmlAttribute(name = "MiddleName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String middleName;
    @XmlAttribute(name = "MobilePhone")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String mobilePhone;
    @XmlAttribute(name = "OtherPhone")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String otherPhone;
    @XmlAttribute(name = "PersonID")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String personID;
    @XmlAttribute(name = "PersonInfoKey")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String personInfoKey;
    @XmlAttribute(name = "State")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String state;
    @XmlAttribute(name = "Suffix")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String suffix;
    @XmlAttribute(name = "TaxGeoCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String taxGeoCode;
    @XmlAttribute(name = "Title")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String title;
    @XmlAttribute(name = "ZipCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String zipCode;
    @XmlAttribute(name = "a-dtype")
    protected List<String> aDtype;

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getAddressID()
     */
    @Override
    public String getAddressID() {
        return addressID;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setAddressID(java.lang.String)
     */
    @Override
    public void setAddressID(String value) {
        this.addressID = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getAddressLine1()
     */
    @Override
    public String getAddressLine1() {
        return addressLine1;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setAddressLine1(java.lang.String)
     */
    @Override
    public void setAddressLine1(String value) {
        this.addressLine1 = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getAddressLine2()
     */
    @Override
    public String getAddressLine2() {
        return addressLine2;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setAddressLine2(java.lang.String)
     */
    @Override
    public void setAddressLine2(String value) {
        this.addressLine2 = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getAddressLine3()
     */
    @Override
    public String getAddressLine3() {
        return addressLine3;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setAddressLine3(java.lang.String)
     */
    @Override
    public void setAddressLine3(String value) {
        this.addressLine3 = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getAddressLine4()
     */
    @Override
    public String getAddressLine4() {
        return addressLine4;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setAddressLine4(java.lang.String)
     */
    @Override
    public void setAddressLine4(String value) {
        this.addressLine4 = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getAddressLine5()
     */
    @Override
    public String getAddressLine5() {
        return addressLine5;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setAddressLine5(java.lang.String)
     */
    @Override
    public void setAddressLine5(String value) {
        this.addressLine5 = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getAddressLine6()
     */
    @Override
    public String getAddressLine6() {
        return addressLine6;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setAddressLine6(java.lang.String)
     */
    @Override
    public void setAddressLine6(String value) {
        this.addressLine6 = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getAddressType()
     */
    public String getAddressType() {
        return addressType;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setAddressType(java.lang.String)
     */
    public void setAddressType(String value) {
        this.addressType = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getAlternateEmailID()
     */
    @Override
    public String getAlternateEmailID() {
        return alternateEmailID;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setAlternateEmailID(java.lang.String)
     */
    @Override
    public void setAlternateEmailID(String value) {
        this.alternateEmailID = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getBeeper()
     */
    @Override
    public String getBeeper() {
        return beeper;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setBeeper(java.lang.String)
     */
    @Override
    public void setBeeper(String value) {
        this.beeper = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getCity()
     */
    @Override
    public String getCity() {
        return city;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setCity(java.lang.String)
     */
    @Override
    public void setCity(String value) {
        this.city = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getCompany()
     */
    @Override
    public String getCompany() {
        return company;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setCompany(java.lang.String)
     */
    @Override
    public void setCompany(String value) {
        this.company = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getCountry()
     */
    @Override
    public String getCountry() {
        return country;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setCountry(java.lang.String)
     */
    @Override
    public void setCountry(String value) {
        this.country = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getDayFaxNo()
     */
    @Override
    public String getDayFaxNo() {
        return dayFaxNo;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setDayFaxNo(java.lang.String)
     */
    @Override
    public void setDayFaxNo(String value) {
        this.dayFaxNo = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getDayPhone()
     */
    @Override
    public String getDayPhone() {
        return dayPhone;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setDayPhone(java.lang.String)
     */
    @Override
    public void setDayPhone(String value) {
        this.dayPhone = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getDepartment()
     */
    @Override
    public String getDepartment() {
        return department;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setDepartment(java.lang.String)
     */
    @Override
    public void setDepartment(String value) {
        this.department = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getEMailID()
     */
    @Override
    public String getEMailID() {
        return eMailID;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setEMailID(java.lang.String)
     */
    @Override
    public void setEMailID(String value) {
        this.eMailID = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getEveningFaxNo()
     */
    @Override
    public String getEveningFaxNo() {
        return eveningFaxNo;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setEveningFaxNo(java.lang.String)
     */
    @Override
    public void setEveningFaxNo(String value) {
        this.eveningFaxNo = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getEveningPhone()
     */
    @Override
    public String getEveningPhone() {
        return eveningPhone;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setEveningPhone(java.lang.String)
     */
    @Override
    public void setEveningPhone(String value) {
        this.eveningPhone = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getFirstName()
     */
    @Override
    public String getFirstName() {
        return firstName;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setFirstName(java.lang.String)
     */
    @Override
    public void setFirstName(String value) {
        this.firstName = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getIsAddressVerified()
     */
    @Override
    public String getIsAddressVerified() {
        return isAddressVerified;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setIsAddressVerified(java.lang.String)
     */
    @Override
    public void setIsAddressVerified(String value) {
        this.isAddressVerified = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getIsCommercialAddress()
     */
    @Override
    public String getIsCommercialAddress() {
        return isCommercialAddress;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setIsCommercialAddress(java.lang.String)
     */
    @Override
    public void setIsCommercialAddress(String value) {
        this.isCommercialAddress = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getJobTitle()
     */
    @Override
    public String getJobTitle() {
        return jobTitle;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setJobTitle(java.lang.String)
     */
    @Override
    public void setJobTitle(String value) {
        this.jobTitle = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getLastName()
     */
    @Override
    public String getLastName() {
        return lastName;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setLastName(java.lang.String)
     */
    @Override
    public void setLastName(String value) {
        this.lastName = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getLatitude()
     */
    @Override
    public String getLatitude() {
        return latitude;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setLatitude(java.lang.String)
     */
    @Override
    public void setLatitude(String value) {
        this.latitude = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getLongitude()
     */
    @Override
    public String getLongitude() {
        return longitude;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setLongitude(java.lang.String)
     */
    @Override
    public void setLongitude(String value) {
        this.longitude = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getMiddleName()
     */
    @Override
    public String getMiddleName() {
        return middleName;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setMiddleName(java.lang.String)
     */
    @Override
    public void setMiddleName(String value) {
        this.middleName = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getMobilePhone()
     */
    @Override
    public String getMobilePhone() {
        return mobilePhone;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setMobilePhone(java.lang.String)
     */
    @Override
    public void setMobilePhone(String value) {
        this.mobilePhone = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getOtherPhone()
     */
    @Override
    public String getOtherPhone() {
        return otherPhone;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setOtherPhone(java.lang.String)
     */
    @Override
    public void setOtherPhone(String value) {
        this.otherPhone = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getPersonID()
     */
    @Override
    public String getPersonID() {
        return personID;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setPersonID(java.lang.String)
     */
    @Override
    public void setPersonID(String value) {
        this.personID = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getPersonInfoKey()
     */
    @Override
    public String getPersonInfoKey() {
        return personInfoKey;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setPersonInfoKey(java.lang.String)
     */
    @Override
    public void setPersonInfoKey(String value) {
        this.personInfoKey = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getState()
     */
    @Override
    public String getState() {
        return state;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setState(java.lang.String)
     */
    @Override
    public void setState(String value) {
        this.state = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getSuffix()
     */
    @Override
    public String getSuffix() {
        return suffix;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setSuffix(java.lang.String)
     */
    @Override
    public void setSuffix(String value) {
        this.suffix = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getTaxGeoCode()
     */
    @Override
    public String getTaxGeoCode() {
        return taxGeoCode;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setTaxGeoCode(java.lang.String)
     */
    @Override
    public void setTaxGeoCode(String value) {
        this.taxGeoCode = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getTitle()
     */
    @Override
    public String getTitle() {
        return title;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setTitle(java.lang.String)
     */
    @Override
    public void setTitle(String value) {
        this.title = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getZipCode()
     */
    @Override
    public String getZipCode() {
        return zipCode;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#setZipCode(java.lang.String)
     */
    @Override
    public void setZipCode(String value) {
        this.zipCode = value;
    }

    /* (non-Javadoc)
     * @see com.mozu.sterling.model.order.ContactInfo#getADtype()
     */
    @Override
    public List<String> getADtype() {
        if (aDtype == null) {
            aDtype = new ArrayList<String>();
        }
        return this.aDtype;
    }

}

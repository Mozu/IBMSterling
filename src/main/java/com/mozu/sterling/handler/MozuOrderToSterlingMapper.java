package com.mozu.sterling.handler;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mozu.api.contracts.commerceruntime.commerce.CommerceUnitPrice;
import com.mozu.api.contracts.commerceruntime.fulfillment.FulfillmentInfo;
import com.mozu.api.contracts.commerceruntime.orders.Order;
import com.mozu.api.contracts.commerceruntime.orders.OrderItem;
import com.mozu.api.contracts.commerceruntime.orders.OrderNote;
import com.mozu.api.contracts.commerceruntime.payments.BillingInfo;
import com.mozu.api.contracts.commerceruntime.products.Product;
import com.mozu.api.contracts.commerceruntime.products.ProductPrice;
import com.mozu.api.contracts.core.Address;
import com.mozu.api.contracts.core.AuditInfo;
import com.mozu.api.contracts.core.Contact;
import com.mozu.api.contracts.core.Phone;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.order.ContactInfo;
import com.mozu.sterling.model.order.Item;
import com.mozu.sterling.model.order.LinePriceInfo;
import com.mozu.sterling.model.order.LineTax;
import com.mozu.sterling.model.order.LineTaxes;
import com.mozu.sterling.model.order.Note;
import com.mozu.sterling.model.order.Notes;
import com.mozu.sterling.model.order.OrderLine;
import com.mozu.sterling.model.order.OrderLines;
import com.mozu.sterling.model.order.PersonInfo;
import com.mozu.sterling.model.order.PersonInfoBillTo;
import com.mozu.sterling.model.order.PersonInfoShipTo;
import com.mozu.sterling.service.SterlingClient;

@Service
public class MozuOrderToSterlingMapper {
    /**
     * Map the Mozu order to the Sterling order.
     * 
     * @param mozuOrder
     * @return
     */
    public com.mozu.sterling.model.order.Order mapMozuOrderToSterling(Order mozuOrder, Setting setting) {
        com.mozu.sterling.model.order.Order sterlingOrder = new com.mozu.sterling.model.order.Order();

        String orderNoStr = mozuOrder.getOrderNumber() != null ? String.valueOf(mozuOrder.getOrderNumber()) : "";
        sterlingOrder.setOrderNo(orderNoStr);
        sterlingOrder.setEnterpriseCode(setting.getSterlingEnterpriseCode());
        if (setting.getSiteMap() != null) {
            String sellerCode = setting.getSiteMap().get(mozuOrder.getSiteId().toString());
            sterlingOrder.setSellerOrganizationCode(sellerCode);
        }
        
        if (setting.getShipMethodMap() != null && mozuOrder.getFulfillmentInfo() != null) {
            String mozuShippingCode = mozuOrder.getFulfillmentInfo().getShippingMethodCode();
            String serviceCode = setting.getSiteMap().get(mozuShippingCode);
            sterlingOrder.setCarrierServiceCode(serviceCode);
        }
        sterlingOrder.setOrderDate(mozuOrder.getAuditInfo().getCreateDate().toString("yyyyMMdd"));
        sterlingOrder.setOrderLines(getOrderLines(mozuOrder.getItems()));
        sterlingOrder.setPersonInfoShipTo(getPersonInfoShipTo(mozuOrder.getFulfillmentInfo()));
        sterlingOrder.setPersonInfoBillTo(getPersonInfoBillTo(mozuOrder.getBillingInfo()));
        sterlingOrder.setNotes(getNotes(mozuOrder.getNotes()));
        return sterlingOrder;
    }

    /**
     * Map the order items from Mozu to the Order lines in Sterling.
     * 
     * @param orderItems
     * @return the converted order lines of Sterling.
     */
    private OrderLines getOrderLines(List<OrderItem> orderItems) {
        OrderLines orderLines = new OrderLines();
        OrderLine orderLine = null;

        for (OrderItem orderItem : orderItems) {
            orderLine = new OrderLine();
            orderLine.setOrderedQty(String.valueOf(orderItem.getQuantity()));
            ProductPrice productPrice = null;
            if (orderItem.getProduct() != null) {
                Item sItem = new Item();
                Product product = orderItem.getProduct();
                sItem.setItemID(product.getProductCode());
                sItem.setUPCCode(product.getUpc());
                sItem.setItemShortDesc(product.getName());
                productPrice = product.getPrice();
                orderLine.setItem(sItem);
            }
            orderLine.setLinePriceInfo(getLinePriceInfo(orderItem, productPrice));
            if (orderItem.getIsTaxable()) {
                LineTaxes lineTaxes = new LineTaxes();
                LineTax lineTax = new LineTax();
                lineTax.setInvoicedTax(orderItem.getItemTaxTotal() != null ? orderItem.getItemTaxTotal().toString() : null);
                lineTax.setTax(orderItem.getTaxableTotal() != null ? orderItem.getTaxableTotal().toString(): null );
                lineTaxes.getLineTax().add(lineTax);
                orderLine.setLineTaxes(lineTaxes);
            }
            
            orderLines.getOrderLine().add(orderLine);
        }
        return orderLines;
    }

    private LinePriceInfo getLinePriceInfo(OrderItem orderItem, ProductPrice productPrice) {
        LinePriceInfo linePriceInfo = new LinePriceInfo();
        CommerceUnitPrice unitPrice = orderItem.getUnitPrice();
        linePriceInfo.setIsPriceLocked(SterlingClient.STERLING_BOOLEAN_VALUE_YES);
        if (unitPrice != null) {
            linePriceInfo.setUnitPrice(String.format("%.2f", unitPrice.getExtendedAmount()));
            linePriceInfo.setListPrice(String.format("%.2f", unitPrice.getListAmount()));
        }
        if (productPrice != null) {
            linePriceInfo.setRetailPrice(String.format("%.2f", productPrice.getPrice()));
        }
        return linePriceInfo;
    }

    private Notes getNotes(List<OrderNote> mozuNotes) {
        Notes sterlingNotes = new Notes();

        for (OrderNote orderNote : mozuNotes) {
            Note sterlingNote = new Note();
            sterlingNote.setNoteText(orderNote.getText());
            sterlingNote.setTranid(orderNote.getId());
            AuditInfo noteInfo = orderNote.getAuditInfo();
            if (noteInfo != null) {
                sterlingNote.setContactUser(noteInfo.getCreateBy());
                sterlingNote.setContactTime(noteInfo.getCreateDate().toString("yyyyMMdd HH:mm"));
            }
            sterlingNotes.getNote().add(sterlingNote);
        }

        return sterlingNotes;
    }

    protected ContactInfo getPersonInfo(Contact contact) {
        ContactInfo personInfo = null;
        if (contact != null) {
            personInfo = new PersonInfo();
            personInfo.setPersonID(String.valueOf(contact.getId()));
            personInfo.setLastName(contact.getLastNameOrSurname());
            personInfo.setFirstName(contact.getFirstName());
            personInfo.setMiddleName(contact.getMiddleNameOrInitial());
            personInfo.setEMailID(contact.getEmail());
            Address address = contact.getAddress();
            if (address != null) {
                personInfo.setAddressLine1(address.getAddress1());
                personInfo.setAddressLine2(address.getAddress2());
                personInfo.setAddressLine3(address.getAddress3());
                personInfo.setAddressLine4(address.getAddress4());
                personInfo.setCity(address.getCityOrTown());
                personInfo.setState(address.getStateOrProvince());
                personInfo.setCountry(address.getCountryCode());
                personInfo.setZipCode(address.getPostalOrZipCode());
            }
            personInfo.setCompany(contact.getCompanyOrOrganization());
            Phone phone = contact.getPhoneNumbers();
            if (phone != null) {
                personInfo.setDayPhone(phone.getWork());
                personInfo.setMobilePhone(phone.getMobile());
                personInfo.setEveningPhone(phone.getHome());
            }
        }

        return personInfo;
    }

    protected PersonInfoShipTo getPersonInfoShipTo(FulfillmentInfo fulfillmentInfo) {
        PersonInfoShipTo personInfoShipTo = new PersonInfoShipTo();
        BeanUtils.copyProperties(getPersonInfo(fulfillmentInfo.getFulfillmentContact()), personInfoShipTo);

        // personInfoShipTo.setIsCommercialAddress(fulfillmentInfo.getIsDestinationCommercial().toString());
        return personInfoShipTo;
    }

    protected PersonInfoBillTo getPersonInfoBillTo(BillingInfo billingInfo) {
        PersonInfoBillTo personInfoBillTo = new PersonInfoBillTo();
        BeanUtils.copyProperties(getPersonInfo(billingInfo.getBillingContact()), personInfoBillTo);

        return personInfoBillTo;
    }

}

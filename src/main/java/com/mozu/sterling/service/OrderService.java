package com.mozu.sterling.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.mozu.api.ApiContext;
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
import com.mozu.api.contracts.event.Event;
import com.mozu.api.resources.commerce.OrderResource;
import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.order.Item;
import com.mozu.sterling.model.order.LinePriceInfo;
import com.mozu.sterling.model.order.Note;
import com.mozu.sterling.model.order.Notes;
import com.mozu.sterling.model.order.OrderLine;
import com.mozu.sterling.model.order.OrderLines;
import com.mozu.sterling.model.order.PersonInfo;
import com.mozu.sterling.model.order.PersonInfoBillTo;
import com.mozu.sterling.model.order.PersonInfoShipTo;

/**
 * Service for reading, mapping, and sending Orders from to Mozu to Sterling.
 * 
 * @author bob_hewett
 *
 */
@Service
public class OrderService extends SterlingClient {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public final static String ORDER_SERVICE_NAME = "createOrder";

    @Autowired
    SterlingClient sterlingClient;

    @Autowired
    ConfigHandler configHandler;

    public OrderService () throws Exception {
        super();
    }
    /**
     * Create an order in mozu based on a Mozu event.
     * 
     * @param apiContext
     *            the api context
     * @param event
     *            the Mozu event.
     * @return true if the order was created in Sterling w/o errors.
     */
    public boolean createOrder(ApiContext apiContext, Event event) throws Exception {
        OrderResource orderResource = new OrderResource(apiContext);
        try {
            Order mozuOrder = orderResource.getOrder(event.getEntityId());
            return createOrder(apiContext, mozuOrder);
        } catch (Exception e) {
            logger.debug(String.format("Correlation ID: %s. Unable to get Order with id %s from Mozu: %s",
                    event.getCorrelationId(), event.getEntityId(), e.getMessage()));
            throw e;
        }
    }

    /**
     * Converts a Mozu order to a Sterling order and sends to the Sterling OMS
     * @param apiContext
     * @param mozuOrder mozu order to send to Sterling
     * @return
     */
    public boolean createOrder(ApiContext apiContext, Order mozuOrder) throws Exception {
        Setting setting = configHandler.getSetting(apiContext.getTenantId());
        com.mozu.sterling.model.order.Order sterlingOrder = mapOrderToSterling(mozuOrder, setting);

        Document inDoc = this.convertObjectToXml(sterlingOrder, com.mozu.sterling.model.order.Order.class);
        
        Document outDoc = sterlingClient.invoke(ORDER_SERVICE_NAME, inDoc, setting);
        
        return outDoc != null;
    }

    /**
     * Update orders in Mozu from a Sterling order.
     * @param sterlingOrder
     * @return
     */
    public boolean updateOrder (com.mozu.sterling.model.order.Order sterlingOrder) {
        // stub for updating status of orders from Sterling.
        
        return true;
    }
    /**
     * Map the Mozu order to the Sterling order.
     * 
     * @param mozuOrder
     * @return
     */
    private com.mozu.sterling.model.order.Order mapOrderToSterling(Order mozuOrder, Setting setting) {
        com.mozu.sterling.model.order.Order sterlingOrder = new com.mozu.sterling.model.order.Order();

        String orderNoStr = mozuOrder.getOrderNumber() != null ? String.valueOf(mozuOrder.getOrderNumber()) : "";
        sterlingOrder.setOrderNo(orderNoStr);
        sterlingOrder.setEnterpriseCode(setting.getSterlingEnterpriseCode());
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
            orderLines.getOrderLine().add(orderLine);
        }
        return orderLines;
    }

    private LinePriceInfo getLinePriceInfo(OrderItem orderItem, ProductPrice productPrice) {
        LinePriceInfo linePriceInfo = new LinePriceInfo();
        CommerceUnitPrice unitPrice = orderItem.getUnitPrice();
        linePriceInfo.setIsPriceLocked(STERLING_BOOLEAN_VALUE_YES);
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

    protected PersonInfo getPersonInfo(Contact contact) {
        PersonInfo personInfo = null;
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

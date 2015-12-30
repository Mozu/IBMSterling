package com.mozu.sterling.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mozu.api.ApiContext;
import com.mozu.api.contracts.commerceruntime.commerce.ChangeMessage;
import com.mozu.api.contracts.commerceruntime.commerce.CommerceUnitPrice;
import com.mozu.api.contracts.commerceruntime.discounts.AppliedDiscount;
import com.mozu.api.contracts.commerceruntime.discounts.Discount;
import com.mozu.api.contracts.commerceruntime.fulfillment.FulfillmentInfo;
import com.mozu.api.contracts.commerceruntime.fulfillment.Package;
import com.mozu.api.contracts.commerceruntime.fulfillment.PackageItem;
import com.mozu.api.contracts.commerceruntime.fulfillment.Pickup;
import com.mozu.api.contracts.commerceruntime.fulfillment.PickupItem;
import com.mozu.api.contracts.commerceruntime.orders.Order;
import com.mozu.api.contracts.commerceruntime.orders.OrderItem;
import com.mozu.api.contracts.commerceruntime.orders.OrderNote;
import com.mozu.api.contracts.commerceruntime.payments.BillingInfo;
import com.mozu.api.contracts.commerceruntime.payments.Payment;
import com.mozu.api.contracts.commerceruntime.payments.PaymentAction;
import com.mozu.api.contracts.commerceruntime.payments.PaymentGatewayInteraction;
import com.mozu.api.contracts.commerceruntime.payments.PaymentInteraction;
import com.mozu.api.contracts.commerceruntime.products.BundledProduct;
import com.mozu.api.contracts.commerceruntime.returns.Return;
import com.mozu.api.contracts.commerceruntime.returns.ReturnAction;
import com.mozu.api.contracts.commerceruntime.returns.ReturnItem;
import com.mozu.api.contracts.core.Address;
import com.mozu.api.contracts.core.AuditInfo;
import com.mozu.api.contracts.core.Contact;
import com.mozu.api.contracts.core.Phone;
import com.mozu.api.contracts.location.Location;
import com.mozu.api.contracts.productadmin.LocationInventoryAdjustment;
import com.mozu.api.contracts.productruntime.Product;
import com.mozu.api.resources.commerce.ReturnResource;
import com.mozu.api.resources.commerce.catalog.admin.products.LocationInventoryResource;

@Component
public class SterlingOrderToMozuMapper {
    private static final Logger logger = LoggerFactory.getLogger(SterlingOrderToMozuMapper.class);
    public static final String ANONYMOUS_EMAIL = "anonymous@lightspeed.com";
    public static final String ANONYMOUS_FIRST_NAME = "Anony";
    public static final String ANONYMOUS_LAST_NAME = "Mous";
    
    public static final String FULFILLED_STATUS ="Fulfilled";
    public static final String NOT_FULFILLED_STATUS ="NotFulfilled";
//    @Autowired
//    SaleToReturnMapper saleToReturnMapper;
//
//    /**
//     * Map a Sterling order to a Mozu order
//     * 
//     * @param sterlingOrder
//     *            Sterling order
//     * @param apiContext
//     * @return Mozu order
//     * @throws Exception
//     */
//    public Order saleToOrder(com.mozu.sterling.model.order.Order sterlingOrder, ApiContext apiContext, Location storeLocation) throws Exception {
//        Order order = new Order();
//
//        order.setTenantId(apiContext.getTenantId());
//        order.setSiteId(apiContext.getSiteId());
//
//        order.setAcceptedDate(getAcceptedDate(sterlingOrder));
//        order.setAmountRemainingForPayment(sterlingOrder.getBalance());
//
//        order.setChannelCode("LightSpeed"); // TODO: What do we set this to??
//        order.setDiscountTotal(sterlingOrder.getCalcDiscount());
//        order.setDiscountedSubtotal(sterlingOrder.getCalcSubtotal() - sterlingOrder.getCalcDiscount());
//        order.setDiscountedTotal(sterlingOrder.getCalcTotal() - sterlingOrder.getCalcDiscount());
//
//        order.setIsImport(true);
//        order.setImportDate(DateTime.now());
//        order.setType("Offline");
//        order.setChangeMessages(new ArrayList<ChangeMessage>());
//
//        order.setAcceptedDate(getAcceptedDate(sterlingOrder));
//        order.setSubmittedDate(getAcceptedDate(sterlingOrder));
//        if (sterlingOrder.getShipTo() != null && sterlingOrder.getShipTo().isShipped())
//            order.setClosedDate(getAcceptedDate(sterlingOrder));
//
//        AuditInfo auditInfo = new AuditInfo();
//        auditInfo.setCreateDate(getAcceptedDate(sterlingOrder));
//        auditInfo.setUpdateDate(getAcceptedDate(sterlingOrder));
//        order.setAuditInfo(auditInfo);
//
//        if (sterlingOrder.getCalcTax1() != null) {
//            if (sterlingOrder.getCalcTax2() != null)
//                order.setItemTaxTotal(sterlingOrder.getCalcTax1() + sterlingOrder.getCalcTax2());
//            else
//                order.setItemTaxTotal(sterlingOrder.getCalcTax1());
//        }
//
//        // Set external ID to the Lightspeed ID so we can match them up later
//        order.setExternalId(sterlingOrder.getSaleID().toString());
//
//        order.setSubmittedDate(getAcceptedDate(sterlingOrder));
//        order.setSubtotal(sterlingOrder.getCalcSubtotal());
//        order.setTaxTotal(sterlingOrder.getTaxTotal());
//        order.setTotal(sterlingOrder.getTotal());
//        order.setTotalCollected(sterlingOrder.getCalcPayments());
//
//        // save the Lightspeed customer ID here for now. This will be
//        // overwritten later but is needed now for processing of new
//        // or existing customers later.
//        try {
//            order.setCustomerAccountId(idMapper.getMozuCustomerId(apiContext, sterlingOrder.getCustomerID()));
//        } catch (Exception e) {
//            logger.warn("Exception getting customer id mapping for sale " + sterlingOrder.getSaleID());
//        }
//
//        if (sterlingOrder.getCustomer() != null && sterlingOrder.getCustomer().getContact() != null) {
//            Customer customer = sterlingOrder.getCustomer();
//            order.setAcceptsMarketing(!customer.getContact().isNoEmail());
//            // Make Sure Contact is not null
//            if (customer.getContact() != null) {
//                String email = null;
//                if (customer.getContact().getEmails() != null
//                        && customer.getContact().getEmails().getContactEmail() != null) {
//                    for (ContactEmail contactEmail : customer.getContact().getEmails().getContactEmail()) {
//                        if (contactEmail.getUseType().equalsIgnoreCase("Primary")) {
//                            email = contactEmail.getAddress();
//                            break;
//                        }
//                    }
//                }
//                order.setEmail(email);
//            }
//        }
//
//        // Add billing info, this may not exist
//        BillingInfo billingInfo = getBillingInfo(sterlingOrder, storeLocation);
//        order.setBillingInfo(billingInfo);
//
//        // Set the fulfilment info. This may be empty
//        FulfillmentInfo fulfillmentInfo = getFulfillmentInfo(sterlingOrder, storeLocation);
//        order.setFulfillmentInfo(fulfillmentInfo);
//
//        List<Payment> payments = getPayments(sterlingOrder, billingInfo);
//
//        // All sales items go into orderItems
//        List<OrderItem> orderItems = new ArrayList<OrderItem>();
//        // Items set to Pickup go into pickups
//        List<Pickup> pickups = new ArrayList<>();
//        // Items set to Ship go into packages
//        List<Package> packages = new ArrayList<Package>();
//
//        List<AppliedDiscount> discounts = new ArrayList<>();
//
//        if (sterlingOrder.getSaleLines() != null && sterlingOrder.getSaleLines().getSaleLines() != null) {
//
//            List<SaleLine> saleLines = sterlingOrder.getSaleLines().getSaleLines();
//            int lineId = 1;
//            for (SaleLine saleLine : saleLines) {
//                if (saleLine.getUnitQuantity() < 0) {
//                    // this is a return, see if it is known
//                    if (idMapper.getMozuReturnId(apiContext, saleLine.getSaleLineID()) == null) {
//                        try {
//                            createReturn(sterlingOrder, saleLine, billingInfo, payments, apiContext, storeLocation, lineId);
//                        } catch (Exception e) {
//                            logger.debug("Exception processsing return: " + e.getMessage());
//                            // try and keep going to handle the remaining items
//                            // in the sale
//                        }
//                    } else {
//                        logger.debug("Return not processed, refund already processed.");
//                    }
//                } else {
//                    OrderItem orderItem = new OrderItem();
//                    orderItem.setLineId(lineId);
//                    orderItem.setDiscountedTotal(saleLine.getUnitPrice());
//                    orderItem.setDiscountTotal(saleLine.getCalcLineDiscount());
//                    orderItem.setFulfillmentLocationCode(storeLocation.getCode());
//
//                    if (sterlingOrder.getShipTo() != null) {
//                        orderItem.setFulfillmentMethod(sterlingOrder.getShipTo().isShipped() ? "Pickup" : "Ship");
//                    } else {
//                        orderItem.setFulfillmentMethod("Pickup");
//                    }
//
//                    orderItem.setIsTaxable(saleLine.isTax());
//                    if (saleLine.getCalcTax1() != null) {
//                        if (saleLine.getCalcTax2() != null) {
//                            orderItem.setItemTaxTotal(saleLine.getCalcTax1() + saleLine.getCalcTax2());
//                        } else {
//                            orderItem.setItemTaxTotal(saleLine.getCalcTax1() + saleLine.getCalcTax2());
//                        }
//                    }
//                    orderItem.setQuantity(saleLine.getUnitQuantity());
//                    orderItem.setSubtotal(saleLine.getCalcSubtotal());
//                    orderItem.setTaxableTotal(saleLine.getCalcSubtotal());
//                    orderItem.setTotal(saleLine.getCalcTotal());
//                    orderItem.setExtendedTotal(saleLine.getCalcSubtotal());
//                    CommerceUnitPrice commerceUnitPrice = new CommerceUnitPrice();
//                    if (saleLine.getItem() != null)
//                        commerceUnitPrice.setListAmount(saleLine.getItem().getMsrp());
//                    commerceUnitPrice.setSaleAmount(saleLine.getUnitPrice());
//                    orderItem.setUnitPrice(commerceUnitPrice);
//
//                    if (saleLine.getItem() != null) {
//                        String productCode = saleLine.getItem().getCustomSku();
//
//                        Integer itemMatrixId = saleLine.getItem().getItemMatrixID();
//                        // if there is an ItemMatrix ID that is not 0 it is a
//                        // variation.
//                        Boolean isVariation = itemMatrixId != null && !itemMatrixId.equals(0);
//                        com.mozu.api.contracts.commerceruntime.products.Product lineProduct = getProduct(apiContext,
//                                productCode, isVariation);
//                        orderItem.setProduct(lineProduct);
//
//                        if (orderItem.getFulfillmentMethod().equals("Pickup")) {
//                            Pickup pickup;
//                            List<BundledProduct> bundledProducts = orderItem.getProduct().getBundledProducts();
//                            if (bundledProducts != null && bundledProducts.size() > 0) {
//                                for (BundledProduct bp : bundledProducts) {
//                                    pickup = createPickup(getAcceptedDate(sterlingOrder), storeLocation, bp.getProductCode(),
//                                            bp.getQuantity() * saleLine.getUnitQuantity(), lineId);
//                                    pickups.add(pickup);
//                                }
//                            } else {
//                                pickup = createPickup(getAcceptedDate(sterlingOrder), storeLocation, productCode,
//                                        saleLine.getUnitQuantity(), lineId);
//                                pickups.add(pickup);
//                            }
//                            order.setPickups(pickups);
//                            lineProduct.setFulfillmentStatus(FULFILLED_STATUS);
//                        } else {
//                            Package pkg = new Package();
//                            pkg.setFulfillmentDate(getAcceptedDate(sterlingOrder));
//                            pkg.setFulfillmentLocationCode(storeLocation.getCode());
//                            List<PackageItem> packageItems = new ArrayList<>();
//                            if (order.getItems() != null) {
//                                for (OrderItem item : order.getItems()) {
//                                    PackageItem pItem = new PackageItem();
//                                    if (item.getProduct() != null) {
//                                        String itemProductCode = ProductMappingUtils
//                                                .getActualProductCode(item.getProduct());
//                                        pItem.setProductCode(itemProductCode);
//                                    }
//                                    pItem.setQuantity(item.getQuantity());
//                                    packageItems.add(pItem);
//                                }
//                            }
//                            pkg.setItems(packageItems);
//                            pkg.setStatus(NOT_FULFILLED_STATUS);
//                            packages.add(pkg);
//                            order.setPackages(packages);
//                            lineProduct.setFulfillmentStatus(NOT_FULFILLED_STATUS);
//                        }
//                    }
//
//                    if (saleLine.getNote() != null && saleLine.getNote().getNote() != null) {
//                        List<OrderNote> orderNotes = new ArrayList<>();
//                        OrderNote orderNote = new OrderNote();
//                        orderNote.setText(saleLine.getNote().getNote());
//                        orderNotes.add(orderNote);
//                        order.setNotes(orderNotes);
//                    }
//
//                    // Map the discount from lightspeed
//                    LscDiscount lscDiscount = saleLine.getDiscount();
//                    if (lscDiscount != null) {
//                        Discount discount = new Discount();
//                        discount.setName(lscDiscount.getName());
//                        List<String> itemIds = new ArrayList<>();
//                        try {
//                            String mzId = idMapper.getMozuProductId(apiContext, saleLine.getItemID());
//                            if (mzId != null) {
//                                itemIds.add(mzId);
//                            }
//                        } catch (Exception e) {
//                            // no itemId, just move on
//                        }
//                        discount.setItemIds(itemIds);
//                        discount.setExpirationDate(DateTime.now());
//                        AppliedDiscount appliedDiscount = new AppliedDiscount();
//                        appliedDiscount.setExcluded(false);
//                        appliedDiscount.setDiscount(discount);
//                        appliedDiscount.setImpact(-1.0 * saleLine.getCalcLineDiscount());
//                        appliedDiscount.setCouponCode("");
//                        discounts.add(appliedDiscount);
//                    }
//                    orderItems.add(orderItem);
//                }
//                lineId++;
//            }
//        }
//        order.setOrderDiscounts(discounts);
//        order.setItems(orderItems);
//
//        // This section is where we have to makeup default values if not
//        // provided
//        if (sterlingOrder.isCompleted()) {
//            order.setFulfillmentStatus(FULFILLED_STATUS);
//            order.setPaymentStatus("Paid");
//            order.setStatus("Completed");
//        } else {
//            order.setFulfillmentStatus(NOT_FULFILLED_STATUS);
//            order.setPaymentStatus("Paid");
//            order.setStatus("Processing");
//
//            // If not complete, must have an associated payment
//            if (order.getPayments().size() == 0) {
//                Payment payment = new Payment();
//                payment.setAmountCollected(sterlingOrder.getTotal());
//                payment.setStatus("Collected");
//                // TODO: Force to check until Mozu adds more payment types
//                payment.setPaymentType("Check");
//                payments.add(payment);
//            }
//        }
//        order.setPayments(payments);
//
//        // Use default email address if not provided
//        if (order.getEmail() == null)
//            order.setEmail(ANONYMOUS_EMAIL);
//
//        return order;
//    }
//
//    private DateTime getAcceptedDate(com.mozu.sterling.model.order.Order sterlingOrder) {
//        DateTime acceptedDateTime = null;
//        DateTime saleTimeStamp = DateTimeUtils.convertFromLightSpeedTime(sterlingOrder.getOrderDate()());
//        DateTime saleLineTimeStamp = null;
//        if (sterlingOrder.getSaleLines() != null && sterlingOrder.getSaleLines().getSaleLines() != null
//                && sterlingOrder.getSaleLines().getSaleLines().size() > 0) {
//            SaleLine saleLine = sterlingOrder.getSaleLines().getSaleLines().get(0);
//            saleLineTimeStamp = DateTimeUtils.convertFromLightSpeedTime(saleLine.getCreateTime());
//        }
//
//        if (saleLineTimeStamp != null && saleLineTimeStamp.getMillis() > saleTimeStamp.getMillis()) {
//            acceptedDateTime = saleLineTimeStamp;
//        } else {
//            acceptedDateTime = saleTimeStamp;
//        }
//        return acceptedDateTime;
//    }
//
//    private void createReturn(Sale sale, SaleLine saleLine, BillingInfo billingInfo, List<Payment> payments,
//            ApiContext apiContext, Location storeLocation, int lineId) throws Exception {
//        Return saleReturn = saleToReturnMapper.saleToReturn(sale, saleLine, apiContext, storeLocation);
//
//        ReturnResource returnResource = new ReturnResource(apiContext);
//        try {
//            // create return
//            Return createdReturn = returnResource.createReturn(saleReturn);
//            ReturnAction rtnAction = new ReturnAction();
//
//            createdReturn.setReturnType("Refund");
//            createdReturn = returnResource.updateReturn(createdReturn, createdReturn.getId());
//
//            Order originalOrder = saleToReturnMapper.getOriginalOrder(saleLine, apiContext);
//            List<ReturnItem> returnItems = saleToReturnMapper.mapReturnItems(saleLine, originalOrder, apiContext,
//                    lineId);
//
//            for (ReturnItem returnItem : returnItems) {
//                returnResource.createReturnItem(returnItem, createdReturn.getId());
//            }
//
//            // Authorize return
//            rtnAction.setActionName("Authorize");
//            List<String> returnIds = new ArrayList<>();
//            returnIds.add(createdReturn.getId());
//            rtnAction.setReturnIds(returnIds);
//            returnResource.performReturnActions(rtnAction);
//
//            // create payment action
//            PaymentAction paymentAction = new PaymentAction();
//            paymentAction.setActionName("CreditPayment");
//            paymentAction.setAmount(saleReturn.getRefundAmount());
//            paymentAction.setCurrencyCode("USD"); // TODO: Fix this when other
//                                                  // codes supported
//            paymentAction.setInteractionDate(DateTimeUtils.convertFromLightSpeedTime(saleLine.getCreateTime()));
//
//            PaymentGatewayInteraction rtnPaymentInteraction = null;
//            // there could be several payments, try to find one that can be used
//            for (Payment payment : payments) {
//                if (payment.getPaymentServiceTransactionId() != null) {
//                    rtnPaymentInteraction = new PaymentGatewayInteraction();
//                    PaymentInteraction paymentInteraction = payment.getInteractions().get(0);
//                    rtnPaymentInteraction.setGatewayAuthCode(paymentInteraction.getGatewayAuthCode());
//                    rtnPaymentInteraction.setGatewayAVSCodes(paymentInteraction.getGatewayAVSCodes());
//                    rtnPaymentInteraction.setGatewayCVV2Codes(paymentInteraction.getGatewayCVV2Codes());
//                    rtnPaymentInteraction.setGatewayInteractionId(paymentInteraction.getGatewayInteractionId());
//                    rtnPaymentInteraction.setGatewayResponseCode(paymentInteraction.getGatewayResponseCode());
//                    rtnPaymentInteraction.setGatewayResponseText(paymentInteraction.getGatewayResponseText());
//                    rtnPaymentInteraction.setGatewayTransactionId(paymentInteraction.getGatewayInteractionId() != null
//                            ? paymentInteraction.getGatewayInteractionId().toString() : null);
//                    billingInfo.setPaymentType(payment.getPaymentType());
//                    break;
//                }
//            }
//
//            if (rtnPaymentInteraction == null) {
//                // This was not a credit card transaction so put in a check
//                // number to fake it out
//                paymentAction.setCheckNumber("000");
//                billingInfo.setPaymentType("Check");
//            }
//
//            paymentAction.setManualGatewayInteraction(rtnPaymentInteraction);
//
//            paymentAction.setNewBillingInfo(billingInfo);
//
//            // paymentAction.setReferenceSourcePaymentId(referenceSourcePaymentId);
//
//            returnResource.createPaymentActionForReturn(paymentAction, createdReturn.getId());
//
//            // perform refund action
//            rtnAction.setActionName("Refund");
//            returnResource.performReturnActions(rtnAction);
//
//            // perform completed action
//            rtnAction.setActionName("Close");
//            returnResource.performReturnActions(rtnAction);
//
//            idMapper.putMozuReturnId(apiContext, createdReturn.getId(), saleLine.getSaleLineID());
//
//            // update inventory
//            updateReturnInventory(returnItems, storeLocation, apiContext);
//        } catch (Exception e) {
//            logger.error("Unable to create a return for saleLine " + saleLine.getSaleLineID() + " Item "
//                    + saleLine.getItem().getId() + " " + e.getMessage());
//        }
//    }
//
//    private void updateReturnInventory(List<ReturnItem> items, Location storeLocation, ApiContext apiContext)
//            throws Exception {
//
//        LocationInventoryResource lir = new LocationInventoryResource(apiContext);
//
//        List<LocationInventoryAdjustment> adjustments = new ArrayList<>();
//        LocationInventoryAdjustment inventory = new LocationInventoryAdjustment();
//        inventory.setLocationCode(storeLocation.getCode());
//        adjustments.add(inventory);
//
//        for (ReturnItem item : items) {
//            if (item.getProduct().getProductUsage() == null || !item.getProduct().getProductUsage().equals("Bundle")) {
//                String productCode = ProductMappingUtils.getActualProductCode(item.getProduct());
//                inventory.setProductCode(productCode);
//                inventory.setType("Delta");
//                inventory.setValue(item.getQuantityRestockable());
//                try {
//                    lir.updateLocationInventory(adjustments, productCode);
//                } catch (Exception e) {
//                    logger.error("Unable to update inventory for product code " + productCode + " " + e.getMessage());
//                }
//            } else {
//                logger.debug("Product " + item.getProduct().getProductCode()
//                        + " is a bundle, updating inventory for bundle items");
//                List<BundledProduct> bundledProducts = item.getProduct().getBundledProducts();
//                for (BundledProduct bp : bundledProducts) {
//                    inventory.setProductCode(bp.getProductCode());
//                    inventory.setType("Delta");
//                    inventory.setValue(item.getQuantityRestockable() * bp.getQuantity());
//                    try {
//                        lir.updateLocationInventory(adjustments, bp.getProductCode());
//                    } catch (Exception e) {
//                        logger.error("Unable to update inventory for product code " + item.getProduct().getProductCode()
//                                + " " + e.getMessage());
//                    }
//                }
//            }
//        }
//    }
//
//    private FulfillmentInfo getFulfillmentInfo(Sale sale, Location storeLocation) {
//        FulfillmentInfo fulfillmentInfo = new FulfillmentInfo();
//        if (sale.getShipTo() != null) {
//            Contact fulfillmentContact = new Contact();
//            fulfillmentContact.setCompanyOrOrganization(sale.getShipTo().getCompany());
//            if (sale.getShipTo().getContact() != null) {
//                if (sale.getShipTo().getContact().getEmails() != null
//                        && sale.getShipTo().getContact().getEmails().getContactEmail() != null) {
//                    List<ContactEmail> lscEmails = sale.getShipTo().getContact().getEmails().getContactEmail();
//                    for (ContactEmail lscEmail : lscEmails) {
//                        if (lscEmail.getUseType().equalsIgnoreCase("Primary")) {
//                            fulfillmentContact.setEmail(lscEmail.getAddress());
//                            break;
//                        }
//                    }
//                    if (fulfillmentContact.getEmail() == null && lscEmails.size() > 0) {
//                        // there is an email but it is not primary, use it
//                        fulfillmentContact.setEmail(lscEmails.get(0).getAddress());
//                    }
//                }
//
//                fulfillmentContact.setFirstName(sale.getShipTo().getFirstName());
//                fulfillmentContact.setLastNameOrSurname(sale.getShipTo().getLastName());
//
//                if (sale.getShipTo().getContact().getAddresses() != null
//                        && sale.getShipTo().getContact().getAddresses().getAddresses() != null) {
//                    ContactAddress contactAddress = sale.getShipTo().getContact().getAddresses().getAddresses().get(0);
//                    if (contactAddress != null) {
//                        Address shipToAddress = new Address();
//                        shipToAddress.setAddress1(contactAddress.getAddress1());
//                        shipToAddress.setAddress2(contactAddress.getAddress2());
//                        shipToAddress.setCityOrTown(contactAddress.getCity());
//                        shipToAddress.setCountryCode(contactAddress.getCountry());
//                        shipToAddress.setPostalOrZipCode(contactAddress.getZip());
//                        shipToAddress.setStateOrProvince(contactAddress.getState());
//                        fulfillmentContact.setAddress(shipToAddress);
//                    }
//                }
//
//                if (sale.getShipTo().getContact().getPhones() != null
//                        && sale.getShipTo().getContact().getPhones().getPhones() != null) {
//                    Phone shipToPhoneNumbers = new Phone();
//                    List<ContactPhone> contactPhones = sale.getShipTo().getContact().getPhones().getPhones();
//                    for (ContactPhone phone : contactPhones) {
//                        if (phone.getUseType().equalsIgnoreCase("Home")) {
//                            shipToPhoneNumbers.setHome(phone.getNumber());
//                        } else if (phone.getUseType().equalsIgnoreCase("Mobile")) {
//                            shipToPhoneNumbers.setMobile(phone.getNumber());
//                        } else if (phone.getUseType().equalsIgnoreCase("Work")) {
//                            shipToPhoneNumbers.setWork(phone.getNumber());
//                        }
//                    }
//                    fulfillmentContact.setPhoneNumbers(shipToPhoneNumbers);
//                }
//                fulfillmentInfo.setFulfillmentContact(fulfillmentContact);
//            }
//
//            // skip for now, may need to add in later
//            // fulfillmentInfo.setShippingMethodCode(shippingMethodCode);
//            // fulfillmentInfo.setShippingMethodName(shippingMethodName);
//
//        }
//
//        // if the fulfilment info is not complete, fill in with data from
//        // the store location
//        Contact ffContact = null;
//        if (storeLocation != null && fulfillmentInfo.getFulfillmentContact() == null) {
//            ffContact = new Contact();
//        } else {
//            ffContact = fulfillmentInfo.getFulfillmentContact();
//        }
//
//        if (ffContact.getAddress() == null)
//            ffContact.setAddress(storeLocation.getAddress());
//        if (ffContact.getCompanyOrOrganization() == null)
//            ffContact.setCompanyOrOrganization(storeLocation.getName());
//        if (ffContact.getEmail() == null)
//            ffContact.setEmail(ANONYMOUS_EMAIL);
//        if (ffContact.getLastNameOrSurname() == null) {
//            ffContact.setFirstName(ANONYMOUS_FIRST_NAME);
//            ffContact.setLastNameOrSurname(ANONYMOUS_LAST_NAME);
//        }
//        if (ffContact.getPhoneNumbers() == null) {
//            Phone shopPhone = new Phone();
//            shopPhone.setWork(StringUtils.isEmpty(storeLocation.getPhone()) ? "5555551212" : storeLocation.getPhone());
//            ffContact.setPhoneNumbers(shopPhone);
//        }
//
//        fulfillmentInfo.setFulfillmentContact(ffContact);
//        return fulfillmentInfo;
//    }
//
//    private BillingInfo getBillingInfo(Sale sale, Location storeLocation) {
//        BillingInfo billingInfo = new BillingInfo();
//        Contact billingContact = new Contact();
//        if (sale.getCustomer() != null) {
//            Customer customer = sale.getCustomer();
//
//            billingContact.setCompanyOrOrganization(customer.getCompany());
//            if (customer.getContact().getEmails() != null) {
//                List<ContactEmail> lscEmails = customer.getContact().getEmails().getContactEmail();
//                for (ContactEmail lscEmail : lscEmails) {
//                    if (lscEmail.getUseType().equalsIgnoreCase("Primary")) {
//                        billingContact.setEmail(lscEmail.getAddress());
//                        break;
//                    }
//                }
//                if (billingContact.getEmail() == null && lscEmails.size() > 0) {
//                    // there is an email but it is not primary, use it
//                    billingContact.setEmail(lscEmails.get(0).getAddress());
//                }
//            }
//
//            billingContact.setFirstName(customer.getFirstName());
//            billingContact.setLastNameOrSurname(customer.getLastName());
//
//            if (customer.getContact().getAddresses() != null
//                    && customer.getContact().getAddresses().getAddresses() != null) {
//                ContactAddress contactAddress = customer.getContact().getAddresses().getAddresses().get(0);
//                if (contactAddress != null) {
//                    Address address = new Address();
//                    address.setAddress1(contactAddress.getAddress1());
//                    address.setAddress2(contactAddress.getAddress2());
//                    address.setCityOrTown(contactAddress.getCity());
//                    address.setCountryCode(contactAddress.getCountry());
//                    address.setPostalOrZipCode(contactAddress.getZip());
//                    address.setStateOrProvince(contactAddress.getState());
//                    billingContact.setAddress(address);
//                }
//            }
//
//            Phone phoneNumbers = new Phone();
//            if (customer.getContact().getPhones() != null && customer.getContact().getPhones().getPhones() != null) {
//                List<ContactPhone> contactPhones = customer.getContact().getPhones().getPhones();
//                for (ContactPhone phone : contactPhones) {
//                    if (phone.getUseType().equalsIgnoreCase("Home")) {
//                        phoneNumbers.setHome(phone.getNumber());
//                    } else if (phone.getUseType().equalsIgnoreCase("Mobile")) {
//                        phoneNumbers.setMobile(phone.getNumber());
//                    } else if (phone.getUseType().equalsIgnoreCase("Work")) {
//                        phoneNumbers.setWork(phone.getNumber());
//                    }
//                }
//                billingContact.setPhoneNumbers(phoneNumbers);
//            }
//        } else {
//            billingInfo.setIsSameBillingShippingAddress(true);
//        }
//        if (billingContact.getAddress() == null)
//            billingContact.setAddress(storeLocation.getAddress());
//        if (billingContact.getCompanyOrOrganization() == null)
//            billingContact.setCompanyOrOrganization(storeLocation.getName());
//        if (billingContact.getEmail() == null)
//            billingContact.setEmail(ANONYMOUS_EMAIL);
//        if (billingContact.getLastNameOrSurname() == null) {
//            billingContact.setFirstName(ANONYMOUS_FIRST_NAME);
//            billingContact.setLastNameOrSurname(ANONYMOUS_LAST_NAME);
//        }
//
//        billingInfo.setBillingContact(billingContact);
//        return billingInfo;
//    }
//
//    private List<Payment> getPayments(Sale sale, BillingInfo billingInfo) {
//        List<Payment> payments = new ArrayList<>();
//        if (sale.getSalePayments() != null && sale.getSalePayments().getSalePayments() != null) {
//            List<SalePayment> salePayments = sale.getSalePayments().getSalePayments();
//            for (SalePayment salePayment : salePayments) {
//                Payment payment = new Payment();
//                if (salePayment.getAmount() > 0) {
//                    payment.setAmountCollected(salePayment.getAmount());
//                } else if (salePayment.getAmount() < 0) {
//                    payment.setAmountCredited(salePayment.getAmount());
//                }
//
//                // Add payment type. Mozu does not support cash at this time, so
//                // if not a supported Mozu
//                // type, set as check. TODO: update when Mozu adds types.
//                if (salePayment.getPaymentType() != null
//                        && salePayment.getPaymentType().getName().equalsIgnoreCase("Credit Card")) {
//                    payment.setPaymentType("CreditCard");
//                } else if (salePayment.getPaymentType() != null
//                        && salePayment.getPaymentType().getName().equalsIgnoreCase("Gift Card")) {
//                    payment.setPaymentType("GiftCard");
//                } else {
//                    payment.setPaymentType("Check");
//                }
//                payment.setBillingInfo(billingInfo);
//                payment.setStatus("Collected");
//
//                if (salePayment.getCcCharge() != null) {
//                    payment.setPaymentServiceTransactionId(salePayment.getCcCharge().getGatewayTransID());
//                    List<PaymentInteraction> paymentInteractions = new ArrayList<>();
//                    PaymentInteraction paymentInteraction = new PaymentInteraction();
//                    paymentInteraction.setGatewayResponseCode(salePayment.getCcCharge().getAuthCode());
//                    paymentInteraction.setGatewayTransactionId(salePayment.getCcCharge().getGatewayTransID());
//                    paymentInteraction.setInteractionDate(
//                            DateTimeUtils.convertFromLightSpeedTime(salePayment.getCcCharge().getTimeStamp()));
//                    paymentInteraction.setAmount(salePayment.getCcCharge().getAmount());
//                    paymentInteraction.setCurrencyCode(salePayment.getCcCharge().getCurrency());
//                    paymentInteraction.setGatewayAuthCode(salePayment.getCcCharge().getAuthCode());
//                    payment.setInteractions(paymentInteractions);
//                }
//                payments.add(payment);
//            }
//        }
//        return payments;
//    }
//
//    private static Pickup createPickup(DateTime fulfillmentTime, Location storeLocation, String productCode,
//            Integer unitQuantity, int lineId) {
//        Pickup pickup = new Pickup();
//        pickup.setFulfillmentDate(fulfillmentTime);
//        pickup.setFulfillmentLocationCode(storeLocation.getCode());
//        pickup.setStatus(FULFILLED_STATUS);
//        PickupItem pi = new PickupItem();
//        pi.setLineId(lineId);
//        pi.setFulfillmentItemType("Physical");
//        pi.setProductCode(productCode);
//        pi.setQuantity(unitQuantity);
//        List<PickupItem> pickupItems = new ArrayList<>();
//        pickupItems.add(pi);
//        pickup.setItems(pickupItems);
//        return pickup;
//    }
//
//    public static Sale orderToSale(Order order) {
//        Sale sale = new Sale();
//
//        return sale;
//    }
//
//    private com.mozu.api.contracts.commerceruntime.products.Product getProduct(ApiContext apiContext,
//            String productCode, boolean isVariant) throws Exception {
//        com.mozu.api.contracts.commerceruntime.products.Product commerceRuntimeProduct = null;
//        try {
//            Product product = ProductMappingUtils.getProductFromMozu(apiContext, productCode, isVariant);
//            if (product == null) {
//                throw new RuntimeException(String.format(
//                        "The product with code %s cannot be found for the site %d.  Skipping import of order.",
//                        productCode, apiContext.getSiteId()));
//            }
//            commerceRuntimeProduct = ProductMappingUtils.convertProduct(product);
//        } catch (Exception e) {
//            // log the error and try and keep going without the product
//            StringBuilder errMsg = new StringBuilder("Unable to process Mozu product with product code ")
//                    .append(productCode).append(" Exception: ").append(e.getMessage());
//            logger.warn(errMsg.toString());
//            throw e;
//        }
//        return commerceRuntimeProduct;
//    }
}

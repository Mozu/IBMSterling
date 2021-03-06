package com.mozu.sterling.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mozu.api.ApiContext;
import com.mozu.api.contracts.commerceruntime.commerce.ChangeMessage;
import com.mozu.api.contracts.commerceruntime.commerce.CommerceUnitPrice;
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
import com.mozu.api.contracts.commerceruntime.payments.PaymentInteraction;
import com.mozu.api.contracts.commerceruntime.products.BundledProduct;
import com.mozu.api.contracts.commerceruntime.products.Product;
import com.mozu.api.contracts.core.Address;
import com.mozu.api.contracts.core.AuditInfo;
import com.mozu.api.contracts.core.Contact;
import com.mozu.api.contracts.core.Phone;
import com.mozu.api.contracts.customer.CustomerAccount;
import com.mozu.api.contracts.customer.CustomerAccountCollection;
import com.mozu.api.resources.commerce.customer.CustomerAccountResource;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.order.ChargeTransactionDetail;
import com.mozu.sterling.model.order.ContactInfo;
import com.mozu.sterling.model.order.CreditCardTransaction;
import com.mozu.sterling.model.order.Item;
import com.mozu.sterling.model.order.KitLine;
import com.mozu.sterling.model.order.KitLines;
import com.mozu.sterling.model.order.LineOverallTotals;
import com.mozu.sterling.model.order.LinePriceInfo;
import com.mozu.sterling.model.order.Note;
import com.mozu.sterling.model.order.OrderLine;
import com.mozu.sterling.model.order.OverallTotals;
import com.mozu.sterling.model.order.PaymentMethod;
import com.mozu.sterling.model.order.PersonInfoShipTo;
import com.mozu.sterling.model.order.RemainingFinancialTotals;
import com.mozu.sterling.model.organization.ShipNode;
import com.mozu.sterling.model.organization.ShipNodePersonInfo;
import com.mozu.sterling.service.SterlingOrganizationService;

@Component
public class SterlingOrderToMozuMapper {
    private static final Logger logger = LoggerFactory.getLogger(SterlingOrderToMozuMapper.class);
    public static final String ANONYMOUS_EMAIL = "anonymous@sterling.com";
    public static final String ANONYMOUS_FIRST_NAME = "Anony";
    public static final String ANONYMOUS_LAST_NAME = "Mous";

    public static final String FULFILLED_STATUS = "Fulfilled";
    public static final String NOT_FULFILLED_STATUS = "NotFulfilled";
    public static final String PACKAGE_STATUS = "Pending";
     @Autowired
     SterlingOrganizationService sterlingOrganizationService;

    /**
     * Map a Sterling order to a Mozu order
     * 
     * @param sterlingOrder
     *            Sterling order
     * @param apiContext
     * @return Mozu order
     * @throws Exception
     */
    public Order saleToOrder(com.mozu.sterling.model.order.Order sterlingOrder,Order order, ApiContext apiContext, Setting setting)
            throws Exception {
    	if(order==null){
    		order = new Order();
    	}

        order.setTenantId(apiContext.getTenantId());
        order.setSiteId(apiContext.getSiteId());

        order.setAcceptedDate(getAcceptedDate(sterlingOrder));
        order.setAmountRemainingForPayment(getRemainingPayment(sterlingOrder.getRemainingFinancialTotals()));

        order.setChannelCode("Sterling"); // TODO: What do we set this to??

        order.setIsImport(true);
        order.setImportDate(DateTime.now());
        order.setType("Offline");
        order.setChangeMessages(new ArrayList<ChangeMessage>());

        order.setAcceptedDate(getAcceptedDate(sterlingOrder));
        order.setSubmittedDate(getAcceptedDate(sterlingOrder));
        if (sterlingOrder.getIsShipComplete() != null && sterlingOrder.getIsShipComplete().equals('Y'))
            order.setClosedDate(getAcceptedDate(sterlingOrder));

        AuditInfo auditInfo = new AuditInfo();
        auditInfo.setCreateDate(getAcceptedDate(sterlingOrder));
        auditInfo.setUpdateDate(getAcceptedDate(sterlingOrder));
        order.setAuditInfo(auditInfo);

        if (sterlingOrder.getOverallTotals() != null) {
            OverallTotals sterlingTotal = sterlingOrder.getOverallTotals();

            if (sterlingTotal.getGrandTax() != null) {
                Double tax = Double.valueOf(sterlingTotal.getGrandTax());
                order.setItemTaxTotal(tax);
                order.setTaxTotal(tax);
            }
            if (sterlingTotal.getGrandTotal() != null) {
                order.setTotal(Double.valueOf(sterlingTotal.getGrandTotal()));
            }

        }

        // Set external ID to the IBM Sterling order no. so we can match them up
        // later
        order.setExternalId(sterlingOrder.getOrderNo());

        order.setSubmittedDate(getAcceptedDate(sterlingOrder));

        order.setEmail(sterlingOrder.getCustomerEMailID());
        BillingInfo billingInfo = getBillingInfo(sterlingOrder);
        order.setBillingInfo(billingInfo);

        // Set the fulfilment info. This may be empty
        FulfillmentInfo fulfillmentInfo = getFulfillmentInfo(sterlingOrder, setting);
        order.setFulfillmentInfo(fulfillmentInfo);

        updateMozuCustomerId(apiContext, order);

        List<Payment> payments = getPayments(sterlingOrder, billingInfo);
        order.setPayments(payments);

        // All sales items go into orderItems
        List<OrderItem> orderItems = new ArrayList<OrderItem>();

        // List<AppliedDiscount> discounts = new ArrayList<>();

        if (sterlingOrder.getOrderLines() != null && sterlingOrder.getOrderLines().getOrderLine() != null) {
            List<OrderLine> saleLines = sterlingOrder.getOrderLines().getOrderLine();
            int lineId = 1;
            for (OrderLine saleLine : saleLines) {
                OrderItem orderItem = new OrderItem();
                orderItem.setLineId(lineId);
                if (saleLine.getDeliveryMethod() != null) {
                    orderItem.setFulfillmentMethod(saleLine.getDeliveryMethod().equals("PICK") ? "Pickup" : "Ship");
                } else {
                    orderItem.setFulfillmentMethod("Pickup");
                }
                LineOverallTotals lineOverallTotals = saleLine.getLineOverallTotals();
                if (lineOverallTotals != null) {
                    int qty = lineOverallTotals.getPricingQty() == null ? 0
                            : Double.valueOf(lineOverallTotals.getPricingQty()).intValue();
                    orderItem.setQuantity(qty < 1 ? 1 : qty);
                    orderItem.setSubtotal(Double.valueOf(lineOverallTotals.getLineTotalWithoutTax() != null
                            ? lineOverallTotals.getLineTotalWithoutTax() : lineOverallTotals.getLineTotal()));
                    orderItem.setTaxableTotal(Double.valueOf(lineOverallTotals.getTax()));
                    orderItem.setTotal(Double.valueOf(lineOverallTotals.getLineTotal()));
                    orderItem.setExtendedTotal(Double.valueOf(lineOverallTotals.getExtendedPrice()));
                    orderItem.setDiscountedTotal(Double.valueOf(lineOverallTotals.getLineTotal()));
                    orderItem.setDiscountTotal(Double.valueOf(lineOverallTotals.getDiscount()));
                    orderItem.setShippingTotal(lineOverallTotals.getShippingTotal() != null
                            ? Double.valueOf(lineOverallTotals.getShippingTotal()) : 0.00);
                }

                LinePriceInfo linePriceInfo = saleLine.getLinePriceInfo();

                if (linePriceInfo != null) {
                    CommerceUnitPrice commerceUnitPrice = new CommerceUnitPrice();
                    Double unitPrice = Double.valueOf(linePriceInfo.getUnitPrice());
                    Double listPrice = Double.valueOf(linePriceInfo.getListPrice());
                    Double retailPrice = Double.valueOf(linePriceInfo.getSettledAmount());

                    if (unitPrice > 0.0) {
                        commerceUnitPrice.setOverrideAmount(unitPrice);
                    }
                    if (listPrice > 0.00) {
                        commerceUnitPrice.setListAmount(listPrice);
                    } else if (retailPrice > 0.0) {
                        commerceUnitPrice.setListAmount(retailPrice);
                    }

                    orderItem.setUnitPrice(commerceUnitPrice);
                    orderItem.setIsTaxable("Y".equals(linePriceInfo.getTaxableFlag()));
                }

                String shipNodeId = saleLine.getShipnode() != null ? saleLine.getShipnode().getShipnodeKey() : saleLine.getShipNode();
                if (StringUtils.isNotBlank(shipNodeId)) {
                    orderItem.setFulfillmentLocationCode(getStoreLocation(setting, shipNodeId));
                }
                if (saleLine.getNotes() != null && saleLine.getNotes().getNumberOfNotes() != null
                        && Integer.valueOf(saleLine.getNotes().getNumberOfNotes()) > 0) {
                    List<OrderNote> orderNotes = new ArrayList<>();
                    for (Note note : saleLine.getNotes().getNote()) {
                        OrderNote orderNote = new OrderNote();
                        orderNote.setText(note.getNoteText());
                        orderNotes.add(orderNote);
                    }
                    order.setNotes(orderNotes);
                }
                if (saleLine.getItem() != null) {
                    Item item = saleLine.getItem();
                    if (item == null) {
                        throw new Exception ("No item found in order");
                    }

                    String productCode = null;
                    boolean isVariant = false;

                    if ("Dynamic Physical Kit".equals(saleLine.getKitCode())) {
			KitLine primaryProduct = saleLine.getKitLines().getKitLine().iterator().next();
			productCode = primaryProduct.getItemID();
			isVariant = Boolean.TRUE.equals(primaryProduct.getProductClass());

			com.mozu.api.contracts.commerceruntime.products.Product lineProduct = getProduct(apiContext,
                                productCode, isVariant);
			replaceProductOptionsWithSterlingValues(lineProduct, saleLine, apiContext);
			orderItem.setProduct(lineProduct);

                    } else if ("BUNDLE".equals(saleLine.getKitCode())) {
			productCode = item.getItemID();
			isVariant = (item.getPrimaryInformation() != null && "Y".equals(item.getPrimaryInformation().getIsModelItem()));

			com.mozu.api.contracts.commerceruntime.products.Product lineProduct = getProduct(apiContext,
                                productCode, isVariant);
                        orderItem.setProduct(lineProduct);
                    } else if (saleLine.getBundleParentLine() != null) {
			// don't add to order items, ignore.
			continue;
                    } else {
			productCode = item.getItemID();
			isVariant = (item.getPrimaryInformation() != null && "Y".equals(item.getPrimaryInformation().getIsModelItem()));

			com.mozu.api.contracts.commerceruntime.products.Product lineProduct = getProduct(apiContext,
                                productCode, isVariant);
                        orderItem.setProduct(lineProduct);
                    }
                }
                orderItems.add(orderItem);

                lineId++;
            }

            order.setItems(orderItems);


            // This section is where we have to makeup default values if not
            String orderStatus = sterlingOrder.getStatus();
            if (orderStatus != null) {
                if (orderStatus.equals("Shipped")) {
                    order.setFulfillmentStatus(FULFILLED_STATUS);
                    order.setPaymentStatus("Paid");
                   // createFulfilledPackages(order);
                } else {
                    order.setFulfillmentStatus(NOT_FULFILLED_STATUS);
                    order.setPaymentStatus(null);
                   // createPendingPackages(order);
                }
                order.setStatus(getOrderStatus(orderStatus));
            } else {
                order.setPaymentStatus("Paid");
                order.setStatus("Processing");
            }

            // Use default email address if not provided
            if (order.getEmail() == null)
                order.setEmail(ANONYMOUS_EMAIL);
        }

        return order;
    }

    private void replaceProductOptionsWithSterlingValues (
		com.mozu.api.contracts.commerceruntime.products.Product lineProduct,
		OrderLine saleLine, ApiContext apiContext) throws Exception {
	boolean firstTime = true;

		if (saleLine.getKitLines() != null) {
			KitLines kitLines = saleLine.getKitLines();
			if (kitLines.getKitLine() != null) {
				List<KitLine> kitLineList = kitLines.getKitLine();
				for (KitLine kitLine : kitLineList) {
					if (firstTime) {
						//skip the first kit line because this is the primary product
						firstTime = false;
						continue;
					}

					com.mozu.api.contracts.commerceruntime.products.ProductOption matchedOption =
							findProductOptonForName(lineProduct.getOptions(), kitLine.getItemShortDesc());

					if (matchedOption != null) {
						if (matchedOption.getDataType() == null) {
							// This is a product extra paried with the product.
							if (StringUtils.isNotBlank(kitLine.getProductClass())) {
								Product product = getProduct(apiContext, kitLine.getItemID(),
										Boolean.TRUE.equals(kitLine.getProductClass()));

								if (product != null) {
									matchedOption.setValue(product.getProductCode());
									matchedOption.setStringValue(product.getName());
								}
							} else {
								matchedOption.setShopperEnteredValue(kitLine.getItemID());
							}
						} else {
							// This is ideal if datatype is dependable
							switch (matchedOption.getDataType().toLowerCase()) {
								case "product":
									Product product = getProduct(apiContext, kitLine.getItemID(),
											Boolean.TRUE.equals(kitLine.getProductClass()));

									if (product != null) {
										matchedOption.setValue(product.getProductCode());
										matchedOption.setStringValue(product.getName());
									}
									break;
								case "text":
									matchedOption.setShopperEnteredValue(kitLine.getItemID());
									break;
								case "number":
									matchedOption.setShopperEnteredValue(Double.valueOf(kitLine.getItemID()));
									break;
							}
						}
					} else {
						logger.warn("Product option {} not found in mozu product.", kitLine.getItemShortDesc());
					}
				}
			}
		}
    }

    private com.mozu.api.contracts.commerceruntime.products.ProductOption findProductOptonForName(
		List<com.mozu.api.contracts.commerceruntime.products.ProductOption> productOptions, String optionName) {
	com.mozu.api.contracts.commerceruntime.products.ProductOption matchedOption = null;

	    if (productOptions != null) {
		    for (com.mozu.api.contracts.commerceruntime.products.ProductOption option : productOptions) {
			    if (option.getName().equals(optionName)) {
				    matchedOption = option;
				    break;
			    }
		    }
	    }

	    return matchedOption;
    }

    private String getOrderStatus(String orderStatus) {
        switch (orderStatus) {
        case "Shipped":
        case "Included In Shipment":
            return "Closed";
        case "Scheduled":
        case "Released":
            return "Processing";
        case "Cancelled":
            return "Cancelled";
        case "Draft Order Created":
            return "Pending";
        case "Created":
            return "Accepted";
        }
        return "Processing";
    }

    /**
     * Create or update the customer in Mozu and attach it to the order by
     * setting the Customer ID. if an email is not found, the customer is
     * skipped.
     * 
     * @param order
     */
    private void updateMozuCustomerId(ApiContext apiContext, Order order) throws Exception {
        CustomerAccountResource car = new CustomerAccountResource(apiContext);
        String email = order.getEmail();
        if (StringUtils.isBlank(email)) {
            email = ANONYMOUS_EMAIL;
        }
        StringBuilder filter = new StringBuilder("emailAddress eq ").append(email);
        CustomerAccountCollection accounts = car.getAccounts(0, 1, null, filter.toString(), null, null, null, null,
                null);
        CustomerAccount customerAccount = null;
        if (accounts != null && accounts.getItems() != null && accounts.getTotalCount() > 0) {
            customerAccount = accounts.getItems().get(0);
        } else {
            Contact contact = order.getBillingInfo().getBillingContact();
            if (contact != null) {
                customerAccount = new CustomerAccount();
                customerAccount.setAcceptsMarketing(order.getAcceptsMarketing());
                customerAccount.setCompanyOrOrganization(contact.getCompanyOrOrganization());
                customerAccount.setEmailAddress(email);
                customerAccount.setFirstName(contact.getFirstName());
                customerAccount.setLastName(contact.getLastNameOrSurname());
                customerAccount.setTaxExempt(order.getIsTaxExempt());
            }

            customerAccount = car.addAccount(customerAccount);
        }
        order.setCustomerAccountId(customerAccount.getId());

    }

    private String getStoreLocation(Setting setting, String shipnode) {
        String mozuLocation = null;
        if (StringUtils.isNotBlank(shipnode)) {
            Map<String, String> locationMap = setting.getLocationMap();
            for (Entry<String, String> entry : locationMap.entrySet()) {
                if (shipnode.equals(entry.getValue())) {
                    mozuLocation = entry.getKey();
                    break;
                }
            }
        }

	if (StringUtils.isBlank(mozuLocation)) {
	    //default to the first location
	    mozuLocation = setting.getLocationMap().keySet().iterator().next();
	}
        return mozuLocation;
    }

    private FulfillmentInfo getFulfillmentInfo(com.mozu.sterling.model.order.Order sterlingOrder, Setting setting) throws Exception {
        FulfillmentInfo fulfillmentInfo = new FulfillmentInfo();
        PersonInfoShipTo personShipTo = sterlingOrder.getPersonInfoShipTo();
        ShipNodePersonInfo shipNodePersonInfo=getShipNodeAddress(sterlingOrder, setting);
        if (personShipTo != null) {
            fulfillmentInfo.setFulfillmentContact(populateShippingContact(personShipTo,shipNodePersonInfo));
            fulfillmentInfo.setIsDestinationCommercial("Y".equals(personShipTo.getIsCommercialAddress()));
        }
        if (StringUtils.isNotBlank(sterlingOrder.getScacAndService())) {
            fulfillmentInfo
                    .setShippingMethodCode(getShippingMethodCode(setting, sterlingOrder.getScacAndService()));
        }

        return fulfillmentInfo;
    }
    
    private ShipNodePersonInfo getShipNodeAddress(com.mozu.sterling.model.order.Order sterlingOrder, Setting setting) throws Exception{
    	String shipNodeId = null;
        if(sterlingOrder.getOrderLines()!=null &&
        		sterlingOrder.getOrderLines().getOrderLine()!=null &&
        		sterlingOrder.getOrderLines().getOrderLine().get(0)!=null 
        	){
        	shipNodeId = sterlingOrder.getOrderLines().getOrderLine().get(0).getShipNode();
        }
        List<ShipNode> shipNodes = sterlingOrganizationService.getShipNodes(setting);
        ShipNode shipNodeObject =null;
        if(shipNodeId!=null){
            for (ShipNode shipNode : shipNodes) {
				if(shipNode.getShipNode().equalsIgnoreCase(shipNodeId)){
					shipNodeObject=shipNode;
					break;
				}
			}
        }
		return shipNodeObject!=null?shipNodeObject.getShipNodePersonInfo():null;
    	
    }

    private String getShippingMethodCode(Setting setting, String sterlingShipCode) {
        Map<String, String> shippingCodeMap = setting.getShipMethodMap();
        String shippingMethodCode = null;
        Set<Entry<String, String>> shippingEntries = shippingCodeMap.entrySet();
        for (Entry<String, String> entry : shippingEntries) {
            if (sterlingShipCode.equals(entry.getValue())) {
                shippingMethodCode = entry.getKey();
                break;
            }
        }

        return shippingMethodCode;
    }

    private Double getRemainingPayment(RemainingFinancialTotals remainingFinancialTotals) {
        Double remainingPayment = 0.0;

        if (remainingFinancialTotals != null) {
            if (remainingFinancialTotals.getRemainingToCharge() != null) {
                remainingPayment = Double.valueOf(remainingFinancialTotals.getRemainingToCharge());
            }
            if (remainingPayment == 0.0 && remainingFinancialTotals.getRemainingToAuthorize() != null) {
                remainingPayment = Double.valueOf(remainingFinancialTotals.getRemainingToAuthorize());
            }
        }

        return remainingPayment;
    }

    private BillingInfo getBillingInfo(com.mozu.sterling.model.order.Order sterlingOrder) {
        BillingInfo billingInfo = new BillingInfo();
        billingInfo.setBillingContact(populateContact(sterlingOrder.getPersonInfoBillTo()));

        return billingInfo;
    }

    private Contact populateContact(ContactInfo contactInfo) {
        Contact contact = new Contact();
        if (contactInfo != null) {
            contact.setCompanyOrOrganization(contactInfo.getCompany());
            contact.setEmail(StringUtils.isNotBlank(contactInfo.getEMailID()) ? contactInfo.getEMailID() : ANONYMOUS_EMAIL);
            contact.setFirstName(StringUtils.isNotBlank(contactInfo.getFirstName()) ? contactInfo.getFirstName() : ANONYMOUS_FIRST_NAME);
            contact.setMiddleNameOrInitial(contactInfo.getMiddleName());
            contact.setLastNameOrSurname(StringUtils.isNotBlank(contactInfo.getLastName()) ? contactInfo.getLastName() : ANONYMOUS_LAST_NAME);
    
            Address address = new Address();
            address.setAddress1(contactInfo.getAddressLine1());
            address.setAddress2(contactInfo.getAddressLine2());
            address.setCityOrTown(contactInfo.getCity());
            address.setCountryCode(StringUtils.isNotBlank(contactInfo.getCountry()) ? contactInfo.getCountry() : "US");
            address.setPostalOrZipCode(StringUtils.isNotBlank(contactInfo.getZipCode()) ? contactInfo.getZipCode() : "00000");
            address.setStateOrProvince(contactInfo.getState());
    
            contact.setAddress(address);
            Phone phone = new Phone();
            phone.setHome(contactInfo.getEveningPhone());
            phone.setMobile(contactInfo.getMobilePhone());
            phone.setWork(contactInfo.getDayPhone());
            if (StringUtils.isBlank(phone.getHome()) && 
                    StringUtils.isBlank(phone.getWork()) &&
                    StringUtils.isBlank(phone.getMobile())) {
                phone.setHome("212-555-1212");
            }
            contact.setPhoneNumbers(phone);
        }
        return contact;
    }
    
    private Contact populateShippingContact(ContactInfo contactInfo,ShipNodePersonInfo shipNodeContactInfo) {
        Contact contact = new Contact();
        if (contactInfo != null) {
            contact.setCompanyOrOrganization(contactInfo.getCompany());
            contact.setEmail(StringUtils.isNotBlank(contactInfo.getEMailID()) ? contactInfo.getEMailID() : ANONYMOUS_EMAIL);
            contact.setFirstName(StringUtils.isNotBlank(contactInfo.getFirstName()) ? contactInfo.getFirstName() : ANONYMOUS_FIRST_NAME);
            contact.setMiddleNameOrInitial(contactInfo.getMiddleName());
            contact.setLastNameOrSurname(StringUtils.isNotBlank(contactInfo.getLastName()) ? contactInfo.getLastName() : ANONYMOUS_LAST_NAME);
    
            Address address = new Address();
            address.setAddress1(contactInfo.getAddressLine1());
            address.setAddress2(contactInfo.getAddressLine2());
            address.setCityOrTown(contactInfo.getCity());
            address.setCountryCode(StringUtils.isNotBlank(contactInfo.getCountry()) ? contactInfo.getCountry() : "US");
            address.setPostalOrZipCode(StringUtils.isNotBlank(contactInfo.getZipCode()) ? contactInfo.getZipCode() : "00000");
            address.setStateOrProvince(contactInfo.getState());
            
            if(shipNodeContactInfo!=null){
            	address.setAddress1(StringUtils.isBlank(address.getAddress1())?shipNodeContactInfo.getAddressLine1():address.getAddress1());
            	address.setAddress2(StringUtils.isBlank(address.getAddress2())?shipNodeContactInfo.getAddressLine2():address.getAddress2());
            	address.setCityOrTown(StringUtils.isBlank(address.getCityOrTown())?shipNodeContactInfo.getCity():address.getCityOrTown());
            	address.setStateOrProvince(StringUtils.isBlank(address.getStateOrProvince())?shipNodeContactInfo.getState():address.getStateOrProvince());
          }
    
            contact.setAddress(address);
            Phone phone = new Phone();
            phone.setHome(contactInfo.getEveningPhone());
            phone.setMobile(contactInfo.getMobilePhone());
            phone.setWork(contactInfo.getDayPhone());
            if (StringUtils.isBlank(phone.getHome()) && 
                    StringUtils.isBlank(phone.getWork()) &&
                    StringUtils.isBlank(phone.getMobile())) {
                phone.setHome("212-555-1212");
            }
            contact.setPhoneNumbers(phone);
        }
        return contact;
    }

    private DateTime getAcceptedDate(com.mozu.sterling.model.order.Order sterlingOrder) {
        DateTime saleTimeStamp = convertFromSterlingTime(sterlingOrder.getOrderDate());

        return saleTimeStamp;
    }

    private List<Payment> getPayments(com.mozu.sterling.model.order.Order sterlingOrder, BillingInfo billingInfo) {
        List<Payment> payments = new ArrayList<>();
        if (sterlingOrder.getPaymentMethods() != null && sterlingOrder.getPaymentMethods().getPaymentMethod() != null) {
            List<PaymentMethod> salePayments = sterlingOrder.getPaymentMethods().getPaymentMethod();
            List<ChargeTransactionDetail> chargeDetails = sterlingOrder.getChargeTransactionDetails()
                    .getChargeTransactionDetail();
            int i = 0;
            for (PaymentMethod salePayment : salePayments) {
                ChargeTransactionDetail chargeDetail = null;
                if (chargeDetails.size() > i) {
                    chargeDetail = chargeDetails.get(i);
                }

                Payment payment = new Payment();

                // Add payment type. Mozu does not support cash at this time, so
                // if not a supported Mozu
                // type, set as check. TODO: update when Mozu adds types.
                if (salePayment.getPaymentType() == null) {
                    payment.setPaymentType("Check");
                } else if (salePayment.getPaymentType().equalsIgnoreCase("CREDITCARD")) {
                    payment.setPaymentType("CreditCard");
                } else if (salePayment.getPaymentType().equalsIgnoreCase("Gift Card")) {
                } else {
                    payment.setPaymentType("Check");
                }
                payment.setBillingInfo(billingInfo);

                if (chargeDetail != null) {
                    Double creditAmount = chargeDetail.getCreditAmount() != null
                            ? Double.valueOf(chargeDetail.getCreditAmount()) : 0.0;
                    Double amountCollected = chargeDetail.getBookAmount() != null
                            ? Double.valueOf(chargeDetail.getBookAmount()) : 0.0;
                    payment.setAmountCollected(amountCollected);
                    payment.setAmountCredited(creditAmount);

                    payment.setStatus(getPaymentStatus(chargeDetail.getStatus()));

                    if (chargeDetail.getCreditCardTransactions() != null
                            && chargeDetail.getCreditCardTransactions().getCreditCardTransaction() != null) {
                        CreditCardTransaction creditCardTransaction = chargeDetail.getCreditCardTransactions()
                                .getCreditCardTransaction();
                        payment.setPaymentServiceTransactionId(creditCardTransaction.getRequestId());
                        List<PaymentInteraction> paymentInteractions = new ArrayList<>();
                        PaymentInteraction paymentInteraction = new PaymentInteraction();
                        paymentInteraction.setGatewayResponseCode(creditCardTransaction.getTranReturnCode());
                        paymentInteraction.setGatewayTransactionId(creditCardTransaction.getChargeTransactionKey());
                        paymentInteraction
                                .setInteractionDate(convertFromSterlingTime(creditCardTransaction.getAuthTime()));
                        paymentInteraction.setAmount(Double.valueOf(creditCardTransaction.getTranAmount()));
                        paymentInteraction.setCurrencyCode("USD");
                        paymentInteraction.setGatewayAuthCode(creditCardTransaction.getAuthCode());
                        payment.setInteractions(paymentInteractions);
                    }
                }
                payments.add(payment);
            }
        }
        return payments;
    }

    private String getPaymentStatus(String status) {
        switch (status) {
        case "OPEN":
            return "Pending";
        case "CLOSED":
            return "Collected";
        case "ERROR":
            return "Declined";
        case "CHECKED":
            return "Authorized";
        case "VOIDED":
            return "Voided";

        }
        return null;
    }

    private void createFulfilledPackages(Order order) {
	createPackages(order, FULFILLED_STATUS, FULFILLED_STATUS);
    }

    private void createPendingPackages(Order order) {
	createPackages(order, NOT_FULFILLED_STATUS, PACKAGE_STATUS);
    }

    private void createPackages(Order order, String packageStatus, String productStatus) {
	// Items set to Pickup go into pickups
        List<Pickup> pickups = new ArrayList<>();
     // Items set to Ship go into packages
        List<Package> packages = new ArrayList<Package>();

	for (OrderItem orderItem : order.getItems()) {
		// setup pickup or package
	        if (StringUtils.isBlank(orderItem.getFulfillmentLocationCode())) {
			throw new RuntimeException (String.format("The order %s does not have a set fulfillment location"
					+ " which is required for a package.", order.getExternalId()));
	        }
	        if (orderItem.getFulfillmentMethod().equals("Pickup")) {
	            Pickup pickup;
	            List<BundledProduct> bundledProducts =
	            orderItem.getProduct().getBundledProducts();
	            if (bundledProducts != null && bundledProducts.size() > 0) {
	                for (BundledProduct bp : bundledProducts) {
	                    pickup = createPickup(order.getAcceptedDate(),
	                    orderItem.getFulfillmentLocationCode(),
	                    bp.getProductCode(),
	                    bp.getQuantity() * Integer.valueOf(orderItem.getQuantity()), orderItem.getLineId());
	                    pickups.add(pickup);
	                }
	            } else {
			pickup = createPickup(order.getAcceptedDate(),
					orderItem.getFulfillmentLocationCode(),
					orderItem.getProduct().getProductCode(),
					Integer.valueOf(orderItem.getQuantity()), orderItem.getLineId());
		            pickups.add(pickup);
	             }

	             order.setPickups(pickups);
	             orderItem.getProduct().setFulfillmentStatus(FULFILLED_STATUS);
	        } else {
			Package pkg = new Package();
	            pkg.setFulfillmentDate(order.getAcceptedDate());
	            pkg.setFulfillmentLocationCode(orderItem.getFulfillmentLocationCode());
	            List<PackageItem> packageItems = new ArrayList<>();

	            PackageItem pItem = new PackageItem();

	            if (orderItem.getProduct() != null) {
	                String itemProductCode = ProductMappingUtils
	                .getActualProductCode(orderItem.getProduct());
	                pItem.setProductCode(itemProductCode);
	            }

	            pItem.setQuantity(orderItem.getQuantity());
	            packageItems.add(pItem);

	            pkg.setItems(packageItems);
	            pkg.setStatus(packageStatus);
	            packages.add(pkg);
	            order.setPackages(packages);
	            orderItem.getProduct().setFulfillmentStatus(productStatus);
	        }
	}
    }

	private static Pickup createPickup(DateTime fulfillmentTime, String storeLocation, String productCode,
		Integer unitQuantity, int lineId) {
		Pickup pickup = new Pickup();
		pickup.setFulfillmentDate(fulfillmentTime);
		pickup.setFulfillmentLocationCode(storeLocation);
		pickup.setStatus(FULFILLED_STATUS);
		PickupItem pi = new PickupItem();
		pi.setLineId(lineId);
		pi.setFulfillmentItemType("Physical");
		pi.setProductCode(productCode);
		pi.setQuantity(unitQuantity);
		List<PickupItem> pickupItems = new ArrayList<>();
		pickupItems.add(pi);
		pickup.setItems(pickupItems);
		return pickup;
	}

    // public static Sale orderToSale(Order order) {
    // Sale sale = new Sale();
    //
    // return sale;
    // }
    //
    private Product getProduct(ApiContext apiContext, String productCode, boolean isVariant) throws Exception {
        Product commerceRuntimeProduct = null;
        try {
            com.mozu.api.contracts.productruntime.Product product = ProductMappingUtils.getProductFromMozu(apiContext,
                    productCode, isVariant);
            if (product == null) {
                throw new RuntimeException(String.format(
                        "The product with code %s cannot be found for the site %d. Skippingimport of order.",
                        productCode, apiContext.getSiteId()));
            }
            commerceRuntimeProduct = ProductMappingUtils.convertProduct(product);
        } catch (Exception e) {
            // log the error and try and keep going without the product
            StringBuilder errMsg = new StringBuilder("Unable to process Mozu product with product code ")
                    .append(productCode).append(" Exception: ").append(e.getMessage());
            logger.warn(errMsg.toString());
            throw e;
        }
        return commerceRuntimeProduct;
    }

    private static final String FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";

    public DateTime convertFromSterlingTime(String timeString) {
        DateTime dt = null;

        if (timeString != null) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(FORMAT_PATTERN);
            dt = formatter.parseDateTime(timeString);
        }
        return dt;
    }

}

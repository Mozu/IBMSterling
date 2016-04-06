package com.mozu.sterling.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.mozu.api.ApiContext;
import com.mozu.api.contracts.commerceruntime.commerce.CommerceUnitPrice;
import com.mozu.api.contracts.commerceruntime.discounts.AppliedDiscount;
import com.mozu.api.contracts.commerceruntime.discounts.ShippingDiscount;
import com.mozu.api.contracts.commerceruntime.fulfillment.FulfillmentInfo;
import com.mozu.api.contracts.commerceruntime.orders.Order;
import com.mozu.api.contracts.commerceruntime.orders.OrderItem;
import com.mozu.api.contracts.commerceruntime.orders.OrderNote;
import com.mozu.api.contracts.commerceruntime.payments.BillingInfo;
import com.mozu.api.contracts.commerceruntime.payments.Payment;
import com.mozu.api.contracts.commerceruntime.payments.PaymentCard;
import com.mozu.api.contracts.commerceruntime.products.Product;
import com.mozu.api.contracts.commerceruntime.products.ProductOption;
import com.mozu.api.contracts.commerceruntime.products.ProductPrice;
import com.mozu.api.contracts.core.Address;
import com.mozu.api.contracts.core.AuditInfo;
import com.mozu.api.contracts.core.Contact;
import com.mozu.api.contracts.core.Phone;
import com.mozu.api.resources.commerce.catalog.admin.ProductResource;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.order.ContactInfo;
import com.mozu.sterling.model.order.HeaderCharge;
import com.mozu.sterling.model.order.HeaderCharges;
import com.mozu.sterling.model.order.Item;
import com.mozu.sterling.model.order.KitLine;
import com.mozu.sterling.model.order.KitLines;
import com.mozu.sterling.model.order.LineCharge;
import com.mozu.sterling.model.order.LineCharges;
import com.mozu.sterling.model.order.LinePriceInfo;
import com.mozu.sterling.model.order.LineTax;
import com.mozu.sterling.model.order.LineTaxes;
import com.mozu.sterling.model.order.Note;
import com.mozu.sterling.model.order.Notes;
import com.mozu.sterling.model.order.OrderLine;
import com.mozu.sterling.model.order.OrderLines;
import com.mozu.sterling.model.order.OverallTotals;
import com.mozu.sterling.model.order.PaymentDetails;
import com.mozu.sterling.model.order.PaymentMethod;
import com.mozu.sterling.model.order.PaymentMethods;
import com.mozu.sterling.model.order.PersonInfo;
import com.mozu.sterling.model.order.PersonInfoBillTo;
import com.mozu.sterling.model.order.PersonInfoContact;
import com.mozu.sterling.model.order.PersonInfoMarkFor;
import com.mozu.sterling.model.order.PersonInfoShipTo;
import com.mozu.sterling.model.order.PersonInfoSoldTo;
import com.mozu.sterling.model.order.PrimaryInformation;
import com.mozu.sterling.model.organization.ShipNode;
import com.mozu.sterling.service.SterlingClient;

@Service
public class MozuOrderToSterlingMapper {
    /**
     * Map the Mozu order to the Sterling order.
     * 
     * @param mozuOrder
     * @return
     */
    public com.mozu.sterling.model.order.Order mapMozuOrderToSterling(Order mozuOrder, com.mozu.sterling.model.order.Order existingSterlingOrder, Setting setting, ApiContext apiContext) throws Exception {
	com.mozu.sterling.model.order.Order	sterlingOrder = new com.mozu.sterling.model.order.Order();
        String orderNoStr = mozuOrder.getOrderNumber() != null ? String.valueOf(mozuOrder.getOrderNumber()) : "";
        sterlingOrder.setOrderNo(orderNoStr);
        sterlingOrder.setEnterpriseCode(setting.getSterlingEnterpriseCode());
        if (setting.getSiteMap() != null) {
            String sellerCode = setting.getSiteMap().get(mozuOrder.getSiteId().toString());
            sterlingOrder.setSellerOrganizationCode(sellerCode);
        }
        String serviceCode = null;
        if (setting.getShipMethodMap() != null && mozuOrder.getFulfillmentInfo() != null) {
            String mozuShippingCode = mozuOrder.getFulfillmentInfo().getShippingMethodCode();
            if(mozuShippingCode!=null){
            	serviceCode = setting.getShipMethodMap().get(mozuShippingCode.toUpperCase());
            	sterlingOrder.setScacAndService(serviceCode);
            }
        }
        sterlingOrder.setOrderDate(mozuOrder.getAuditInfo().getCreateDate().toString("yyyyMMdd"));
        PersonInfoShipTo personInfoShipTo = getPersonInfoShipTo(mozuOrder.getFulfillmentInfo());
        OrderLines existingOrderLines = existingSterlingOrder!=null? existingSterlingOrder.getOrderLines(): null;
        sterlingOrder.setOrderLines(getOrderLines(mozuOrder.getItems(), setting, serviceCode, personInfoShipTo,existingOrderLines, apiContext));
        sterlingOrder.setPersonInfoShipTo(personInfoShipTo);
        sterlingOrder.setPersonInfoBillTo(getPersonInfoBillTo(mozuOrder.getBillingInfo()));
        sterlingOrder.setNotes(getNotes(mozuOrder.getNotes()));
        sterlingOrder.setHeaderCharges(getHeaderCharges(mozuOrder));
        sterlingOrder.setOrderType(mozuOrder.getType());
        setPaymentStatus(mozuOrder, sterlingOrder);
        setPayment(mozuOrder, sterlingOrder);
        return sterlingOrder;
    }

    /**
     * Map the order items from Mozu to the Order lines in Sterling.
     * 
     * @param orderItems
     * @return the converted order lines of Sterling.
     */
    private OrderLines getOrderLines(List<OrderItem> orderItems,Setting setting,String serviceCode, PersonInfoShipTo personInfoShipTo , OrderLines sterlingOrderLines, ApiContext apiContext) throws Exception {
	OrderLines	orderLines = new OrderLines();
        OrderLine orderLine = null;
       List<OrderLine> existingSterlingOrderLines=new ArrayList<OrderLine>();
        for (OrderItem orderItem : orderItems) {
        	OrderLine existingSterlingOrderLine=null;
        	orderLine = new OrderLine();
        	if(sterlingOrderLines!=null){
        		existingSterlingOrderLine = getExistingSterlingOrderLine(sterlingOrderLines.getOrderLine(), orderItem);
        		
        	}
         	if(existingSterlingOrderLine!=null){
         		orderLine.setOrderLineKey(existingSterlingOrderLine.getOrderLineKey());
         		existingSterlingOrderLines.add(existingSterlingOrderLine);
         	}
         	
            orderLine.setPrimeLineNo(orderItem.getLineId().toString());
            orderLine.setShipNode(setting.getLocationMap().get(orderItem.getFulfillmentLocationCode()));
            orderLine.setScacAndService(serviceCode);
            orderLine.setPersonInfoShipTo(personInfoShipTo);
            orderLine.setOrderedQty(String.valueOf(orderItem.getQuantity()));
            
           
            if(orderItem.getFulfillmentMethod().equalsIgnoreCase("Ship")){
            	orderLine.setDeliveryMethod("SHP");
            }else if(orderItem.getFulfillmentMethod().equalsIgnoreCase("Pickup")){
            	orderLine.setDeliveryMethod("PICK");
            }
            ProductPrice productPrice = null;
            if (orderItem.getProduct() != null) {
                Item sItem = new Item();
                Product product = orderItem.getProduct();
                if(product.getVariationProductCode()!=null){
                	sItem.setItemID(product.getVariationProductCode());
                	PrimaryInformation primaryInformation=new PrimaryInformation();
                	primaryInformation.setIsModelItem("Y");
                	sItem.setPrimaryInformation(primaryInformation);
                }else{
                	sItem.setItemID(product.getProductCode());
                }
                sItem.setUPCCode(product.getUpc());
                sItem.setItemShortDesc((product.getName()!=null && product.getName().length()>200)?product.getName().substring(0, 199):product.getName());
                sItem.setItemDesc((product.getDescription()!=null && product.getDescription().length()>500) ? product.getDescription().substring(0, 499):product.getDescription());
               
                sItem.setUnitOfMeasure("EACH");
                productPrice = product.getPrice();
                orderLine.setItem(sItem);

                KitLines kitLines = createKitLinesFromProductOptions(product, product.getOptions(), apiContext);
                orderLine.setKitLines(kitLines);

                if (kitLines.getKitLine() != null && !kitLines.getKitLine().isEmpty()) {
			sItem.setUPCCode("");
			sItem.setItemDesc("Mozu dynamic physical kit.");
			sItem.setItemID("MozuKit");

			orderLine.setKitCode("Dynamic Physical Kit");
                } else {
	                if(product.getBundledProducts()!=null && product.getBundledProducts().size()>0){
				orderLine.setKitCode("BUNDLE");
	                }
                }
                if(orderItem.getFulfillmentMethod().equalsIgnoreCase("Digital")){
			sItem.setProductLine("DigitalProduct");
                	
                }
           }
            
            orderLine.setLinePriceInfo(getLinePriceInfo(orderItem, productPrice));
            
          //  if (orderItem.getIsTaxable()!=null && orderItem.getIsTaxable()) {
                LineTaxes lineTaxes = new LineTaxes();
                LineTax lineTax = new LineTax();
                lineTax.setTaxName("Tax");
                lineTax.setInvoicedTax(orderItem.getItemTaxTotal() != null ? orderItem.getItemTaxTotal().toString() : null);
                lineTax.setTax(orderItem.getItemTaxTotal() != null ? orderItem.getItemTaxTotal().toString(): null );
                lineTaxes.getLineTax().add(lineTax);
                orderLine.setLineTaxes(lineTaxes);
                
          //  }
                LineCharges lineCharges=new LineCharges();
                LineCharge lineChargeDiscount=new LineCharge();
                lineChargeDiscount.setChargePerLine(orderItem.getDiscountTotal().toString());
                lineChargeDiscount.setChargeCategory("Discount");
                lineChargeDiscount.setIsDiscount("Y");
                lineCharges.getLineCharge().add(lineChargeDiscount);
                orderLine.setLineCharges(lineCharges);
                
            orderLines.getOrderLine().add(orderLine);
            
            
        }
        
        orderLines= cancelSterlingOrderLines(sterlingOrderLines, existingSterlingOrderLines, orderLines);
        
        return orderLines;
    }
    
    private OrderLines cancelSterlingOrderLines(OrderLines sterlingOrderLines,List<OrderLine> existingSterlingOrderLines,OrderLines orderLines ){
    	if(sterlingOrderLines!=null){
	        for(OrderLine sterlingOrderLine:sterlingOrderLines.getOrderLine()){
	        	if(!sterlingOrderLine.getStatus().equalsIgnoreCase("Cancelled")){
		        	boolean orderLineExists=false;
		        	for(OrderLine existingSterlingOrderLine:existingSterlingOrderLines){
		        		if(sterlingOrderLine.getOrderLineKey().equalsIgnoreCase(existingSterlingOrderLine.getOrderLineKey())){
		        			orderLineExists=true;
		        			break;
		        		}
		        	}
		        	if(!orderLineExists){
		        		OrderLine orderLineToBeCancelled =new OrderLine();
		        		if(sterlingOrderLine.getBundleParentLine() != null){
		        			boolean bundleParentExists=false;
		        			for(OrderLine existingSterlingOrderLine:existingSterlingOrderLines){
		        				if(sterlingOrderLine.getBundleParentLine().getOrderLineKey().equals(existingSterlingOrderLine.getOrderLineKey())){
		        					bundleParentExists=true;
		        					break;
		        				}
		        			}
		        			if(!bundleParentExists){
		        				orderLineToBeCancelled.setPrimeLineNo(sterlingOrderLine.getPrimeLineNo());
		        				orderLineToBeCancelled.setItem(sterlingOrderLine.getItem());
		        				orderLineToBeCancelled.setOrderLineKey(sterlingOrderLine.getOrderLineKey());
		        				orderLineToBeCancelled.setAction("CANCEL");
		        				orderLines.getOrderLine().add(orderLineToBeCancelled);
		        			}
		        		}else{
		        			orderLineToBeCancelled.setPrimeLineNo(sterlingOrderLine.getPrimeLineNo());
	        				orderLineToBeCancelled.setItem(sterlingOrderLine.getItem());
	        				orderLineToBeCancelled.setOrderLineKey(sterlingOrderLine.getOrderLineKey());
	        				orderLineToBeCancelled.setAction("CANCEL");
	        				orderLines.getOrderLine().add(orderLineToBeCancelled);
		        		}
		            
		        	}
	        	}
	        }
        }
		return orderLines;
    }
    
    private OrderLine getExistingSterlingOrderLine(List<OrderLine> orderLines,OrderItem orderItem){
    	OrderLine existingOrderLine =null;
    	for(OrderLine orderLine:orderLines){
    		if(orderLine.getItem().getItemID().equals(orderItem.getProduct().getProductCode()) && orderLine.getBundleParentLine() == null){
    	    	existingOrderLine=orderLine;
    	    	break;
    	    }
   		
    	}
		return existingOrderLine;
    	
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
                sterlingNote.setContactTime(noteInfo.getCreateDate()!=null?noteInfo.getCreateDate().toString("yyyyMMdd HH:mm"):null);
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
    
    protected PersonInfoMarkFor getPersonInfoMarkFor(FulfillmentInfo fulfillmentInfo) {
        PersonInfoMarkFor personInfoMarkFor = new PersonInfoMarkFor();
        BeanUtils.copyProperties(getPersonInfo(fulfillmentInfo.getFulfillmentContact()), personInfoMarkFor);

        return personInfoMarkFor;
    }
    
    protected PersonInfoContact getPersonInfoContact(FulfillmentInfo fulfillmentInfo) {
    	PersonInfoContact personInfoContact = new PersonInfoContact();
        BeanUtils.copyProperties(getPersonInfo(fulfillmentInfo.getFulfillmentContact()), personInfoContact);

        return personInfoContact;
    }
    
    protected PersonInfoSoldTo getPersonInfoSoldTo(FulfillmentInfo fulfillmentInfo) {
    	PersonInfoSoldTo personInfoSoldTo = new PersonInfoSoldTo();
        BeanUtils.copyProperties(getPersonInfo(fulfillmentInfo.getFulfillmentContact()), personInfoSoldTo);

        return personInfoSoldTo;
    }
    
    protected OverallTotals getOverallTotals(Order mozuOrder){
    	OverallTotals overallTotals=new OverallTotals();
        overallTotals.setGrandDiscount(mozuOrder.getDiscountTotal().toString());
        overallTotals.setGrandTotal(mozuOrder.getTotal().toString());
        overallTotals.setGrandTax(mozuOrder.getTaxTotal().toString());
        overallTotals.setGrandShippingTotal(mozuOrder.getShippingTotal().toString());
		return overallTotals;
    }

    protected HeaderCharges getHeaderCharges(Order mozuOrder){
    	Double orderDiscountTotal=0.0;
        for(AppliedDiscount appliedDiscount:mozuOrder.getOrderDiscounts()){
        	if(!appliedDiscount.getExcluded()){
        		orderDiscountTotal+=appliedDiscount.getImpact();
        	}
        }
        for(ShippingDiscount shippingDiscount:mozuOrder.getShippingDiscounts()){
        	if(!shippingDiscount.getDiscount().getExcluded()){
        		orderDiscountTotal+=shippingDiscount.getDiscount().getImpact();
        	}
        }
        HeaderCharges headerCharges = new HeaderCharges();
        List<HeaderCharge>  headerChargeList=headerCharges.getHeaderCharge();
        HeaderCharge headerChargeDiscount= new HeaderCharge();
        headerChargeDiscount.setIsDiscount("Y");
        headerChargeDiscount.setChargeCategory("Discount");
        headerChargeDiscount.setChargeAmount(orderDiscountTotal.toString());
        headerChargeList.add(headerChargeDiscount);
        
        HeaderCharge headerChargeShipping= new HeaderCharge();
        headerChargeShipping.setIsShippingCharge("Y");
        headerChargeShipping.setChargeCategory("Shipping");
        headerChargeShipping.setChargeAmount(mozuOrder.getShippingSubTotal().toString());
        headerChargeList.add(headerChargeShipping);
        
		return headerCharges;
    	
    }
    
    protected void setPaymentStatus(Order mozuOrder,com.mozu.sterling.model.order.Order sterlingOrder){
    	if(mozuOrder.getPaymentStatus().equalsIgnoreCase("paid"))
    		sterlingOrder.setPaymentStatus("PAID");
    	else
    		sterlingOrder.setPaymentStatus("AUTHORIZED");
    }
    
    protected void setPayment(Order mozuOrder,com.mozu.sterling.model.order.Order sterlingOrder){
    	List<Payment> validPayments = new ArrayList<Payment>();
        List<Payment> payments = mozuOrder.getPayments();
        for (Payment payment: payments) {
        	if (!payment.getStatus().equalsIgnoreCase("Voided") &&
        		!payment.getStatus().equalsIgnoreCase("Declined")) {
        		validPayments.add(payment);
        	}
        }
        PaymentMethods paymentMethods=new PaymentMethods();
    	List<PaymentMethod> paymentMethodList = paymentMethods.getPaymentMethod();
    	
        for(Payment validPayment:validPayments){
        	PaymentMethod paymentMethod=new PaymentMethod();
        	PaymentDetails paymentDetails= new PaymentDetails();
        	if(validPayment.getPaymentType().equalsIgnoreCase("CreditCard")){
        		paymentMethod.setPaymentType("CREDIT_CARD");
        		PaymentCard mozuCard =validPayment.getBillingInfo().getCard();
                 if (mozuCard!=null) {
                   paymentMethod.setCreditCardNo(mozuCard.getCardNumberPartOrMask());
                   paymentMethod.setCreditCardName(mozuCard.getNameOnCard());
                   paymentMethod.setCreditCardType(mozuCard.getPaymentOrCardType());
                   paymentMethod.setCreditCardExpDate(String.valueOf(mozuCard.getExpireMonth())+"/"+String.valueOf(mozuCard.getExpireYear()));
                  
                 }
        	}else if(validPayment.getPaymentType().equalsIgnoreCase("Check")){
        		paymentMethod.setPaymentType("CHECK");
        	//	paymentDetails.setChargeType("AUTHORIZATION");
            	//paymentDetails.setRequestAmount(validPayment.getAmountRequested().toString());
            	//paymentMethod.getPaymentDetails().add(paymentDetails);
        		
        	}else{
        		paymentMethod.setPaymentType("OTHER");
        	}
        	
        	
        	if(validPayment.getStatus().equalsIgnoreCase("Authorized")){
        		paymentDetails.setChargeType("AUTHORIZATION");
            	paymentDetails.setProcessedAmount(validPayment.getAmountRequested().toString());
            	paymentMethod.getPaymentDetails().add(paymentDetails);
        	}else if(validPayment.getStatus().equalsIgnoreCase("Collected")){
        		paymentDetails.setChargeType("CHARGE");
            	paymentDetails.setProcessedAmount(validPayment.getAmountCollected().toString());
            	paymentMethod.getPaymentDetails().add(paymentDetails);
        	}
        	
        	paymentMethodList.add(paymentMethod);
        }
        sterlingOrder.setPaymentMethods(paymentMethods);
    }

    protected KitLines createKitLinesFromProductOptions(Product product, List<ProductOption> productOptions, ApiContext apiContext) throws Exception {
	KitLines kitLines = new KitLines();
	List<KitLine> kitLineList = kitLines.getKitLine();

	if (productOptions != null && !productOptions.isEmpty()) {
		for (ProductOption productOption : productOptions) {
			KitLine kitLine = new KitLine();
			kitLine.setItemShortDesc(productOption.getName());

			if (productOption.getDataType() == null) {
				// This is an extra product that is paired with this product.
				if (productOption.getShopperEnteredValue() == null) {
					String extraProduct = productOption.getValue().toString();
					kitLine.setItemID(extraProduct);
					kitLine.setProductClass(isVariantProduct(extraProduct, apiContext) ? "TRUE" : "FALSE");
				} else {
					kitLine.setItemID(productOption.getShopperEnteredValue().toString());
				}
			} else {
				// This would be ideal if datatype is dependable
				switch (productOption.getDataType().toLowerCase()) {
					case "product":
						String extraProduct = productOption.getValue().toString();
						kitLine.setItemID(extraProduct);
						kitLine.setProductClass(isVariantProduct(extraProduct, apiContext) ? "TRUE" : "FALSE");
						break;
					case "text":
						kitLine.setItemID(productOption.getShopperEnteredValue().toString());
						break;
					case "number":
						kitLine.setItemID(String.valueOf(productOption.getShopperEnteredValue()));
						break;
				}
			}

			kitLineList.add(kitLine);
		}

		// If there are kitlines, then the product itself is a kitline
		if (!kitLineList.isEmpty()) {
			KitLine kitLine = new KitLine();
			kitLine.setItemShortDesc(product.getName());
			kitLine.setItemID(product.getProductCode());
			kitLine.setProductClass(isVariantProduct(product.getProductCode(), apiContext) ? "TRUE" : "FALSE");

			kitLineList.add(0, kitLine);
		}
	}

	return kitLines;
    }

    protected boolean isVariantProduct(String productCode, ApiContext apiContext) throws Exception{
	ProductResource productResource = new ProductResource(apiContext);
	com.mozu.api.contracts.productadmin.Product adminProduct = productResource.getProduct(productCode, "isVariation");

	boolean isVariation = false;

	if (adminProduct != null) {
		isVariation = Boolean.TRUE.equals(adminProduct.getIsValidForProductType());
	}

	return isVariation;
    }
}

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
import com.mozu.sterling.model.order.LineOverallTotals;
import com.mozu.sterling.model.order.LinePriceInfo;
import com.mozu.sterling.model.order.Note;
import com.mozu.sterling.model.order.OrderLine;
import com.mozu.sterling.model.order.OverallTotals;
import com.mozu.sterling.model.order.PaymentMethod;
import com.mozu.sterling.model.order.PersonInfoShipTo;
import com.mozu.sterling.model.order.RemainingFinancialTotals;
import com.mozu.sterling.model.shipment.Shipment;
import com.mozu.sterling.model.shipment.ShipmentLine;

@Component
public class SterlingShipmentToMozuMapper {
    private static final Logger logger = LoggerFactory.getLogger(SterlingShipmentToMozuMapper.class);
    public static final String ANONYMOUS_EMAIL = "anonymous@sterling.com";
    public static final String ANONYMOUS_FIRST_NAME = "Anony";
    public static final String ANONYMOUS_LAST_NAME = "Mous";

    public static final String FULFILLED_STATUS = "Fulfilled";
    public static final String NOT_FULFILLED_STATUS = "NotFulfilled";
    public static final String PACKAGE_STATUS = "Pending";
       /**
     * Map a Sterling order to a Mozu order
     * 
     * @param sterlingOrder
     *            Sterling order
     * @param apiContext
     * @throws Exception
     */
    public Order shipmentToFulFillment(Shipment sterlingShipment, Order order, ApiContext apiContext, Setting setting)
            throws Exception {
    	
    	
    	order.setFulfillmentStatus(FULFILLED_STATUS);
    	order.setStatus("Processing");
    	
    	createFulfilledPackages(order, sterlingShipment, setting);
		return order;

   
    }

 

    private void createFulfilledPackages(Order order, Shipment sterlingShipment, Setting setting) throws Exception {
	createPackages(order,sterlingShipment, FULFILLED_STATUS, FULFILLED_STATUS,  setting);
    }

   
    private void createPackages(Order order,Shipment sterlingShipment, String packageStatus, String productStatus, Setting setting) throws Exception {
	// Items set to Pickup go into pickups
        List<Pickup> pickups = new ArrayList<>();
     // Items set to Ship go into packages
        List<Package> packages = new ArrayList<Package>();

	for (ShipmentLine  shipLine : sterlingShipment.getShipmentLines().getShipmentLine()) {
		// setup pickup or package
	        if (StringUtils.isBlank(sterlingShipment.getShipNode())) {
			throw new RuntimeException (String.format("The order %s does not have a set fulfillment location"
					+ " which is required for a package.", order.getExternalId()));
	        }
	        if (sterlingShipment.getDeliveryMethod().equals("PICK")) {
	          /*  Pickup pickup;
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
	             orderItem.getProduct().setFulfillmentStatus(packageStatus);*/
	        } else if (sterlingShipment.getDeliveryMethod().equals("SHP")){
	        	Package pkg = new Package();
	            
	            List<PackageItem> packageItems = new ArrayList<>();
	            
	            PackageItem pItem = new PackageItem();

	            if (shipLine.getItemID() != null) {
	               // String itemProductCode = ProductMappingUtils
	                //.getActualProductCode(shipLine.getItemID());
	                pItem.setProductCode(shipLine.getItemID());
	            }
	            pItem.setQuantity(Double.valueOf(shipLine.getQuantity()).intValue());
	            pItem.setLineId(Integer.parseInt(shipLine.getShipmentLineNo()));
	            packageItems.add(pItem);
	         
	            pkg.setItems(packageItems);
	            pkg.setTrackingNumber(sterlingShipment.getTrackingNo());
	            pkg.setStatus(packageStatus);
	            pkg.setFulfillmentDate(convertFromSterlingTime(sterlingShipment.getActualShipmentDate()));
	            pkg.setFulfillmentLocationCode(getfulfillmentlocation(sterlingShipment.getShipNode(), setting));
	            
	            packages.add(pkg);
	            order.setPackages(packages);
	            order.getItems().get(0).getProduct().setFulfillmentStatus(productStatus);
	           
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

	private String getfulfillmentlocation(String shipNode, Setting setting ) throws Exception{
   	    String fulfillmentLocation = null;
        for (Entry<String, String> locationMap : setting.getLocationMap().entrySet()) {
        	if(locationMap.getValue().equalsIgnoreCase(shipNode)){
        		fulfillmentLocation= locationMap.getKey();
        	    break;
        	}
        }
        return fulfillmentLocation;
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

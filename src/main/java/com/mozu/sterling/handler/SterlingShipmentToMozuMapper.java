package com.mozu.sterling.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.mozu.api.ApiContext;
import com.mozu.api.contracts.commerceruntime.fulfillment.Package;
import com.mozu.api.contracts.commerceruntime.fulfillment.PackageItem;
import com.mozu.api.contracts.commerceruntime.fulfillment.Pickup;
import com.mozu.api.contracts.commerceruntime.fulfillment.PickupItem;
import com.mozu.api.contracts.commerceruntime.orders.Order;
import com.mozu.api.contracts.commerceruntime.orders.OrderItem;
import com.mozu.api.contracts.productruntime.Product;
import com.mozu.sterling.model.Setting;
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
    public Order mapSterlingShipmentToMozuFulFillment(Shipment sterlingShipment,Order order, ApiContext apiContext, Setting setting)
            throws Exception {
    	order.setFulfillmentStatus(FULFILLED_STATUS);
    	order.setStatus("Processing");
    	createPackages(order,sterlingShipment, FULFILLED_STATUS, setting, apiContext);
		return order;
   }
   
    private void createPackages(Order order,Shipment sterlingShipment, String packageStatus, Setting setting,ApiContext apiContext) throws Exception {
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
	       // boolean isVariant = (shipLine.getOrderLine().get(0).getItem().getPrimaryInformation() != null && "Y".equals(shipLine.getOrderLine().get(0).getItem().getPrimaryInformation().getIsModelItem()));
	       // com.mozu.api.contracts.commerceruntime.products.Product lineProduct = getProduct(apiContext,
	        		//shipLine.getItemID(), isVariant);
	        Integer mozuLineId = null;
	       // If the shipline is a bundle parent, ignore the record because in Mozu fulfillment is done against bundle component
			if(CollectionUtils.isNotEmpty(shipLine.getOrderLine())&& shipLine.getOrderLine().get(0).getIsBundleParent().equalsIgnoreCase("Y")){
	        	continue;
	        }
			// Check if the shipline is a bundle component. If yes get the line id of the bundle parent 
			else if (CollectionUtils.isNotEmpty(shipLine.getOrderLine())&& shipLine.getOrderLine().get(0).getBundleParentLine()!=null){
				//Get the bundle parent
	        	ShipmentLine shipmentLine = getSterlingShipLine(shipLine.getOrderLine().get(0).getBundleParentLine().getOrderLineKey(), sterlingShipment.getShipmentLines().getShipmentLine());
	        	//Check if the bundleparent Item exists in Mozu. if yes get the Line Id of the parent.
	        	if(shipmentLine!=null){
	        		mozuLineId = getMozuOrderItemLineId(shipmentLine.getItemID(), order);
	        	}
	        }//else if(isVariant){
	        	//mozuLineId = getMozuOrderItemLineId(lineProduct.getProductCode(), order);
	       // }
			else{
	        	mozuLineId = getMozuOrderItemLineId(shipLine.getItemID(), order);
	        }
			
			if(mozuLineId!=null){
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
		        	
		        	Package pkg= createPackage(sterlingShipment, shipLine, Double.valueOf(shipLine.getQuantity()).intValue(), 
		        			mozuLineId, convertFromSterlingTime(sterlingShipment.getActualShipmentDate()), getfulfillmentlocation(sterlingShipment.getShipNode(), setting));
		            packages.add(pkg);
		            order.setPackages(packages);
		            order.getItems().get(0).getProduct().setFulfillmentStatus(FULFILLED_STATUS);
		           
		        }
			}else{
	                logger.debug(String.format(
		                        "Mozu Order Item with productCode %s cannot be found on Order %d for the site %d.  Skipping fulfillment.",
		                        shipLine.getItemID(), order.getOrderNumber(), apiContext.getSiteId()));
		        
			}
	      
	}
    }
    
    private com.mozu.api.contracts.commerceruntime.products.Product getProduct(ApiContext apiContext,
            String productCode, boolean isVariant) throws Exception {
        com.mozu.api.contracts.commerceruntime.products.Product commerceRuntimeProduct = null;
        try {

        	Product product = ProductMappingUtils.getProductFromMozu(apiContext, productCode, isVariant);
            if (product == null) {
                throw new RuntimeException(String.format(
                        "The product with code %s cannot be found for the site %d.  Skipping import of order.",
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
 
    private ShipmentLine getSterlingShipLine(String orderLineKey, List<ShipmentLine> sterlingShipLines){
    	ShipmentLine sterlingShipLine=null;
    	for (ShipmentLine shipmentLine : sterlingShipLines) {
			if(orderLineKey.equals(shipmentLine.getOrderLineKey())){
				sterlingShipLine = shipmentLine;
				break;
			}
		}
    	return sterlingShipLine;
    }
 
    private Integer getMozuOrderItemLineId(String sterlingOrderItemId, Order mozuOrder){
    	OrderItem mozuOrderItem=null;
    	Integer lineId = null;
    	for (OrderItem orderItem : mozuOrder.getItems()) {
    		if(sterlingOrderItemId.equalsIgnoreCase(orderItem.getProduct().getProductCode())){
    			mozuOrderItem=orderItem;
    			break;
    		}
    	}
    	if(mozuOrderItem==null){
    		for (OrderItem orderItem : mozuOrder.getItems()) {
    			if(orderItem.getProduct().getVariationProductCode() !=null && sterlingOrderItemId.equalsIgnoreCase(orderItem.getProduct().getVariationProductCode())){
        			mozuOrderItem=orderItem;
        			break;
        		}
    		}
    	}
    	if(mozuOrderItem!=null){
    		lineId= mozuOrderItem.getLineId();
    	}
		return lineId;
    	
    }
	private static Package createPackage(Shipment sterlingShipment,ShipmentLine shipLine,
		Integer unitQuantity, int lineId,DateTime fulfillmentTime, String storeLocation) {
		
		Package pkg = new Package();
		
        pkg.setTrackingNumber(sterlingShipment.getTrackingNo());
        pkg.setStatus(FULFILLED_STATUS);
        pkg.setFulfillmentDate(fulfillmentTime);
        pkg.setFulfillmentLocationCode(storeLocation);
        
       
        PackageItem pItem = new PackageItem();

        if (shipLine.getItemID() != null) {
           // String itemProductCode = ProductMappingUtils
            //.getActualProductCode(shipLine.getItemID());
            pItem.setProductCode(shipLine.getItemID());
        }
        pItem.setQuantity(unitQuantity);
        pItem.setLineId(lineId);
        List<PackageItem> packageItems = new ArrayList<>();
        packageItems.add(pItem);
        pkg.setItems(packageItems);
        return pkg;
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

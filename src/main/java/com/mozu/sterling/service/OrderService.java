package com.mozu.sterling.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.mozu.api.ApiContext;
import com.mozu.api.contracts.commerceruntime.fulfillment.FulfillmentAction;
import com.mozu.api.contracts.commerceruntime.fulfillment.PackageItem;
import com.mozu.api.contracts.commerceruntime.fulfillment.PickupItem;
import com.mozu.api.contracts.commerceruntime.orders.Order;
import com.mozu.api.contracts.commerceruntime.orders.OrderAction;
import com.mozu.api.contracts.commerceruntime.orders.OrderCollection;
import com.mozu.api.contracts.event.Event;
import com.mozu.api.resources.commerce.OrderResource;
import com.mozu.api.resources.commerce.orders.FulfillmentActionResource;
import com.mozu.api.resources.commerce.orders.PackageResource;
import com.mozu.api.resources.commerce.orders.PaymentResource;
import com.mozu.api.resources.commerce.orders.PickupResource;
import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.handler.MozuOrderToSterlingMapper;
import com.mozu.sterling.handler.SterlingOrderToMozuMapper;
import com.mozu.sterling.handler.SterlingShipmentToMozuMapper;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.order.OrderList;
import com.mozu.sterling.model.shipment.Shipment;
import com.mozu.sterling.model.changeorderstatus.OrderLines;
import com.mozu.sterling.model.changeorderstatus.OrderStatusChange;
import com.mozu.api.contracts.commerceruntime.orders.OrderItem;
import com.mozu.api.contracts.commerceruntime.payments.Payment;
import com.mozu.api.contracts.commerceruntime.payments.PaymentAction;


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
    public final static String GET_ORDER_LIST_SERVICE_NAME = "getOrderList";
    public final static String GET_ORDER_SERVICE_NAME = "getOrderDetails";
    public final static String UPDATE_ORDER_SERVICE_NAME = "changeOrder";
    public final static String TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public final static String ORDER_DATE_QRY = "BETWEEN";
    public final static String CHANGE_ORDER_STATUS_SERVICE_NAME = "changeOrderStatus ";
    public static final String CANCEL_ORDER_ACTION = "CancelOrder";
    public static final String VOID_PAYMENT_ACTION = "VoidPayment";
    public static final String CAPTURE_PAYMENT_ACTION = "CapturePayment";
    public static final String FULFILLED_STATUS = "Fulfilled";
    public static final String NOT_FULFILLED_STATUS = "NotFulfilled";
    
    @Autowired
    SterlingClient sterlingClient;

    @Autowired
    ConfigHandler configHandler;

    @Autowired
    MozuOrderToSterlingMapper mozuOrderToSterlingMapper;

    @Autowired
    SterlingOrderToMozuMapper sterlingOrderToMozuMapper;
    
    @Autowired
    SterlingShipmentToMozuMapper sterlingShipmentToMozuMapper;
    
    public OrderService() throws Exception {
        super();
    }

    public List<com.mozu.sterling.model.order.Order> getSterlingOrders(Setting setting) throws Exception {
        return getSterlingOrders(setting, null);
    }
    public List<com.mozu.sterling.model.order.Order> getSterlingOrders(Setting setting, Long orderDate) throws Exception {
        OrderList orderList = null;
        if (StringUtils.isNotBlank(setting.getSterlingUrl())) {
            com.mozu.sterling.model.order.Order inOrder = new com.mozu.sterling.model.order.Order();
            if (StringUtils.isNotBlank(setting.getSterlingEnterpriseCode())) {
                inOrder.setEnterpriseCode(setting.getSterlingEnterpriseCode());
            }
            
            if (orderDate != null && orderDate > 0L) {
                DateTime orderDateTime = new DateTime (orderDate);
                inOrder.setFromOrderDate(orderDateTime.toString(TIME_FORMAT));
                inOrder.setToOrderDate(new DateTime().toString(TIME_FORMAT));
                inOrder.setOrderDateQryType(ORDER_DATE_QRY);
            }

            Document inDoc = convertObjectToXml(inOrder, com.mozu.sterling.model.order.Order.class);
            Document outDoc = null;
            try {
                outDoc = this.invoke(GET_ORDER_LIST_SERVICE_NAME, inDoc, setting);
                orderList = (OrderList) convertXmlToObject(outDoc, OrderList.class);
            } catch (Exception e) {
                logger.warn("Unable to get order list from Sterling: " + e.getMessage());
            }

        } else {
            logger.warn("Cannot get Sterling ship nodes because the settings aren't set.");
        }
        return orderList != null ? orderList.getOrder() : new ArrayList<com.mozu.sterling.model.order.Order>();
    }

    /**
     * Create an order in Sterling based on a Mozu event.
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
        	 logger.debug(String.format("Correlation ID: %s. Unable to create Order with id %s in Sterling: %s",
                    event.getCorrelationId(), event.getEntityId(), e.getMessage()));
            throw e;
        }
    }

    /**
     * Converts a Mozu order to a Sterling order and sends to the Sterling OMS
     * 
     * @param apiContext
     * @param mozuOrder
     *            mozu order to send to Sterling
     * @return
     */
    public boolean createOrder(ApiContext apiContext, Order mozuOrder) throws Exception {
        Setting setting = configHandler.getSetting(apiContext.getTenantId());
        com.mozu.sterling.model.order.Order sterlingOrder = mozuOrderToSterlingMapper.mapMozuOrderToSterling(mozuOrder, null,
                setting, apiContext);

        Document inDoc = this.convertObjectToXml(sterlingOrder, com.mozu.sterling.model.order.Order.class);

        Document outDoc = sterlingClient.invoke(ORDER_SERVICE_NAME, inDoc, setting);

        com.mozu.sterling.model.order.Order createdSterlingOrder= (com.mozu.sterling.model.order.Order) convertXmlToObject(outDoc, com.mozu.sterling.model.order.Order.class);
        return outDoc != null;
    }
    
    /**
     * Update an order in Sterling based on a Mozu event.
     * 
     * @param apiContext
     *            the api context
     * @param event
     *            the Mozu event.
     * @return true if the order was updated in Sterling w/o errors.
     */
    public boolean updateSterlingOrder(ApiContext apiContext, Event event) throws Exception {
        OrderResource orderResource = new OrderResource(apiContext);
        boolean status=false;
        Setting setting = configHandler.getSetting(apiContext.getTenantId());
        try {
            Order mozuOrder = orderResource.getOrder(event.getEntityId());
            com.mozu.sterling.model.order.Order sterlingOrder = getSterlingOrderDetail(setting, mozuOrder.getOrderNumber().toString());
            if(sterlingOrder !=null){
		status= updateSterlingOrder(setting, mozuOrder, sterlingOrder, apiContext);
            }
        } catch (Exception e) {
            logger.debug(String.format("Correlation ID: %s. Unable to get Order with id %s from Mozu: %s",
                    event.getCorrelationId(), event.getEntityId(), e.getMessage()));
            throw e;
        }
		return status;
    }
    
    /**
     * Converts a Mozu order to a Sterling order and sends to the Sterling OMS to update existing order
     * 
     * @param apiContext
     * @param mozuOrder
     *            mozu order to send to Sterling
     * @return
     */
    public boolean updateSterlingOrder(Setting  setting, Order mozuOrder,  com.mozu.sterling.model.order.Order existingSterlingOrder, ApiContext apiContext) throws Exception {

        com.mozu.sterling.model.order.Order sterlingOrder = mozuOrderToSterlingMapper.mapMozuOrderToSterling(mozuOrder, existingSterlingOrder,
                setting, apiContext);

        Document inDoc = this.convertObjectToXml(sterlingOrder, com.mozu.sterling.model.order.Order.class);

        Document outDoc = sterlingClient.invoke(UPDATE_ORDER_SERVICE_NAME, inDoc, setting);

        return outDoc != null;
    }
    
    /**
     * Cancel an order in Sterling based on a Mozu event.
     * 
     * @param apiContext
     *            the api context
     * @param event
     *            the Mozu event.
     * @return true if the order was updated in Sterling w/o errors.
     */
    public boolean cancelSterlingOrder(ApiContext apiContext, Event event) throws Exception {
        OrderResource orderResource = new OrderResource(apiContext);
        boolean status=false;
        Setting setting = configHandler.getSetting(apiContext.getTenantId());
        try {
            Order mozuOrder = orderResource.getOrder(event.getEntityId());
            com.mozu.sterling.model.order.Order sterlingOrder = getSterlingOrderDetail(setting, mozuOrder.getOrderNumber().toString());
            if(sterlingOrder !=null){
            	status= cancelSterlingOrder(setting, mozuOrder, sterlingOrder);
            }
        } catch (Exception e) {
            logger.debug(String.format("Correlation ID: %s. Unable to get Order with id %s from Mozu: %s",
                    event.getCorrelationId(), event.getEntityId(), e.getMessage()));
            throw e;
        }
		return status;
    }
    
    /**
     * Set the action of Sterling order to "CANCEL" and send it to the Sterling OMS to cancel existing order
     * 
     * @param setting
     * @param mozuOrder
     * @param existingSterlingOrder
     * @return
     */
    public boolean cancelSterlingOrder(Setting  setting, Order mozuOrder,  com.mozu.sterling.model.order.Order existingSterlingOrder) throws Exception {
    	com.mozu.sterling.model.order.Order	sterlingOrder = new com.mozu.sterling.model.order.Order();
    	sterlingOrder.setOrderHeaderKey(existingSterlingOrder.getOrderHeaderKey());
    	sterlingOrder.setAction("CANCEL");
        Document inDoc = this.convertObjectToXml(sterlingOrder, com.mozu.sterling.model.order.Order.class);

        Document outDoc = sterlingClient.invoke(UPDATE_ORDER_SERVICE_NAME, inDoc, setting);

        return outDoc != null;
    }
    
    /**
     * Complete an order in Sterling based on a Mozu event.
     * 
     * @param apiContext
     *            the api context
     * @param event
     *            the Mozu event.
     * @return true if the order was updated in Sterling w/o errors.
     */
    public boolean completeSterlingOrder(ApiContext apiContext, Event event) throws Exception {
        OrderResource orderResource = new OrderResource(apiContext);
        boolean status=false;
        Setting setting = configHandler.getSetting(apiContext.getTenantId());
        try {
            Order mozuOrder = orderResource.getOrder(event.getEntityId());
            com.mozu.sterling.model.order.Order sterlingOrder = getSterlingOrderDetail(setting, mozuOrder.getOrderNumber().toString());
            if(sterlingOrder !=null){
	            for(OrderItem orderItem : mozuOrder.getItems()){
	            	if(orderItem.getFulfillmentMethod().equalsIgnoreCase("Digital")){
	            		status= updateSterlingOrderStatus(setting, mozuOrder, sterlingOrder,orderItem);
	            	}
	            }
            }
           
           
        } catch (Exception e) {
            logger.debug(String.format("Correlation ID: %s. Unable to get Order with id %s from Mozu: %s",
                    event.getCorrelationId(), event.getEntityId(), e.getMessage()));
            throw e;
        }
		return status;
    }
    
    /**
     * Converts a Mozu order to a Sterling order and sends to the Sterling OMS to update existing order
     * 
     * @param apiContext
     * @param mozuOrder
     *            mozu order to send to Sterling
     * @return
     */
    public boolean updateSterlingOrderStatus(Setting  setting, Order mozuOrder,  com.mozu.sterling.model.order.Order existingSterlingOrder,OrderItem orderItem) throws Exception {
     	OrderStatusChange orderStatusChange =  getOrderStatusChange(setting, existingSterlingOrder, orderItem);
        Document inDoc = this.convertObjectToXml(orderStatusChange, com.mozu.sterling.model.changeorderstatus.OrderStatusChange.class);

        Document outDoc = sterlingClient.invoke(CHANGE_ORDER_STATUS_SERVICE_NAME, inDoc, setting);

        return outDoc != null;
    }
    
    private OrderStatusChange getOrderStatusChange(Setting  setting,com.mozu.sterling.model.order.Order existingSterlingOrder,OrderItem orderItem){
    	OrderStatusChange orderStatusChange=new OrderStatusChange();
    	orderStatusChange.setEnterpriseCode(setting.getSterlingEnterpriseCode());
    	orderStatusChange.setOrderHeaderKey(existingSterlingOrder.getOrderHeaderKey());
    	orderStatusChange.setTransactionId("DELIVER_ORDER");
    	orderStatusChange.setChangeForAllAvailableQty("Y");
    	orderStatusChange.setBaseDropStatus("3700.7777");
    	OrderLines orderLines=new OrderLines();
    	List< com.mozu.sterling.model.changeorderstatus.OrderLine> orderLineList=orderLines.getOrderLine();
    	com.mozu.sterling.model.changeorderstatus.OrderLine orderLine=new  com.mozu.sterling.model.changeorderstatus.OrderLine();
    	orderLine.setPrimeLineNo(orderItem.getLineId().toString());
    	orderLine.setBaseDropStatus("3700.7777");
    	orderLineList.add(orderLine);
    	orderStatusChange.setOrderLines(orderLines);
		return orderStatusChange;
    }

    /**
     * Update orders in Mozu from a Sterling order.
     * 
     * @param sterlingOrder
     * @return
     */
    public boolean updateOrder(com.mozu.sterling.model.order.Order sterlingOrder) {
        // stub for updating status of orders from Sterling.

        return true;
    }

    public com.mozu.sterling.model.order.Order getSterlingOrderDetail(Setting setting, String orderNo) throws Exception {
        com.mozu.sterling.model.order.Order sterlingOrder = null;
        if (StringUtils.isNotBlank(setting.getSterlingUrl())) {
            com.mozu.sterling.model.order.Order inOrder = new com.mozu.sterling.model.order.Order();
            if (StringUtils.isNotBlank(setting.getSterlingEnterpriseCode())) {
                inOrder.setEnterpriseCode(setting.getSterlingEnterpriseCode());
            }

            inOrder.setOrderNo(orderNo);
            Document inDoc = convertObjectToXml(inOrder, com.mozu.sterling.model.order.Order.class);
            Document outDoc = null;
            try {
                outDoc = this.invoke(GET_ORDER_SERVICE_NAME, inDoc, setting);
                sterlingOrder = (com.mozu.sterling.model.order.Order) convertXmlToObject(outDoc, com.mozu.sterling.model.order.Order.class);
            } catch (Exception e) {
                logger.warn("Unable to get order list from Sterling: " + e.getMessage());
            }

        } else {
            logger.warn("Cannot get Sterling ship nodes because the settings aren't set.");
        }
        return sterlingOrder;

    }
   
    /**
     * Import in the order from Sterling with the given Order No.  Settings for the tenant in the ApiContext will be loaded automatically.
     * @param apiContext
     * @param orderNo
     * @return
     * @throws Exception
     */
    public Order importSterlingOrder (ApiContext apiContext, String orderNo) throws Exception {
        Setting setting = configHandler.getSetting(apiContext.getTenantId());
        
        return importSterlingOrder(apiContext, setting, orderNo);
    }
    
    /**
     * Import in the order with the give orderNo ID.
     * @param apiContext the Mozu API context with tenant and site ID
     * @param setting the settings for the tenant.
     * @param orderNo the order number of the order in Sterling.
     * @return the imported mozu order.
     * @throws Exception
     */
    public Order importSterlingOrder (ApiContext apiContext, Setting setting, String orderNo) throws Exception {
        com.mozu.sterling.model.order.Order sterlingOrder = getSterlingOrderDetail(setting, orderNo);
        return importSterlingOrder (apiContext, setting, sterlingOrder);
    }
    
    
    public Order importSterlingOrder (ApiContext apiContext, Setting setting, com.mozu.sterling.model.order.Order sterlingOrder) throws Exception {
        Order mozuOrder = null;
        if (sterlingOrder != null) {
            
            mozuOrder = sterlingOrderToMozuMapper.saleToOrder(sterlingOrder, apiContext, setting);
            OrderResource orderResource = new OrderResource (apiContext);
            OrderCollection existingOrders = orderResource.getOrders(0, null, null,
                    "externalId eq " + sterlingOrder.getOrderNo(), null, null, null);
            if (existingOrders != null && existingOrders.getItems() != null &&  
                    existingOrders.getItems().size() > 0) {
                Order existingOrder = existingOrders.getItems().get(0);
                if(sterlingOrder.getStatus().equalsIgnoreCase("Cancelled")){
            		cancelMozuOrder(apiContext, existingOrder);
            	}else{
                    mozuOrder = orderResource.updateOrder(mozuOrder, existingOrder.getId());
            	}
            }else {
                mozuOrder = orderResource.createOrder(mozuOrder);
            }
        } else {
            logger.info ("Order cannot be null.");
        }
        return mozuOrder;
    }
    
  
    
    public Order importSterlingShipment (ApiContext apiContext, Setting setting, Shipment sterlingShipment) throws Exception {
        Order mozuOrder = null;
        if (sterlingShipment != null) {
        	OrderResource orderResource = new OrderResource (apiContext);
            OrderCollection existingOrders = orderResource.getOrders(0, null, null,
                    "orderNumber eq " + sterlingShipment.getShipmentLines().getShipmentLine().get(0).getOrderNo(), null, null, null);
            if (existingOrders != null && existingOrders.getItems() != null &&  
                    existingOrders.getItems().size() > 0) {
                Order existingOrder = existingOrders.getItems().get(0);
                mozuOrder = sterlingShipmentToMozuMapper.mapSterlingShipmentToMozuFulFillment(sterlingShipment,existingOrder, apiContext, setting);
                if(mozuOrder.getPackages().size()>0){
                	shipOrder(mozuOrder, apiContext);
                }else if(mozuOrder.getPickups().size()>0){
                	pickOrder(mozuOrder, apiContext);
                }
                if(mozuOrder.getFulfillmentStatus().equals("Fulfilled")){
                	completeOrder(mozuOrder, apiContext);
                }
            }
               
        } else {
            logger.info ("Order cannot be null.");
        }
        return mozuOrder;
    }
    
    private void completeOrder(Order mozuOrder, ApiContext apiContext) throws Exception{
    	PaymentResource paymentResource = new PaymentResource(apiContext);
    	for (Payment payment : mozuOrder.getPayments()) {
    		if (StringUtils.equalsIgnoreCase(payment.getStatus(), "voided"))
                continue;
    		List<String> availabePaymentActions = paymentResource.getAvailablePaymentActions(mozuOrder.getId(), payment.getId());
    		if(availabePaymentActions.contains(CAPTURE_PAYMENT_ACTION)){
        		PaymentAction paymentAction = new PaymentAction();
                paymentAction.setActionName(CAPTURE_PAYMENT_ACTION);
                try {
                	logger.debug("Perform CapturePayment  action for order number "+ mozuOrder.getOrderNumber()+" on tenanat "+apiContext.getTenantId());
					paymentResource.performPaymentAction(paymentAction,
							mozuOrder.getId(), payment.getId());
				} catch (Exception e) {
					logger.warn("Exception while capturing payment for Order no "+mozuOrder.getOrderNumber()+" on tenanat "+apiContext.getTenantId());
					throw e;
				}
    		}
 		}
    }
    
    private void shipOrder(Order order, ApiContext context) throws Exception {
        PackageResource packageResource = new PackageResource(context);
        FulfillmentActionResource fulFillmentResource = new FulfillmentActionResource(context);
        FulfillmentAction action = new FulfillmentAction();
        action.setPackageIds(new ArrayList<String>());

        for (com.mozu.api.contracts.commerceruntime.fulfillment.Package pkg : order.getPackages()) {
            List<PackageItem> packageItems = new ArrayList<PackageItem>();
            packageItems = pkg.getItems();
            if (packageItems.size() > 0) {
               com.mozu.api.contracts.commerceruntime.fulfillment.Package pkg1 = packageResource
                        .createPackage(pkg, order.getId());

                if (StringUtils.equals(pkg.getStatus(), FULFILLED_STATUS)) {
                    action.getPackageIds().add(pkg1.getId());
                }
            } 
        }
        if (action.getPackageIds().size() > 0) {
            action.setActionName("Ship");
            fulFillmentResource.performFulfillmentAction(action, order.getId());
        }

    }
    
    private void pickOrder(Order order, ApiContext context) throws Exception {
        PickupResource pickupResource = new PickupResource(context);
        FulfillmentActionResource fulFillmentResource = new FulfillmentActionResource(context);
        FulfillmentAction action = new FulfillmentAction();
        action.setPickupIds(new ArrayList<String>());

        for (com.mozu.api.contracts.commerceruntime.fulfillment.Pickup pickup : order.getPickups()) {
            List<PickupItem> pickupItems = new ArrayList<PickupItem>();
            pickupItems = pickup.getItems();
            if (pickupItems.size() > 0) {
               com.mozu.api.contracts.commerceruntime.fulfillment.Pickup pickup1 = pickupResource
                        .createPickup(pickup, order.getId());

                if (StringUtils.equals(pickup.getStatus(), FULFILLED_STATUS)) {
                    action.getPickupIds().add(pickup1.getId());
                }
            } 
        }
        if (action.getPickupIds().size() > 0) {
            action.setActionName("PickUp");
            fulFillmentResource.performFulfillmentAction(action, order.getId());
        }

    }
    
    
    private void cancelMozuOrder(ApiContext apiContext, Order mozuOrder) throws Exception{
    	PaymentResource paymentResource = new PaymentResource(apiContext);
    	OrderResource orderResource = new OrderResource(apiContext);
    	if(mozuOrder.getPayments()!=null){
        	for (Payment payment : mozuOrder.getPayments()) {
        		if (StringUtils.equalsIgnoreCase(payment.getStatus(), "voided"))
                    continue;
        		List<String> availabePaymentActions = paymentResource.getAvailablePaymentActions(mozuOrder.getId(), payment.getId());
        		if(availabePaymentActions.contains(VOID_PAYMENT_ACTION)){
            		PaymentAction paymentAction = new PaymentAction();
                    paymentAction.setActionName(VOID_PAYMENT_ACTION);
                    paymentResource.performPaymentAction(paymentAction,
                    		mozuOrder.getId(), payment.getId());
        		}
			}
    	}
    	List<String> availabeActions = orderResource.getAvailableActions(mozuOrder.getId());
    	if(availabeActions.contains(CANCEL_ORDER_ACTION)){
            logger.debug("Perform Cancel Order action for order number "+ mozuOrder.getOrderNumber()+" on tenanat "+apiContext.getTenantId());
            OrderAction orderAction = new OrderAction();
            orderAction.setActionName(CANCEL_ORDER_ACTION);
            orderResource.performOrderAction(orderAction, mozuOrder.getId());
            logger.debug("Successfully performed Cancel Order action on order number "+ mozuOrder.getOrderNumber()+" on tenanat "+apiContext.getTenantId());
    	}
    }
    
    

    public Shipment getSterlingShipmentDetail(Setting setting, String shipmentKey, String shipmentNo ) throws Exception {
    	Shipment sterlingShipment = null;
        if (StringUtils.isNotBlank(setting.getSterlingUrl())) {
        	com.mozu.sterling.model.shipmentInput.Shipment inShipment = new com.mozu.sterling.model.shipmentInput.Shipment();
            if (StringUtils.isNotBlank(setting.getSterlingEnterpriseCode())) {
            	inShipment.setSellerOrganizationCode("Aurora");
            }

            inShipment.setShipmentKey(shipmentKey);
            inShipment.setShipmentNo(shipmentNo);
            Document inDoc = convertObjectToXml(inShipment, com.mozu.sterling.model.shipmentInput.Shipment.class);
            Document outDoc = null;
            try {
                outDoc = this.invoke("getShipmentDetails", inDoc, setting);
                sterlingShipment = (Shipment) convertXmlToObject(outDoc, Shipment.class);
            } catch (Exception e) {
                logger.warn("Unable to get order list from Sterling: " + e.getMessage());
            }

        } else {
            logger.warn("Cannot get Sterling ship nodes because the settings aren't set.");
        }
        return sterlingShipment;

    }
   

}

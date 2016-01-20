package com.mozu.sterling.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.mozu.api.ApiContext;
import com.mozu.api.contracts.commerceruntime.orders.Order;
import com.mozu.api.contracts.commerceruntime.orders.OrderCollection;
import com.mozu.api.contracts.event.Event;
import com.mozu.api.resources.commerce.OrderResource;
import com.mozu.sterling.handler.ConfigHandler;
import com.mozu.sterling.handler.MozuOrderToSterlingMapper;
import com.mozu.sterling.handler.SterlingOrderToMozuMapper;
import com.mozu.sterling.model.Setting;
import com.mozu.sterling.model.order.OrderList;

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

    @Autowired
    SterlingClient sterlingClient;

    @Autowired
    ConfigHandler configHandler;

    @Autowired
    MozuOrderToSterlingMapper mozuOrderToSterlingMapper;

    @Autowired
    SterlingOrderToMozuMapper sterlingOrderToMozuMapper;
    
    public OrderService() throws Exception {
        super();
    }

    public List<com.mozu.sterling.model.order.Order> getSterlingOrders(Setting setting) throws Exception {
        OrderList orderList = null;
        if (StringUtils.isNotBlank(setting.getSterlingUrl())) {
            com.mozu.sterling.model.order.Order inOrder = new com.mozu.sterling.model.order.Order();
            if (StringUtils.isNotBlank(setting.getSterlingEnterpriseCode())) {
                inOrder.setEnterpriseCode(setting.getSterlingEnterpriseCode());
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
            logger.debug(String.format("Correlation ID: %s. Unable to get Order with id %s from Mozu: %s",
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
        com.mozu.sterling.model.order.Order sterlingOrder = mozuOrderToSterlingMapper.mapMozuOrderToSterling(mozuOrder,
                setting);

        Document inDoc = this.convertObjectToXml(sterlingOrder, com.mozu.sterling.model.order.Order.class);

        Document outDoc = sterlingClient.invoke(ORDER_SERVICE_NAME, inDoc, setting);

        return outDoc != null;
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
        Order mozuOrder = null;
        com.mozu.sterling.model.order.Order sterlingOrder = getSterlingOrderDetail(setting, orderNo);
        if (sterlingOrder != null) {
        
            mozuOrder = sterlingOrderToMozuMapper.saleToOrder(sterlingOrder, apiContext, setting);
                    
            OrderResource orderResource = new OrderResource (apiContext);
            OrderCollection existingOrders = orderResource.getOrders(0, null, null,
                    "externalId eq " + orderNo, null, null, null);
            if (existingOrders != null && existingOrders.getItems() != null &&  
                    existingOrders.getItems().size() > 0) {
                Order existingOrder = existingOrders.getItems().get(0);
                mozuOrder = orderResource.updateOrder(mozuOrder, existingOrder.getId());
            }else {
                mozuOrder = orderResource.createOrder(mozuOrder);
            }
        } else {
            logger.info ("Unable to find order with the orderId: " + orderNo);
        }
        return mozuOrder;
    }

}

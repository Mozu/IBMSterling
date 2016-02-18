package com.mozu.sterling.event;

import javax.annotation.PostConstruct;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mozu.api.ApiContext;
import com.mozu.api.contracts.event.Event;
import com.mozu.api.events.EventManager;
import com.mozu.api.events.handlers.OrderEventHandler;
import com.mozu.api.events.model.EventHandlerStatus;
import com.mozu.sterling.service.OrderService;

@Service
public class OrderEventHandlerImpl implements OrderEventHandler {
    private static final Logger logger = LoggerFactory.getLogger(OrderEventHandlerImpl.class);

    @Autowired
    OrderService orderService;
    
    public OrderEventHandlerImpl() {
    }
    
    @PostConstruct
    public void initialize() {
        EventManager.getInstance().registerHandler(this);
        logger.info("Site event handler initialized");
    }

    @Override
    public EventHandlerStatus abandoned(ApiContext apiContext, Event event) {
        logger.info("Abandoned Order Event - Not Implemented");
        return new EventHandlerStatus(HttpStatus.SC_OK);
    }

    @Override
    public EventHandlerStatus cancelled(ApiContext apiContext, Event event) {
        logger.info("Cancelled Order Event");
        try {
            orderService.cancelSterlingOrder(apiContext, event);
        } catch (Exception e) {
            return new EventHandlerStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }
        return new EventHandlerStatus(HttpStatus.SC_OK);
    }

    @Override
    public EventHandlerStatus closed(ApiContext apiContext, Event event) {
        logger.info("Closed Order Event");
        try {
            orderService.completeSterlingOrder(apiContext, event);
        } catch (Exception e) {
            return new EventHandlerStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }
       return new EventHandlerStatus(HttpStatus.SC_OK);
    }

    @Override
    public EventHandlerStatus fulfilled(ApiContext apiContext, Event event) {
        logger.info("Fulfilled Order Event - Not Implemented");
        return new EventHandlerStatus(HttpStatus.SC_OK);
    }

    @Override
    public EventHandlerStatus opened(ApiContext apiContext, Event event) {
        logger.info("Opened Order Event Received");
        try {
            orderService.createOrder(apiContext, event);
        } catch (Exception e) {
            return new EventHandlerStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }
        return new EventHandlerStatus(HttpStatus.SC_OK);
    }

    @Override
    public EventHandlerStatus pendingreview(ApiContext apiContext, Event event) {
        logger.info("Pending Review Order Event - Not Implemented");
        return new EventHandlerStatus(HttpStatus.SC_OK);
    }

    @Override
    public EventHandlerStatus updated(ApiContext apiContext, Event event) {
        logger.info("Updated Order Event");
        try {
            orderService.updateSterlingOrder(apiContext, event);
        } catch (Exception e) {
            return new EventHandlerStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }
        return new EventHandlerStatus(HttpStatus.SC_OK);
    }

}

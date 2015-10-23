package com.mozu.sterling.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mozu.sterling.model.order.Order;
import com.mozu.sterling.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderUpdateController {
    private static final Logger logger = LoggerFactory.getLogger(OrderUpdateController.class);
    
    @Autowired
    private OrderService orderService;
    
    @RequestMapping(method=RequestMethod.PUT)
    public @ResponseBody void updateOrder(@RequestBody String orderStr) throws Exception 
    {
        ObjectMapper mapper = new ObjectMapper();
        logger.info("Received Update Order Call from Sterling");
        logger.info(orderStr);
        Order order = mapper.readValue(orderStr, Order.class);
        
        orderService.updateOrder(order);
    }

}

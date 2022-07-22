package com.java.springBoot.microservice.api.constroller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.java.springBoot.microservice.api.commons.Payment;
import com.java.springBoot.microservice.api.commons.TransactionRequest;
import com.java.springBoot.microservice.api.commons.TransactionResponse;
import com.java.springBoot.microservice.api.entity.Order;
import com.java.springBoot.microservice.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/bookOrder1")
    public Order bookOrder1(@RequestBody Order order) {
        System.out.println("hi this is order service saving");
        return orderService.saveOrder(order);
        //do reset call to payment and pass the order Id
    }

    @PostMapping("/bookOrder")
//    @RequestMapping(name = "/bookOrder")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest request) throws JsonProcessingException {
        return orderService.saveOrder(request);
        //do reset call to payment and pass the order Id
    }
}

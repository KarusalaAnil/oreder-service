package com.java.springBoot.microservice.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.springBoot.microservice.api.commons.Payment;
import com.java.springBoot.microservice.api.commons.TransactionRequest;
import com.java.springBoot.microservice.api.commons.TransactionResponse;
import com.java.springBoot.microservice.api.entity.Order;
import com.java.springBoot.microservice.api.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope
public class OrderService {

    @Autowired
    @Lazy
    private RestTemplate restTemplate;
    @Autowired
    private OrderRepository orderRepository;

    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String ENDPOINT_URL;

    private Logger log = LoggerFactory.getLogger(OrderService.class);

    public TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException {
        String message;
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setAmount(order.getPrice());
        payment.setOrderId(order.getId());

        log.info("Order service request {}"+new ObjectMapper().writeValueAsString(request));
        /** payment url calling*/
//        Payment paymentResponse = restTemplate.postForObject("http://localhost:9191/payment/doPayment", payment, Payment.class);
//        Payment paymentResponse = restTemplate.postForObject("http://PAYMENT-SERVICE/payment/doPayment", payment, Payment.class);
        Payment paymentResponse = restTemplate.postForObject(ENDPOINT_URL, payment, Payment.class);
        log.info("payment service response from order service rest call {}"+new ObjectMapper().writeValueAsString(paymentResponse));
        log.error("payment service response from order service rest call {}");

        message = paymentResponse.getPaymentStatus().equalsIgnoreCase("success")?"payment successfully":"payment Fail";

        orderRepository.save(order);
        return new TransactionResponse(order,paymentResponse.getTransactionId(),paymentResponse.getAmount(),message);
//        return new TransactionResponse();
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order findOrderId(int orderId) {
        return orderRepository.findById(orderId).get();
    }
}

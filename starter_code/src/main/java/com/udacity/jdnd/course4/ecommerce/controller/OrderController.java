package com.udacity.jdnd.course4.ecommerce.controller;

import com.udacity.jdnd.course4.ecommerce.entities.UserOrder;
import com.udacity.jdnd.course4.ecommerce.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/submit/{username}")
    public ResponseEntity<UserOrder> createOrderForUser(@PathVariable String username) {
        return orderService.createOrderForUser(username);
    }

    @GetMapping("/history/{username}")
    public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
        return orderService.getOrdersOfUser(username);
    }
}

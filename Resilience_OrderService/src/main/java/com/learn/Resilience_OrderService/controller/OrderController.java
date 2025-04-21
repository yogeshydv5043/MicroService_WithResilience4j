package com.learn.Resilience_OrderService.controller;


import com.learn.Resilience_OrderService.model.Order;
import com.learn.Resilience_OrderService.service.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private UserClient userClient;

    private List<Order> orders = Arrays.asList(
            new Order(1L, "Laptop", 1, 1L),
            new Order(2L, "Mouse", 2, 1L),
            new Order(3L, "Keyboard", 1, 2L),
            new Order(4L, "Monitor", 1, 3L)
    );

    @GetMapping
    public List<Order> getAllOrders() {
        orders.forEach(System.out::println);
        return orders;
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        Order order = orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        // Fetch user details using Feign client
        order.setUser(userClient.getUserById(order.getUserId()));
        return order;
    }
}
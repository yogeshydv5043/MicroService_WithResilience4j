package com.learn.Resilience_OrderService.model;

import com.learn.Resilience_OrderService.dto.User;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Order {
    private Long id;
    private String productName;
    private int quantity;
    private Long userId;
    private User user; // This will be populated via Feign client


    public Order(Long id, String productName, int quantity, Long userId) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.userId = userId;
    }
}
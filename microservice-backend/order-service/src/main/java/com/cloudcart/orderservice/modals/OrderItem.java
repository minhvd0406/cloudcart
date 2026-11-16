package com.cloudcart.orderservice.modals;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Column(name = "product_id")
    private String productId;
    private String productName;
    private double price;
    private int quantity;
    private double amount;
    private String imageUrl;
    private String categoryName;
}

package com.cloudcart.cartservice.modals;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Embeddable
public class CartItem {

    @Column(name = "product_id", nullable = false)
    private String productId;

    private int quantity;

}

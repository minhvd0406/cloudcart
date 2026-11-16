package com.cloudcart.cartservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequestDto {
    @NotBlank(message = "Product is required.")
    private String productId;
    private int quantity;
}

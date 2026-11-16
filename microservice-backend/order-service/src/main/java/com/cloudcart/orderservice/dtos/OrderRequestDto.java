package com.cloudcart.orderservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    @NotBlank(message = "First name is required.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    private String lastName;

    @NotBlank(message = "Address is required.")
    private String addressLine1;

    private String addressLine2;

    @NotBlank(message = "City is required.")
    private String city;

    @NotBlank(message = "Phone number is required.")
    private String phoneNo;

    @NotBlank(message = "Cart is required.")
    private String cartId;

}

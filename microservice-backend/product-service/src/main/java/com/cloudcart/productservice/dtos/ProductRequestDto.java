package com.cloudcart.productservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {

    @NotBlank(message = "Product name is required.")
    private String productName;

    @PositiveOrZero(message = "Price must not be negative.")
    private double price;

    @NotBlank(message = "Product description is required.")
    private String description;

    @NotBlank(message = "Product image is required.")
    private String imageUrl;

    @NotBlank(message = "Category is required.")
    private String categoryId;

}

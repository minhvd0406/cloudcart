package com.cloudcart.categoryservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDto {

    @NotBlank(message = "Category name is required.")
    private String name;

    @NotBlank(message = "Category description is required.")
    private String description;

    @NotBlank(message = "Category image is required.")
    private String imageUrl;
}

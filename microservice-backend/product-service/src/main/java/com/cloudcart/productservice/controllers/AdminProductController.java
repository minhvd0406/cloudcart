package com.cloudcart.productservice.controllers;

import com.cloudcart.productservice.dtos.ApiResponseDto;
import com.cloudcart.productservice.dtos.ProductRequestDto;
import com.cloudcart.productservice.exceptions.ResourceNotFoundException;
import com.cloudcart.productservice.exceptions.ServiceLogicException;
import com.cloudcart.productservice.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponseDto<?>> addProduct(@Valid @RequestBody ProductRequestDto requestDto) throws ServiceLogicException, ResourceNotFoundException {
        return productService.addProduct(requestDto);
    }

    @PutMapping("/edit")
    public ResponseEntity<ApiResponseDto<?>> editProduct(@RequestParam String productId, @Valid @RequestBody ProductRequestDto requestDto) throws ServiceLogicException, ResourceNotFoundException {
        return productService.editProduct(productId, requestDto);
    }

}

package com.cloudcart.productservice.controllers;

import com.cloudcart.productservice.dtos.ApiResponseDto;
import com.cloudcart.productservice.exceptions.ResourceNotFoundException;
import com.cloudcart.productservice.exceptions.ServiceLogicException;
import com.cloudcart.productservice.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class CommonProductController {

    private final ProductService productService;

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponseDto<?>> getAllProducts() throws ServiceLogicException{
        return productService.getAllProducts();
    }

    @GetMapping("/get/byId")
    public ResponseEntity<ApiResponseDto<?>> getProductById(@RequestParam String id) throws ServiceLogicException{
        return productService.getProductById(id);
    }

    @GetMapping("/get/byCategory")
    public ResponseEntity<ApiResponseDto<?>> getProductByCategory(@RequestParam String id) throws ServiceLogicException, ResourceNotFoundException {
        return productService.getProductByCategory(id);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponseDto<?>> searchProducts(@RequestParam String searchKey) throws ServiceLogicException{
        return productService.searchProducts(searchKey);
    }


}

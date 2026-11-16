package com.cloudcart.categoryservice.controllers;

import com.cloudcart.categoryservice.dtos.ApiResponseDto;
import com.cloudcart.categoryservice.exceptions.ServiceLogicException;
import com.cloudcart.categoryservice.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CommonCategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get/all")
    public ResponseEntity<ApiResponseDto<?>> getAllCategories() throws ServiceLogicException {
        return categoryService.getAllCategories();
    }

    @GetMapping("/get/byId")
    public ResponseEntity<ApiResponseDto<?>> getCategoryById(@RequestParam String id) throws ServiceLogicException {
        return categoryService.getCategoryById(id);
    }

}

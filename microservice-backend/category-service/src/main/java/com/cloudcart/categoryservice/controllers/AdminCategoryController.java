package com.cloudcart.categoryservice.controllers;

import com.cloudcart.categoryservice.dtos.ApiResponseDto;
import com.cloudcart.categoryservice.dtos.CategoryRequestDto;
import com.cloudcart.categoryservice.exceptions.CategoryAlreadyExistsException;
import com.cloudcart.categoryservice.exceptions.ServiceLogicException;
import com.cloudcart.categoryservice.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto<?>> createCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto) throws ServiceLogicException, CategoryAlreadyExistsException {
        return categoryService.createCategory(categoryRequestDto);
    }

    @PutMapping("/edit")
    public ResponseEntity<ApiResponseDto<?>> editCategory(@RequestParam String categoryId, @Valid @RequestBody CategoryRequestDto categoryRequestDto) throws ServiceLogicException, CategoryAlreadyExistsException {
        return categoryService.editCategory(categoryId, categoryRequestDto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponseDto<?>> deleteCategory(@RequestParam String categoryId) throws ServiceLogicException, CategoryAlreadyExistsException {
        return categoryService.deleteCategory(categoryId);
    }

}

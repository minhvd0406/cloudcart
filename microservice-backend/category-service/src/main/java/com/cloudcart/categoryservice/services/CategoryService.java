package com.cloudcart.categoryservice.services;


import com.cloudcart.categoryservice.dtos.ApiResponseDto;
import com.cloudcart.categoryservice.dtos.CategoryRequestDto;
import com.cloudcart.categoryservice.exceptions.CategoryAlreadyExistsException;
import com.cloudcart.categoryservice.exceptions.ServiceLogicException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    ResponseEntity<ApiResponseDto<?>> getAllCategories() throws ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> getCategoryById(String categoryId) throws ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> createCategory(CategoryRequestDto categoryRequestDto) throws ServiceLogicException, CategoryAlreadyExistsException;

    ResponseEntity<ApiResponseDto<?>> editCategory(String categoryId, CategoryRequestDto categoryRequestDto) throws ServiceLogicException, CategoryAlreadyExistsException;

    ResponseEntity<ApiResponseDto<?>> deleteCategory(String categoryId) throws ServiceLogicException;
}

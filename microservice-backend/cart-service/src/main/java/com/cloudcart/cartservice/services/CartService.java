package com.cloudcart.cartservice.services;

import com.cloudcart.cartservice.dtos.ApiResponseDto;
import com.cloudcart.cartservice.dtos.CartItemRequestDto;
import com.cloudcart.cartservice.exceptions.ResourceNotFoundException;
import com.cloudcart.cartservice.exceptions.ServiceLogicException;
import org.springframework.http.ResponseEntity;

public interface CartService {
    ResponseEntity<ApiResponseDto<?>> addItemToCart(String userId, CartItemRequestDto requestDto) throws ResourceNotFoundException, ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> getCartItemsByUser(String userId) throws ResourceNotFoundException, ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> removeCartItemFromCart(String userId, String productId) throws ServiceLogicException, ResourceNotFoundException;
    ResponseEntity<ApiResponseDto<?>> clearCartById(String id) throws ServiceLogicException, ResourceNotFoundException;
    ResponseEntity<ApiResponseDto<?>> getCartById(String id) throws ServiceLogicException;
}

package com.cloudcart.orderservice.services;

import com.cloudcart.orderservice.dtos.ApiResponseDto;
import com.cloudcart.orderservice.dtos.OrderRequestDto;
import com.cloudcart.orderservice.exceptions.ResourceNotFoundException;
import com.cloudcart.orderservice.exceptions.ServiceLogicException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    ResponseEntity<ApiResponseDto<?>> createOrder(String token, OrderRequestDto request) throws ResourceNotFoundException, ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> getOrdersByUser(String userId) throws ResourceNotFoundException, ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> cancelOrder(String orderId) throws ServiceLogicException, ResourceNotFoundException;

    ResponseEntity<ApiResponseDto<?>> getAllOrders() throws ServiceLogicException;
}

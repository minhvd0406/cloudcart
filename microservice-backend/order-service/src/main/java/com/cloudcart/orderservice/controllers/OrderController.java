package com.cloudcart.orderservice.controllers;

import com.cloudcart.orderservice.dtos.ApiResponseDto;
import com.cloudcart.orderservice.dtos.OrderRequestDto;
import com.cloudcart.orderservice.exceptions.ResourceNotFoundException;
import com.cloudcart.orderservice.exceptions.ServiceLogicException;
import com.cloudcart.orderservice.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<ApiResponseDto<?>> createOrder(Authentication authentication, @Valid @RequestBody OrderRequestDto request) throws ResourceNotFoundException, ServiceLogicException {
        return orderService.createOrder(authentication.getCredentials().toString(), request);
    }

    @GetMapping("/get/byUser")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<ApiResponseDto<?>> getOrdersByUser(Authentication authentication) throws ResourceNotFoundException, ServiceLogicException {
        return orderService.getOrdersByUser(authentication.getPrincipal().toString());
    }

    @GetMapping("/get/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<ApiResponseDto<?>> getAllOrders() throws ResourceNotFoundException, ServiceLogicException {
        return orderService.getAllOrders();
    }

    @PatchMapping("/cancel")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<ApiResponseDto<?>> cancelOrder(@RequestParam String orderId) throws ResourceNotFoundException, ServiceLogicException {
        return orderService.cancelOrder(orderId);
    }


}

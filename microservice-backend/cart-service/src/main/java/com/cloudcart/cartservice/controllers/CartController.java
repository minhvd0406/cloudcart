package com.cloudcart.cartservice.controllers;

import com.cloudcart.cartservice.dtos.ApiResponseDto;
import com.cloudcart.cartservice.dtos.CartItemRequestDto;
import com.cloudcart.cartservice.exceptions.ResourceNotFoundException;
import com.cloudcart.cartservice.exceptions.ServiceLogicException;
import com.cloudcart.cartservice.services.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<ApiResponseDto<?>> addItemToCart(Authentication authentication, @Valid @RequestBody CartItemRequestDto requestDto)
            throws ResourceNotFoundException, ServiceLogicException{
        return cartService.addItemToCart(authentication.getPrincipal().toString(), requestDto);
    }

    @GetMapping("/get/byUser")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<ApiResponseDto<?>> getCartItemsByUser(Authentication authentication)
            throws ResourceNotFoundException, ServiceLogicException{
        return cartService.getCartItemsByUser(authentication.getPrincipal().toString());
    }

    @DeleteMapping("/remove")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<ApiResponseDto<?>> removeCartItemFromCart(Authentication authentication, @RequestParam String productId)
            throws ServiceLogicException, ResourceNotFoundException {
        return cartService.removeCartItemFromCart(authentication.getPrincipal().toString(), productId);
    }

    @GetMapping("/get/byId")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<ApiResponseDto<?>> getCartById(@RequestParam String id) throws ServiceLogicException {
        return cartService.getCartById(id);
    }

    @DeleteMapping("/clear/byId")
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<ApiResponseDto<?>> clearCartById(@RequestParam String id) throws ResourceNotFoundException, ServiceLogicException {
        return cartService.clearCartById(id);
    }

}


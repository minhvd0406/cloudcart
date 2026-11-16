package com.cloudcart.userservice.controllers;

import com.cloudcart.userservice.dtos.ApiResponseDto;
import com.cloudcart.userservice.dtos.UserSyncRequestDto;
import com.cloudcart.userservice.exceptions.ServiceLogicException;
import com.cloudcart.userservice.exceptions.UserNotFoundException;
import com.cloudcart.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final String internalToken;

    public UserController(UserService userService,
                          @Value("${cloudcart.internal-token}") String internalToken) {
        this.userService = userService;
        this.internalToken = internalToken;
    }

    @GetMapping("/exists/byId")
    public ResponseEntity<ApiResponseDto<?>> existsUserById(@RequestParam String userId) throws ServiceLogicException{
        return userService.existsUserById(userId);
    }

    @GetMapping("/get/byId")
    ResponseEntity<ApiResponseDto<?>> getUserById(@RequestParam String id) throws UserNotFoundException, ServiceLogicException {
        return userService.getUserById(id);
    }

    @PostMapping("/internal/sync")
    ResponseEntity<ApiResponseDto<?>> syncUser(
            @RequestBody @Valid UserSyncRequestDto request,
            @RequestHeader("X-CloudCart-Internal-Token") String providedToken) {
        if (!MessageDigest.isEqual(internalToken.getBytes(StandardCharsets.UTF_8),
                providedToken.getBytes(StandardCharsets.UTF_8))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return userService.syncUser(request);
    }


}


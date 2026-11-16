package com.cloudcart.authservice.controllers;

import com.cloudcart.authservice.dtos.ApiResponseDto;
import com.cloudcart.authservice.dtos.SignInRequestDto;
import com.cloudcart.authservice.dtos.SignUpRequestDto;
import com.cloudcart.authservice.exceptions.ServiceLogicException;
import com.cloudcart.authservice.exceptions.UserAlreadyExistsException;
import com.cloudcart.authservice.exceptions.UserNotFoundException;
import com.cloudcart.authservice.exceptions.UserVerificationFailedException;
import com.cloudcart.authservice.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    ResponseEntity<ApiResponseDto<?>> registerUser(@RequestBody @Valid SignUpRequestDto signUpRequestDto)
            throws UnsupportedEncodingException, UserAlreadyExistsException, ServiceLogicException{
        return authService.registerUser(signUpRequestDto);
    }

    @GetMapping("/signup/resend")
    ResponseEntity<ApiResponseDto<?>> resendVerificationCode(@RequestParam String email)
            throws UnsupportedEncodingException, UserNotFoundException, ServiceLogicException{
        return authService.resendVerificationCode(email);
    }

    @GetMapping("/signup/verify")
    ResponseEntity<ApiResponseDto<?>> verifyRegistrationVerification(@RequestParam String code)
            throws UserVerificationFailedException{
        return authService.verifyRegistrationVerification(code);
    }

    @PostMapping("/signin")
    ResponseEntity<ApiResponseDto<?>> authenticateUser(@RequestBody @Valid SignInRequestDto signInRequestDto){
        return authService.authenticateUser(signInRequestDto);
    }

    @GetMapping("/isValidToken")
    ResponseEntity<ApiResponseDto<?>> validateToken(@RequestParam String token){
        return authService.validateToken(token);
    }

}

package com.cloudcart.authservice.services;

import com.cloudcart.authservice.dtos.ApiResponseDto;
import com.cloudcart.authservice.dtos.SignInRequestDto;
import com.cloudcart.authservice.dtos.SignUpRequestDto;
import com.cloudcart.authservice.exceptions.ServiceLogicException;
import com.cloudcart.authservice.exceptions.UserAlreadyExistsException;
import com.cloudcart.authservice.exceptions.UserNotFoundException;
import com.cloudcart.authservice.exceptions.UserVerificationFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface AuthService {
    ResponseEntity<ApiResponseDto<?>> registerUser(SignUpRequestDto signUpRequestDto) throws UnsupportedEncodingException, UserAlreadyExistsException, ServiceLogicException;
    ResponseEntity<ApiResponseDto<?>> resendVerificationCode(String email) throws UnsupportedEncodingException, UserNotFoundException, ServiceLogicException;
    ResponseEntity<ApiResponseDto<?>> verifyRegistrationVerification(String code) throws UserVerificationFailedException;
    ResponseEntity<ApiResponseDto<?>> authenticateUser(SignInRequestDto signInRequestDto);
    ResponseEntity<ApiResponseDto<?>> validateToken(String token);
}

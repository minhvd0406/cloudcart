package com.cloudcart.userservice.services;

import com.cloudcart.userservice.dtos.ApiResponseDto;
import com.cloudcart.userservice.exceptions.ServiceLogicException;
import com.cloudcart.userservice.exceptions.UserNotFoundException;
import com.cloudcart.userservice.dtos.UserSyncRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public interface UserService {
    ResponseEntity<ApiResponseDto<?>> existsUserById(String userId) throws ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> getUserById(String id) throws ServiceLogicException, UserNotFoundException;

    ResponseEntity<ApiResponseDto<?>> syncUser(UserSyncRequestDto request);
}

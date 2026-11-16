package com.cloudcart.userservice.services;

import com.cloudcart.userservice.dtos.ApiResponseDto;
import com.cloudcart.userservice.dtos.UserDto;
import com.cloudcart.userservice.dtos.UserSyncRequestDto;
import com.cloudcart.userservice.exceptions.ServiceLogicException;
import com.cloudcart.userservice.exceptions.UserNotFoundException;
import com.cloudcart.userservice.modals.User;
import com.cloudcart.userservice.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseEntity<ApiResponseDto<?>> existsUserById(String userId) throws ServiceLogicException {
        try {
            return ResponseEntity.ok(ApiResponseDto.builder()
                    .isSuccess(true)
                    .response(userRepository.existsById(userId))
                    .message("User exists.")
                    .build());
        }catch(Exception e) {
            log.error(e.getMessage());
            throw new ServiceLogicException("Something went wrong. Try gain later!");
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> getUserById(String id) throws ServiceLogicException, UserNotFoundException {
        try {
            if (userRepository.existsById(id)) {
                return ResponseEntity.ok(ApiResponseDto.builder()
                        .isSuccess(true)
                        .response(userToUserDto(Objects.requireNonNull(userRepository.findById(id).orElse(null))))
                        .build());
            }
        }catch(Exception e) {
            log.error(e.getMessage());
            throw new ServiceLogicException("Something went wrong. Try gain later!");
        }
        throw new UserNotFoundException("User not found with id " + id);
    }

    private UserDto userToUserDto(User user) {
        return UserDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> syncUser(UserSyncRequestDto request) {
        User user = userRepository.findById(request.getId()).orElseGet(User::new);
        user.setId(request.getId());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setEnabled(true);
        userRepository.save(user);

        return ResponseEntity.ok(ApiResponseDto.builder()
                .isSuccess(true)
                .message("User profile synchronized.")
                .build());
    }

}

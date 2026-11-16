package com.cloudcart.authservice.feigns;

import com.cloudcart.authservice.dtos.ApiResponseDto;
import com.cloudcart.authservice.dtos.UserProfileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("USER-SERVICE")
public interface UserService {

    @PostMapping("/user/internal/sync")
    ResponseEntity<ApiResponseDto<?>> syncUser(
            @RequestBody UserProfileDto userProfile,
            @RequestHeader("X-CloudCart-Internal-Token") String internalToken);
}

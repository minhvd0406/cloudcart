package com.cloudcart.orderservice.feigns;

import com.cloudcart.orderservice.dtos.ApiResponseDto;
import com.cloudcart.orderservice.dtos.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("USER-SERVICE")
public interface UserService {

    @GetMapping("/user/get/byId")
    ResponseEntity<ApiResponseDto<UserDto>> getUserById(@RequestParam String id);

}

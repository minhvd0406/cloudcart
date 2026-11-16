package com.cloudcart.notificationservice.controller;

import com.cloudcart.notificationservice.dtos.ApiResponseDto;
import com.cloudcart.notificationservice.dtos.MailRequestDto;
import com.cloudcart.notificationservice.services.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    ResponseEntity<ApiResponseDto<?>> sendEmail(@Valid @RequestBody MailRequestDto requestDto) {
        return notificationService.sendEmail(requestDto);
    }

}

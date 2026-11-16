package com.cloudcart.notificationservice.services;

import com.cloudcart.notificationservice.dtos.ApiResponseDto;
import com.cloudcart.notificationservice.dtos.MailRequestDto;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface NotificationService {
    ResponseEntity<ApiResponseDto<?>> sendEmail(MailRequestDto requestDto);
}

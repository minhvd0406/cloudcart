package com.cloudcart.notificationservice.services;

import com.cloudcart.notificationservice.dtos.ApiResponseDto;
import com.cloudcart.notificationservice.dtos.MailRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.UUID;

@Component
@Slf4j
@ConditionalOnProperty(name = "cloudcart.notification.mode", havingValue = "outbox", matchIfMissing = true)
public class OutboxNotificationService implements NotificationService {

    private final Path outboxDirectory;

    public OutboxNotificationService(
            @Value("${cloudcart.notification.outbox-directory}") Path outboxDirectory) {
        this.outboxDirectory = outboxDirectory;
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> sendEmail(MailRequestDto requestDto) {
        String messageId = UUID.randomUUID().toString();
        try {
            Files.createDirectories(outboxDirectory);
            Path messageFile = outboxDirectory.resolve(messageId + ".html");
            Files.writeString(messageFile, formatMessage(requestDto), StandardCharsets.UTF_8);
            log.info("notification_written messageId={} mode=outbox", messageId);
            return ResponseEntity.ok(ApiResponseDto.builder()
                    .isSuccess(true)
                    .response("Notification saved to local outbox.")
                    .build());
        } catch (IOException exception) {
            log.error("notification_write_failed messageId={} reason={}", messageId, exception.getMessage());
            return ResponseEntity.internalServerError().body(ApiResponseDto.builder()
                    .isSuccess(false)
                    .response("Unable to save notification.")
                    .build());
        }
    }

    private String formatMessage(MailRequestDto requestDto) {
        return "<!-- created: " + Instant.now() + " -->\n"
                + "<!-- to: " + requestDto.getTo() + " -->\n"
                + "<!-- subject: " + requestDto.getSubject() + " -->\n"
                + requestDto.getBody();
    }
}

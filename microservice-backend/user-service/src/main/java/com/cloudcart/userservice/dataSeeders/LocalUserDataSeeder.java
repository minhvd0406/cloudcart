package com.cloudcart.userservice.dataSeeders;

import com.cloudcart.userservice.modals.User;
import com.cloudcart.userservice.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@ConditionalOnProperty(name = "cloudcart.seed.enabled", havingValue = "true")
public class LocalUserDataSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final String userId;
    private final String username;
    private final String email;

    public LocalUserDataSeeder(
            UserRepository userRepository,
            @Value("${cloudcart.seed.user-id}") String userId,
            @Value("${cloudcart.seed.username}") String username,
            @Value("${cloudcart.seed.email}") String email) {
        this.userRepository = userRepository;
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (userRepository.existsById(userId)) {
            log.info("local_seed_user_projection_skipped userId={}", userId);
            return;
        }

        userRepository.save(User.builder()
                .id(userId)
                .username(username)
                .email(email)
                .enabled(true)
                .build());

        log.info("local_seed_user_projection_created userId={}", userId);
    }
}

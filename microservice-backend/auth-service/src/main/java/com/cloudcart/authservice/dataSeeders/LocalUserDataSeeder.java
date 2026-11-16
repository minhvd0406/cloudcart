package com.cloudcart.authservice.dataSeeders;

import com.cloudcart.authservice.enums.ERole;
import com.cloudcart.authservice.modals.Role;
import com.cloudcart.authservice.modals.User;
import com.cloudcart.authservice.repositories.RoleRepository;
import com.cloudcart.authservice.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Component
@Slf4j
@ConditionalOnProperty(name = "cloudcart.seed.enabled", havingValue = "true")
public class LocalUserDataSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final String userId;
    private final String username;
    private final String email;
    private final String password;

    public LocalUserDataSeeder(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            @Value("${cloudcart.seed.user-id}") String userId,
            @Value("${cloudcart.seed.username}") String username,
            @Value("${cloudcart.seed.email}") String email,
            @Value("${cloudcart.seed.password}") String password) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            User conflictingUser = userRepository.findByEmail(email)
                    .or(() -> userRepository.findByUsername(username))
                    .orElse(null);
            if (conflictingUser != null) {
                userRepository.delete(conflictingUser);
                userRepository.flush();
                log.info("local_seed_user_replaced oldUserId={}", conflictingUser.getId());
            }
            user = new User();
            user.setId(userId);
        }

        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseGet(() -> roleRepository.save(new Role(ERole.ROLE_USER)));

        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        user.setVerificationCode(null);
        user.setVerificationCodeExpiryTime(null);
        user.setRoles(new HashSet<>());
        user.getRoles().add(userRole);
        userRepository.save(user);

        log.info("local_seed_user_ready userId={} email={}", userId, email);
    }
}

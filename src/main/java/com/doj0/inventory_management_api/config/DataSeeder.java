package com.doj0.inventory_management_api.config;

import com.doj0.inventory_management_api.enums.Role;
import com.doj0.inventory_management_api.model.User;
import com.doj0.inventory_management_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner seedDatabase() {
        return args -> {
            seedUserIfNotExists(
                "Dojo Admin",
                "admin@dojo.com",
                "testPass",
                Role.ROLE_ADMIN
            );

            seedUserIfNotExists(
                "Dojo Staff",
                "staff@dojo.com",
                "testPass",
                Role.ROLE_STAFF
            );
        };
    }

    private void seedUserIfNotExists(String name, String email, String rawPassword, Role role) {
        if (userRepository.findByEmail(email).isEmpty()) {
            User user = User.builder()
                    .name(name)
                    .email(email)
                    .password(passwordEncoder.encode(rawPassword))
                    .roles(Set.of(role))
                    .build();

            userRepository.save(user);
            log.info(">>> Successfully seeded default user [{}] with role [{}]", email, role);
        } else {
            log.debug(">>> User [{}] already exists. Skipping seed.", email);
        }
    }
}
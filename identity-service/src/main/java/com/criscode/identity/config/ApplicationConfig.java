package com.criscode.identity.config;

import com.criscode.identity.entity.Role;
import com.criscode.identity.entity.User;
import com.criscode.identity.repository.RoleRepository;
import com.criscode.identity.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    CommandLineRunner commandLineRunner(RoleRepository roleRepository, UserRepository userRepository) {
        return args -> {

            Role checkRole1 = roleRepository.findByRole("USER");
            Role checkRole2 = roleRepository.findByRole("ADMIN");
            Optional<User> checkUser = userRepository.findByEmail("admin@admin");

            if (checkRole1 == null && checkRole2 == null && checkUser.isEmpty()) {
                Role roleUser = Role.builder().role("USER").build();
                Role roleAdmin = Role.builder().role("ADMIN").build();
                roleRepository.save(roleUser);
                roleRepository.save(roleAdmin);

                List<Role> role = new ArrayList<>();
                role.add(roleAdmin);
                User user = User.builder()
                        .firstName("admin")
                        .lastName("admin")
                        .email("admin@admin")
                        .password(passwordEncoder.encode("1234"))
                        .roles(role)
                        .build();

                userRepository.save(user);
            }

        };
    }

}

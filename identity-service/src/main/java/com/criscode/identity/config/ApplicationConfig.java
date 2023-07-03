package com.criscode.identity.config;

import com.criscode.identity.entity.Role;
import com.criscode.identity.repository.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    CommandLineRunner commandLineRunner (RoleRepository roleRepository) {
        return args -> {
            Role role = Role.builder().role("USER").build();
            roleRepository.save(role);
        };
    }
}

package com.criscode.identity.converter;

import com.criscode.identity.dto.RegisterRequest;
import com.criscode.identity.entity.User;
import com.criscode.identity.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserConverter {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public User map(RegisterRequest registerRequest) {
        return User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(registerRequest.getRoles().stream()
                        .map(roleRepository::findByRole)
                        .collect(Collectors.toList())
                )
                .build();
    }

}

package com.criscode.identity.service;

import com.criscode.identity.dto.AuthRequest;
import com.criscode.identity.dto.AuthResponse;
import com.criscode.identity.dto.EmailCheckExistResponse;
import com.criscode.identity.dto.RegisterRequest;
import com.criscode.identity.entity.Role;
import com.criscode.identity.entity.User;
import com.criscode.identity.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    /**
     * Handle Register:
     * 1. Check email exited
     * 2. Send mail otp
     * 3. Confirm
     * 4. Save User
     */

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthResponse saveUser(RegisterRequest registerRequest) {

        if (emailCheckExistResponse(registerRequest).existed()) {
            return AuthResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        } else {
            User user = objectMapper.convertValue(registerRequest, User.class);
            user.setRoles(
                    registerRequest.getRoles().stream().map(
                            role -> new Role(role)).collect(Collectors.toList())
            );
            userRepository.save(user);
            return authenticate(AuthRequest.builder()
                    .email(registerRequest.getEmail())
                    .password(registerRequest.getPassword())
                    .build());
        }
    }

    public EmailCheckExistResponse emailCheckExistResponse(RegisterRequest registerRequest) {
        Optional<User> user = userRepository.findByEmail(registerRequest.getEmail());
        return user.isPresent()
                ? new EmailCheckExistResponse(true)
                : new EmailCheckExistResponse(false);
    }

    public AuthResponse authenticate(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        // Xem lại refresh token and user_id
        if (authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return AuthResponse.builder()
                    .status(HttpStatus.OK.value())
                    .accessToken(jwtService.generateToken(userDetails))
                    .build();
        } else {
            return AuthResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
    }


}

package com.criscode.identity.service;

import com.criscode.clients.cart.CartClient;
import com.criscode.clients.cart.dto.CartDto;
import com.criscode.clients.product.ProductClient;
import com.criscode.clients.user.UserClient;
import com.criscode.identity.config.CustomUserDetailsService;
import com.criscode.identity.converter.UserConverter;
import com.criscode.identity.dto.AuthRequest;
import com.criscode.identity.dto.AuthResponse;
import com.criscode.identity.dto.EmailCheckExistResponse;
import com.criscode.identity.dto.RegisterRequest;
import com.criscode.identity.entity.User;
import com.criscode.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserConverter userConverter;
    private final CartClient cartClient;

    public AuthResponse saveUser(RegisterRequest registerRequest) {

        if (emailCheckExistResponse(registerRequest).existed()) {
            return AuthResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        } else {
            User user = userConverter.map(registerRequest);
            user = userRepository.save(user);
            CartDto cartDto = CartDto.builder().userId(user.getId()).build();
            cartClient.save(cartDto);
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
            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
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

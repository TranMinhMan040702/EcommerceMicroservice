package com.criscode.identity.service.impl;

import com.criscode.amqp.RabbitMQMessageProducer;
import com.criscode.clients.cart.CartClient;
import com.criscode.clients.cart.dto.CartDto;
import com.criscode.clients.mail.OtpClient;
import com.criscode.clients.mail.dto.CheckOtpResponse;
import com.criscode.clients.mail.dto.EmailDetails;
import com.criscode.clients.user.dto.ValidateTokenResponse;
import com.criscode.exceptionutils.AlreadyExistsException;
import com.criscode.identity.config.CustomUserDetailsService;
import com.criscode.identity.converter.UserConverter;
import com.criscode.identity.dto.*;
import com.criscode.identity.entity.Role;
import com.criscode.identity.entity.User;
import com.criscode.identity.repository.UserRepository;
import com.criscode.identity.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserConverter userConverter;
    private final CartClient cartClient;
    private final OtpClient otpClient;
    private final RabbitMQMessageProducer producer;
    private String EXCHANGE = "internal.exchange";
    private String ROUTING_KEY = "internal.mail.routing-key";

    @Override
    public AuthResponse saveUser(RegisterRequest registerRequest, String code) {

        CheckOtpResponse checkOtpResponse = otpClient.checkOtp(code, registerRequest.getEmail());

        if (!checkOtpResponse.correct()) {
            return AuthResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        } else {
            User user = userConverter.mapToEntity(registerRequest);
            user = userRepository.save(user);
            CartDto cartDto = CartDto.builder().userId(user.getId()).build();
            cartClient.save(cartDto);
            return authenticate(AuthRequest.builder()
                    .email(registerRequest.getEmail())
                    .password(registerRequest.getPassword())
                    .build());
        }
    }

    @Override
    public void sendEmail(String email) {
        if (emailCheckExistResponse(email).existed()) {
            throw new AlreadyExistsException("Email already exist: " + email);
        }
        producer.publish(
                EmailDetails.builder().recipient(email).build(),
                EXCHANGE,
                ROUTING_KEY
        );
    }

    @Override
    public EmailCheckExistResponse emailCheckExistResponse(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent()
                ? new EmailCheckExistResponse(true)
                : new EmailCheckExistResponse(false);
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        // Xem lại refresh token and user_id
        if (authentication.isAuthenticated()) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
            User user = userRepository.findByEmail(authRequest.getEmail()).get();
            Set<String> roles = user.getRoles().stream().map(Role::getRole).collect(Collectors.toSet());
            return AuthResponse.builder()
                    .status(HttpStatus.OK.value())
                    .accessToken(jwtService.generateToken(userDetails))
                    .roles(roles)
                    .userId(user.getId())
                    .build();
        } else {
            return AuthResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .build();
        }
    }

    @Override
    public ValidateTokenResponse validateToken(String token) {
        String username = jwtService.extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return ValidateTokenResponse.builder()
                .username(userDetails.getUsername())
                .authorities(userDetails.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .build();
    }
}

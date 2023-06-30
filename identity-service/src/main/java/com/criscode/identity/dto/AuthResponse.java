package com.criscode.identity.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private Integer status;
    private String accessToken;
    private String refreshToken;
    private Set<String> roles;
    private Integer userId;
}

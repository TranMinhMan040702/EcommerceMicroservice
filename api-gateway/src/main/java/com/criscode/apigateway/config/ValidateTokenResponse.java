package com.criscode.apigateway.config;

import lombok.Data;

import java.util.List;

@Data
public class ValidateTokenResponse {
    private String username;
    private List<String> authorities;
}

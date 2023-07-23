package com.criscode.apigateway.config;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class ValidateTokenResponse {
    private String username;
    private List<String> authorities;
}

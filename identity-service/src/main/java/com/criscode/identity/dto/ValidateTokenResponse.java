package com.criscode.identity.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ValidateTokenResponse {
    private String username;
    private List<String> authorities;
}

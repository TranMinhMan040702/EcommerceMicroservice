package com.criscode.clients.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class ValidateTokenResponse {
    private String username;
    private List<String> authorities;
}

package com.criscode.identity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ResetPasswordResponse {

    private int status;
    private String message;

}

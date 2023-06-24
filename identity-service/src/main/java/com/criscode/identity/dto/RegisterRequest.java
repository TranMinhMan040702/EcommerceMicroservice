package com.criscode.identity.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private Set<String> roles = new HashSet<>(Arrays.asList("USER"));

}

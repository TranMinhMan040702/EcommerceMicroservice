package com.criscode.identity.controller;

import com.criscode.identity.dto.RegisterRequest;
import com.criscode.identity.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public void register(@RequestParam("email") String email) {
        authService.sendEmail(email);
    }

    @PostMapping("/register-confirm/{code}")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegisterRequest registerRequest,
            @PathVariable("code") String code) {
        return ResponseEntity.ok(authService.saveUser(registerRequest, code));
    }

}

package com.criscode.identity.controller;

import com.criscode.identity.dto.SingleResponse;
import com.criscode.identity.dto.ValidateTokenResponse;
import com.criscode.identity.dto.AuthRequest;
import com.criscode.identity.dto.RegisterRequest;
import com.criscode.identity.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin({"https://thunderous-basbousa-75b1ca.netlify.app/", "http://localhost:3000"})
@RestController
@RequestMapping("/api/v1/identity-service/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/register")
    public void register(@RequestParam("email") String email) {
        authService.sendEmail(email);
    }

    @PostMapping("/register-confirm/{code}")
    public ResponseEntity<?> registerConfirm(
            @RequestBody @Valid RegisterRequest registerRequest,
            @PathVariable("code") String code) {
        return ResponseEntity.ok(authService.saveUser(registerRequest, code));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PatchMapping("/validate-token/{token}")
    public ResponseEntity<SingleResponse<ValidateTokenResponse>> validateAccessToken(@PathVariable("token") String token) {
        return ResponseEntity.ok(SingleResponse.of(authService.validateToken(token)));
    }

}

package com.criscode.identity.service;

import com.criscode.identity.dto.AuthRequest;
import com.criscode.identity.dto.AuthResponse;
import com.criscode.identity.dto.EmailCheckExistResponse;
import com.criscode.identity.dto.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface IAuthService {
    AuthResponse saveUser(RegisterRequest registerRequest, String code);

    void sendEmail(String email);

    EmailCheckExistResponse emailCheckExistResponse(String email);

    AuthResponse authenticate(AuthRequest authRequest);
}

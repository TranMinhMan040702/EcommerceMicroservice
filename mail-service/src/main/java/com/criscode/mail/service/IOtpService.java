package com.criscode.mail.service;

import com.criscode.clients.mail.dto.CheckOtpResponse;
import com.criscode.mail.entity.Otp;
import org.springframework.stereotype.Service;

@Service
public interface IOtpService {
    Otp save(String email);

    CheckOtpResponse checkOtp(String code, String email);

    String createOtp();
}

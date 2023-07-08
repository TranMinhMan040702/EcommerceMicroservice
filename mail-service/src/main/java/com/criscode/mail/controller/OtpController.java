package com.criscode.mail.controller;

import com.criscode.clients.mail.dto.CheckOtpResponse;
import com.criscode.mail.service.IOtpService;
import com.criscode.mail.service.impl.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OtpController {

    private final IOtpService otpService;

    @GetMapping("/check-otp")
    CheckOtpResponse checkOtp(@RequestParam("code") String code, @RequestParam("email") String email) {
        return otpService.checkOtp(code, email);
    }
}

package com.criscode.clients.mail;

import com.criscode.clients.mail.dto.CheckOtpResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("mail")
public interface OtpClient {
    @GetMapping("/check-otp")
    CheckOtpResponse checkOtp(@RequestParam("code") String code, @RequestParam("email") String email);
}

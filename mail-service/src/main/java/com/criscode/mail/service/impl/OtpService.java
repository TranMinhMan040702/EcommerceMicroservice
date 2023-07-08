package com.criscode.mail.service.impl;

import com.criscode.clients.mail.dto.CheckOtpResponse;
import com.criscode.mail.entity.Otp;
import com.criscode.mail.repository.OtpRepository;
import com.criscode.mail.service.IOtpService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Component
@AllArgsConstructor
public class OtpService implements IOtpService {

    private final OtpRepository otpRepository;

    @Override
    public Otp save(String email) {
        Otp otp = new Otp();
        otp.setCode(createOtp());
        otp.setEmail(email);
        otp.setExpirationDate(new Date(System.currentTimeMillis() + 4 * 60 * 1000));
        return otpRepository.save(otp);
    }

    @Override
    public CheckOtpResponse checkOtp(String code, String email) {
        Otp otp =otpRepository.findByCodeAndEmail(code, email);
        return otp != null
                ? new CheckOtpResponse(true)
                : new CheckOtpResponse(false);
    }

    @Override
    public String createOtp() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
}

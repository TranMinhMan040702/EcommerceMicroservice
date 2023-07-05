package com.criscode.mail.service;

import com.criscode.clients.mail.dto.CheckOtpResponse;
import com.criscode.mail.entity.Otp;
import com.criscode.mail.repository.OtpRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
@AllArgsConstructor
public class OtpService {

    private final OtpRepository otpRepository;

    public Otp save(String email) {
        Otp otp = new Otp();
        otp.setCode(createOtp());
        otp.setEmail(email);
        otp.setExpirationDate(new Date(System.currentTimeMillis() + 4 * 60 * 1000));
        return otpRepository.save(otp);
    }

    public CheckOtpResponse checkOtp(String code, String email) {
        Otp otp =otpRepository.findByCodeAndEmail(code, email);
        return otp != null
                ? new CheckOtpResponse(true)
                : new CheckOtpResponse(false);
    }

    public String createOtp() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }
}

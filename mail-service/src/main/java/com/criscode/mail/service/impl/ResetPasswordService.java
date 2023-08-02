package com.criscode.mail.service.impl;

import com.criscode.mail.entity.ResetPassword;
import com.criscode.mail.repository.ResetPasswordRepository;
import com.criscode.mail.service.IResetPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class ResetPasswordService implements IResetPassword {

    private final ResetPasswordRepository resetPasswordRepository;

    @Override
    public void save(String code, String email, Date expired) {
        resetPasswordRepository.save(ResetPassword.builder()
                .code(code).email(email).expirationDate(expired)
                .build());
    }

    @Override
    public Boolean checkCodeAndEmail(String code, String email) {
        ResetPassword resetPassword = resetPasswordRepository.findByCodeAndEmail(code, email);
        if (resetPassword != null) {
            if (!resetPassword.getExpirationDate().before(new Date())) {
                return true;
            }
            clearCode(code, email);
            log.debug("Code expired");
        }
        log.debug("Not found code and email");
        return false;
    }

    @Override
    public void clearCode(String code, String email) {
        ResetPassword resetPassword = resetPasswordRepository.findByCodeAndEmail(code, email);
        if (resetPassword != null) {
            resetPasswordRepository.delete(resetPassword);
        }
        log.debug("Not found code and email");
    }

}

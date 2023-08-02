package com.criscode.mail.service;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface IResetPassword {
    void save(String code, String email, Date expired);

    Boolean checkCodeAndEmail(String code, String email);

    void clearCode(String code, String email);
}

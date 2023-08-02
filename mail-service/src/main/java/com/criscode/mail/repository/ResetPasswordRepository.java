package com.criscode.mail.repository;

import com.criscode.mail.entity.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword, String> {
    ResetPassword findByCodeAndEmail(String code, String email);
}

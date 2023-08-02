package com.criscode.mail.repository;

import com.criscode.mail.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<Otp, String> {
    Otp findByCodeAndEmail(String code, String email);
}

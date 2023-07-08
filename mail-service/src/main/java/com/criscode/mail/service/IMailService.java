package com.criscode.mail.service;

import com.criscode.clients.mail.dto.EmailDetails;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public interface IMailService {
    @Async
    void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml);

    @Async
    void sendActivationEmail(EmailDetails emailDetails);
}

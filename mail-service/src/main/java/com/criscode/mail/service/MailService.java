package com.criscode.mail.service;

import com.criscode.clients.mail.dto.EmailDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final OtpService otpService;

    @Value("${spring.mail.username}")
    private String sender;

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        var mimeMessage = javaMailSender.createMimeMessage();

        try {
            var mimeHelper = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            mimeHelper.setFrom(sender);
            mimeHelper.setTo(to);
            mimeHelper.setSubject(subject);
            mimeHelper.setText(content, isHtml);

            javaMailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            log.warn("Email wasn't send to user {}", to, e);
        }
    }

    @Async
    public void sendActivationEmail(EmailDetails emailDetails) {

        var userEmail = emailDetails.getRecipient();
        String code = otpService.save(emailDetails.getRecipient()).getCode();

        if (userEmail != null) {
            log.debug("Sending email template to {}", userEmail);

            var subject = "Activate your account";
            var content = "<b>Vui lòng nhập mã code để xác nhận</b><br/>" + "<h1>" + code + "</h1>";
            sendEmail(userEmail, subject, content, false, true);
        }
    }

}

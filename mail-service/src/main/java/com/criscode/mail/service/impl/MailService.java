package com.criscode.mail.service.impl;

import com.criscode.clients.mail.dto.EmailDetails;
import com.criscode.mail.service.IMailService;
import com.criscode.mail.service.IResetPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Random;

@Component
@Slf4j
@RequiredArgsConstructor
public class MailService implements IMailService {

    private final JavaMailSender javaMailSender;
    private final OtpService otpService;
    private final IResetPassword resetPassword;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
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

    @Override
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

    @Override
    public void sendEmailResetPassword(String email) {
        if (email != null) {
            String code = randomAlphanumericString(10);
            String url = "http://localhost:3000/forgot-password?code=" + code + "&email=" + email;

            var subject = "Reset Password";
            var content = "<b>Truy cập vào " + "<a href = \"" + url + "\">Ấn tại đây</a>"
                    + " hoặc đường dẫn <br/>" + "[" + "<a href = \"" + url + "\">" + url + "</a>"
                    + "]" + " để đặt lại mật khẩu.</b>";

            resetPassword.save(code, email, new Date(System.currentTimeMillis() + 5 * 60 * 1000));

            sendEmail(email, subject, content, false, true);
        }
    }

    private String randomAlphanumericString(int length) {
        String alphanumericCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuv";
        StringBuffer randomString = new StringBuffer(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(alphanumericCharacters.length());
            char randomChar = alphanumericCharacters.charAt(randomIndex);
            randomString.append(randomChar);
        }
        return randomString.toString();
    }

}

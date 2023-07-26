package com.criscode.mail.controller;

import com.criscode.clients.mail.dto.EmailDetails;
import com.criscode.mail.service.IMailService;
import com.criscode.mail.service.impl.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/send-email/")
@RequiredArgsConstructor
public class MailController {

    private final IMailService mailService;

    @PostMapping("activation-email")
    void sendActivationEmail(@RequestBody EmailDetails emailDetails) {
        mailService.sendActivationEmail(emailDetails);
    }

    @PostMapping("forgot-password")
    void sendForgotPassword(@RequestParam("email") String email) {
        mailService.sendEmailResetPassword(email);
    }

}

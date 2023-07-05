package com.criscode.mail.controller;

import com.criscode.clients.mail.dto.EmailDetails;
import com.criscode.mail.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send-email")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @PostMapping("activation-email")
    void sendActivationEmail(@RequestBody EmailDetails emailDetails) {
        mailService.sendActivationEmail(emailDetails);
    }
}

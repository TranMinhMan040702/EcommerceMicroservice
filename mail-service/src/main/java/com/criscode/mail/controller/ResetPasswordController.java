package com.criscode.mail.controller;

import com.criscode.mail.service.IResetPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ResetPasswordController {

    private final IResetPassword resetPassword;

    @GetMapping("/check-code-email")
    public Boolean checkCodeAndEmail(@RequestParam("code") String code, @RequestParam("email") String email) {
        return resetPassword.checkCodeAndEmail(code, email);
    }

    @GetMapping("/clear-code")
    public void clearCode(@RequestParam("code") String code, @RequestParam("email") String email) {
        resetPassword.clearCode(code, email);
    }

}

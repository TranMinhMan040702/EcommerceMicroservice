package com.criscode.identity.controller;

import com.criscode.clients.user.dto.UserExistResponse;
import com.criscode.identity.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user-check/{userid}")
    public UserExistResponse existed(@PathVariable Integer userid) {
        return userService.checkUserExisted(userid);
    }

}
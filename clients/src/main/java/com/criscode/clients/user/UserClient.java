package com.criscode.clients.user;

import com.criscode.clients.user.dto.UserDto;
import com.criscode.clients.user.dto.UserExistResponse;
import com.criscode.clients.user.dto.UserResponse;
import com.criscode.clients.user.dto.ValidateTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "identity")
public interface UserClient {
    @GetMapping("/api/v1/identity-service/user/user-check/{userid}")
    UserExistResponse existed(@PathVariable("userid") Integer userid);

    @GetMapping("/api/v1/identity-service/user/admin/get-all-user")
    List<UserDto> findAll();
    @GetMapping("/api/v1/identity-service/user/get-user/{userId}")
    UserResponse getUser(@PathVariable("userId") Integer userId);

    @GetMapping("/validate-token")
    ValidateTokenResponse validateAccessToken(@RequestParam("token") String token);

}

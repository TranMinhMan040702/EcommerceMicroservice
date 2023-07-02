package com.criscode.clients.user;

import com.criscode.clients.user.dto.UserExistResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "identity")
public interface UserClient {
    @GetMapping("/api/v1/user-check/{userid}")
    UserExistResponse existed(@PathVariable("userid") Integer userid);
}

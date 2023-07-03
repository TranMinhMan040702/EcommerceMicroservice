package com.criscode.clients.user;

import com.criscode.clients.user.dto.UserDto;
import com.criscode.clients.user.dto.UserExistResponse;
import com.criscode.clients.user.dto.UserReviewDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "identity")
public interface UserClient {
    @GetMapping("/api/v1/user-check/{userid}")
    UserExistResponse existed(@PathVariable("userid") Integer userid);

    @GetMapping("/api/v1/admin/get-all-user")
    List<UserDto> findAll();
    @GetMapping("/api/v1/get-user-review/{userId}")
    UserReviewDto getUserReview(@PathVariable("userId") Integer userId);

}

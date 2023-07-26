package com.criscode.clients.review;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "review")
public interface ReviewClient {
    @GetMapping("/api/v1/review-service/review/statistic/rating")
    List<Long> statisticRating();
}

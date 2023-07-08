package com.criscode.review.controller;

import com.criscode.review.dto.ReviewDto;
import com.criscode.review.service.IReviewService;
import com.criscode.review.service.impl.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReviewController {

    private final IReviewService reviewService;

    @GetMapping("/review/product/{productId}")
    public ResponseEntity<?> getAllReviewByProduct(@PathVariable("productId") Integer productId) {
        return ResponseEntity.ok(reviewService.getAllReviewByProduct(productId));
    }

    @GetMapping("/review/user/{userId}")
    public ResponseEntity<?> getAllReviewByUser(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(reviewService.getAllReviewByUser(userId));
    }

    @PostMapping("/review")
    public ResponseEntity<?> postReview(@RequestBody ReviewDto reviewDto) {
        return ResponseEntity.ok(reviewService.saveReview(reviewDto));
    }

    @PostMapping("/review/edit")
    public ResponseEntity<?> editReview(@RequestBody ReviewDto reviewDto) {
        return ResponseEntity.ok(reviewService.saveReview(reviewDto));
    }
}

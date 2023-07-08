package com.criscode.review.service;

import com.criscode.review.dto.ReviewDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IReviewService {
    ReviewDto saveReview(ReviewDto reviewDto);

    List<ReviewDto> getAllReviewByUser(Integer userId);

    List<ReviewDto> getAllReviewByProduct(Integer productId);
}

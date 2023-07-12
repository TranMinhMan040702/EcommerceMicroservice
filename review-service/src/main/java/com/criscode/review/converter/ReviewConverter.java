package com.criscode.review.converter;

import com.criscode.clients.user.UserClient;
import com.criscode.review.dto.ReviewDto;
import com.criscode.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReviewConverter {

    private final UserClient userClient;
    public Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        BeanUtils.copyProperties(reviewDto, review);
        return review;
    }

    public ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        BeanUtils.copyProperties(review, reviewDto);
        reviewDto.setUser(userClient.getUser(review.getUserId()));
        return reviewDto;
    }

    public List<ReviewDto> mapToDto(List<Review> reviews) {
        return reviews.stream().map(this::mapToDto).collect(Collectors.toList());
    }
}

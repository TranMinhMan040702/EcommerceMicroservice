package com.criscode.review.service;

import com.criscode.clients.order.OrderClient;
import com.criscode.clients.product.ProductClient;
import com.criscode.exceptionutils.NotFoundException;
import com.criscode.review.converter.ReviewConverter;
import com.criscode.review.dto.ReviewDto;
import com.criscode.review.entity.Review;
import com.criscode.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final String STATUS_ORDER = "DELIVERED";
    private final OrderClient orderClient;
    private final ProductClient productClient;
    private final ReviewConverter reviewConverter;
    private final ReviewRepository reviewRepository;

    public ReviewDto saveReview(ReviewDto reviewDto) {

        Review review = new Review();
        String status = orderClient.getStatusOrder(reviewDto.getOrderId());

        if (status != STATUS_ORDER) {
            reviewDto.setApproved(false);
            return reviewDto;
        } else {
            if (reviewDto.getId() == null) {
                Review reviewCheck = reviewRepository.findByOrderIdAndProductId(
                        reviewDto.getOrderId(), reviewDto.getProductId()
                );
                if (reviewCheck != null) {
                    reviewDto.setApproved(false);
                    return reviewDto;
                }
                review = reviewConverter.mapToEntity(reviewDto);
            } else {
                review = reviewRepository.findById(reviewDto.getId()).orElseThrow(
                        () -> new NotFoundException("Review does exist with id: " + reviewDto.getId())
                );
                review.setContent(reviewDto.getContent());
                review.setRating(reviewDto.getRating());
            }
            review = reviewRepository.save(review);
            updateRatingProduct(reviewDto.getProductId());
            ReviewDto resp = reviewConverter.mapToDto(review);
            resp.setApproved(true);
            return resp;
        }
    }

    /**
     * @param productId
     */
    private void updateRatingProduct(Integer productId) {
        List<Review> reviews = reviewRepository.findAllByProductId(productId);
        Integer rating = 0;
        for (Review review : reviews) {
            rating = rating + review.getRating();
        }
        productClient.updateRating(productId, rating/reviews.size());
    }

    /**
     * @param userId
     * @return
     */
    public List<ReviewDto> getAllReviewByUser(Integer userId) {
        List<Review> reviews = reviewRepository.findAllByUserId(userId);
        return reviewConverter.mapToDto(reviews);
    }

    /**
     * @param productId
     * @return
     */
    public List<ReviewDto> getAllReviewByProduct(Integer productId) {
        List<Review> reviews = reviewRepository.findAllByProductId(productId);
        return reviewConverter.mapToDto(reviews);
    }
}

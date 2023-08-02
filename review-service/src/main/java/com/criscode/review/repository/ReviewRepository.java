package com.criscode.review.repository;

import com.criscode.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Review findByOrderIdAndProductId(Integer orderId, Integer productId);

    List<Review> findAllByProductId(Integer productId);

    List<Review> findAllByUserId(Integer userId);

    Long countByRating(Integer rating);
}

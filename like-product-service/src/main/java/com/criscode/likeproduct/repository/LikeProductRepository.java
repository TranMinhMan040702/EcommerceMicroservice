package com.criscode.likeproduct.repository;

import com.criscode.likeproduct.entity.LikeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeProductRepository extends JpaRepository<LikeProduct, Integer> {
    LikeProduct findByUserIdAndProductId(Integer userId, Integer productId);
    List<LikeProduct> findByUserId(Integer userId);
}

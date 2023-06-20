package com.criscode.likeproduct.repository;

import com.criscode.likeproduct.entity.LikeProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeProductRepository extends JpaRepository<LikeProduct, Integer> {
}

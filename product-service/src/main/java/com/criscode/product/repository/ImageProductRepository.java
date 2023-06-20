package com.criscode.product.repository;

import com.criscode.product.entity.ImageProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageProductRepository extends JpaRepository<ImageProduct, Integer> {
}

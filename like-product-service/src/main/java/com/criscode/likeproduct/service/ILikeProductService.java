package com.criscode.likeproduct.service;

import com.criscode.clients.likeproduct.dto.LikeProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ILikeProductService {
    LikeProductDto likeProduct(Integer userId, Integer productId);

    LikeProductDto unLikeProduct(Integer userId, Integer productId);

    LikeProductDto findLikeProductByUser(Integer userId);

    List<LikeProductDto> findAllLikeProduct();
}

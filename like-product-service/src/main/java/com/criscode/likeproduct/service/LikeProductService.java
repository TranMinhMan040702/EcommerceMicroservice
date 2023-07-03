package com.criscode.likeproduct.service;

import com.criscode.clients.likeproduct.dto.LikeProductDto;
import com.criscode.clients.product.ProductClient;
import com.criscode.clients.product.dto.ProductDto;
import com.criscode.clients.product.dto.ProductExistResponse;
import com.criscode.clients.user.UserClient;
import com.criscode.clients.user.dto.UserDto;
import com.criscode.clients.user.dto.UserExistResponse;
import com.criscode.exceptionutils.NotFoundException;
import com.criscode.likeproduct.entity.LikeProduct;
import com.criscode.likeproduct.repository.LikeProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeProductService {

    private final UserClient userClient;
    private final ProductClient productClient;
    private final LikeProductRepository likeProductRepository;

    /**
     * @param userId
     * @param productId
     * @return
     */
    public LikeProductDto likeProduct(Integer userId, Integer productId) {

        LikeProduct likeProduct = likeProductRepository.findByUserIdAndProductId(userId, productId);
        if (likeProduct != null) {
            // todo: return LikeProductDto
            LikeProductDto likeProductDto = findLikeProductByUser(userId);
            likeProductDto.setExist(true);
            return likeProductDto;
        }

        checkExisted(userId, productId);

        LikeProduct product = new LikeProduct();
        product.setUserId(userId);
        product.setProductId(productId);

        likeProductRepository.save(product);
        LikeProductDto likeProductDto = findLikeProductByUser(userId);
        likeProductDto.setExist(false);
        return likeProductDto;
    }

    /**
     * @param userId
     * @param productId
     * @return
     */
    public LikeProductDto unLikeProduct(Integer userId, Integer productId) {
        checkExisted(userId, productId);
        likeProductRepository.delete(likeProductRepository.findByUserIdAndProductId(userId, productId));
        return findLikeProductByUser(userId);
    }

    /**
     * @param userId
     * @param productId
     */
    private void checkExisted(Integer userId, Integer productId) {
        UserExistResponse userExistResponse = userClient.existed(userId);
        ProductExistResponse productExistResponse = productClient.existed(productId);
        if (!userExistResponse.existed()) {
            throw new NotFoundException("User does exist with id: " + userId);
        }
        if (!productExistResponse.existed()) {
            throw new NotFoundException("Product does exist with id: " + productId);
        }
    }

    /**
     * @param userId
     * @return
     */
    public LikeProductDto findLikeProductByUser(Integer userId) {

        UserExistResponse userExistResponse = userClient.existed(userId);
        List<LikeProduct> likeProducts = likeProductRepository.findByUserId(userId);
        if (!userExistResponse.existed()) {
            throw new NotFoundException("User does exist with id: " + userId);
        }

        String[] productIds = likeProducts.stream()
                .map(likeProduct -> likeProduct.getProductId().toString()).toList().toArray(new String[0]);

        return LikeProductDto.builder()
                .userId(userId)
                .exist(false)
                .products(productIds.length != 0 ? productClient.getAllProductLiked(productIds) : null)
                .build();
    }

    /**
     * @return
     */
    public List<LikeProductDto> findAllLikeProduct() {
        List<UserDto> userDtos = userClient.findAll();
        if (userDtos == null) {
            return null;
        }
        return userDtos.stream().map(
                userDto -> findLikeProductByUser(userDto.getId())).collect(Collectors.toList()
        );
    }
}

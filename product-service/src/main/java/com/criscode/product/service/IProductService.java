package com.criscode.product.service;

import com.criscode.clients.order.dto.OrderItemDto;
import com.criscode.clients.product.dto.ProductDto;
import com.criscode.clients.product.dto.ProductExistResponse;
import com.criscode.product.dto.ProductPaging;
import com.criscode.product.entity.ImageProduct;
import com.criscode.product.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public interface IProductService {
    ProductDto save(ProductDto productDto, MultipartFile[] files);

    ImageProduct saveCloudinary(MultipartFile file, Product product);

    ProductPaging findAll(Long categoryId, Integer page, Integer limit, String sortBy,
                          Double priceMin, Double priceMax, String search);

    ProductDto findById(Integer id);

    List<ProductDto> findByCategory(
            Long categoryId, String search, Double priceMin, Double priceMax);

    Map<String, String> delete(Integer id);

    void updateQuantityAndSoldProduct(List<OrderItemDto> orderItemDtos);

    Integer quantityOfProduct(Integer productId);

    ProductExistResponse existed(Integer productId);

    List<ProductDto> getAllProductLiked(String[] ids);

    void updateRatingProduct(Integer productId, Integer rating);

    long totalProduct();
}

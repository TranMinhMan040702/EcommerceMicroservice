package com.criscode.clients.product;

import com.criscode.clients.order.dto.OrderItemDto;
import com.criscode.clients.product.dto.ProductDto;
import com.criscode.clients.product.dto.ProductExistResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product")
public interface ProductClient {

    @GetMapping("/api/v1/product")
    ProductDto getProductById(@RequestParam("id") Integer id);
    @PostMapping("/api/v1/product/update-quantity-sold")
    void updateQuantityAndSoldProduct(@RequestBody List<OrderItemDto> orderItemDtos);
    @GetMapping("/api/v1/product/check-quantity/{productId}")
    Integer checkQuantityOfProduct(@PathVariable("productId") Integer productId);
    @GetMapping("/api/v1/product/check-exist/{productId}")
    ProductExistResponse existed(@PathVariable("productId") Integer productId);
    @GetMapping("/api/v1/product/get-product-like/{ids}")
    List<ProductDto> getAllProductLiked(@PathVariable("ids") String[] ids);
}

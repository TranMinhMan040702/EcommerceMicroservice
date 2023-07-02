package com.criscode.clients.product;

import com.criscode.clients.order.dto.OrderItemDto;
import com.criscode.clients.product.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product")
public interface ProductClient {

    @GetMapping("/api/v1/product")
    ProductDto getProductById(@RequestParam("id") Integer id);
    @PostMapping("/product/update-quantity-sold")
    void updateQuantityAndSoldProduct(@RequestBody List<OrderItemDto> orderItemDtos);
    @GetMapping("/product/check-quantity/{productId}")
    Integer checkQuantityOfProduct(@PathVariable("productId") Integer productId);
}

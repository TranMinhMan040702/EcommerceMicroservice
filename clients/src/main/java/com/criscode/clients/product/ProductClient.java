package com.criscode.clients.product;

import com.criscode.clients.product.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product")
public interface ProductClient {

    @GetMapping("/api/v1/product")
    ProductDto getProductById(@RequestParam("id") Integer id);
}

package com.criscode.product.controller;

import com.criscode.clients.product.dto.ProductDto;
import com.criscode.exceptionutils.NotFoundException;
import com.criscode.product.constants.ApplicationConstants;
import com.criscode.product.repository.ProductRepository;
import com.criscode.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ObjectMapper objectMapper;
    private final ProductService productService;
    private final ProductRepository productRepository;

    @GetMapping("product")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProductById(@RequestParam("id") Integer id){
        return productService.findById(id);
    }

    @GetMapping("products")
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = ApplicationConstants.DEFAULT_LIMIT_SIZE_PAGE) Integer limit,
            @RequestParam(required = false, defaultValue = ApplicationConstants.DEFAULT_LIMIT_SORT_BY) String sortBy,
            @RequestParam(required = false) Double priceMin,
            @RequestParam(required = false) Double priceMax,
            @RequestParam(required = false) String search) {

        return ResponseEntity.ok(
                productService.findAll(categoryId, page, limit, sortBy, priceMin, priceMax, search)
        );
    }

    @GetMapping("products/category/{id}")
    public ResponseEntity<?> getProductByCategory(
            @PathVariable Long id,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Double priceMin,
            @RequestParam(required = false) Double priceMax) {

        return ResponseEntity.ok(productService.findByCategory(id, search, priceMin, priceMax));
    }

    @PostMapping("/admin/products")
    public ResponseEntity<?> createProduct(
            @RequestParam("model") String JsonObject,
            @RequestParam("files") MultipartFile[] files) throws Exception {

        ProductDto product = objectMapper.readValue(JsonObject, ProductDto.class);
        return ResponseEntity.ok(productService.save(product, files));
    }

    @PostMapping("/admin/products/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Integer id,
            @RequestParam("model") String JsonObject, @RequestParam("files") MultipartFile[] files)
            throws JsonProcessingException {
        productRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Product not existed with id:" + id)
        );
        ProductDto product = objectMapper.readValue(JsonObject, ProductDto.class);
        return ResponseEntity.ok(productService.save(product, files));
    }

    @DeleteMapping("/admin/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.delete(id));
    }

}

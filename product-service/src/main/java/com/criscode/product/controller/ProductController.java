package com.criscode.product.controller;

import com.criscode.clients.order.dto.OrderItemDto;
import com.criscode.clients.product.dto.ProductDto;
import com.criscode.clients.product.dto.ProductExistResponse;
import com.criscode.exceptionutils.NotFoundException;
import com.criscode.product.constants.ApplicationConstants;
import com.criscode.product.repository.ProductRepository;
import com.criscode.product.service.IProductService;
import com.criscode.product.service.impl.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin({ "https://thunderous-basbousa-75b1ca.netlify.app/", "http://localhost:3000/" })
@RequiredArgsConstructor
public class ProductController {

    private final ObjectMapper objectMapper;
    private final IProductService productService;
    private final ProductRepository productRepository;

    @GetMapping("/product")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProductById(@RequestParam("id") Integer id){
        return productService.findById(id);
    }

    @GetMapping("/products")
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

    @GetMapping("/products/category/{id}")
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

    @PostMapping("/product/update-quantity-sold")
    public void updateQuantityAndSoldProduct(@RequestBody List<OrderItemDto> orderItemDtos) {
        productService.updateQuantityAndSoldProduct(orderItemDtos);
    }

    @GetMapping("/product/check-quantity/{productId}")
    public Integer checkQuantityOfProduct(@PathVariable("productId") Integer productId) {
        return productService.quantityOfProduct(productId);
    }

    @GetMapping("/product/check-exist/{productId}")
    public ProductExistResponse existed(@PathVariable("productId") Integer productId) {
        return productService.existed(productId);
    }

    @GetMapping("/product/get-product-like/{ids}")
    public List<ProductDto> getAllProductLiked(@PathVariable("ids") String[] ids) {
        return productService.getAllProductLiked(ids);
    }

    @PostMapping("/product/update-rating")
    public void updateRating(
            @RequestParam("productId") Integer productId, @RequestParam("rating") Integer rating) {
        productService.updateRatingProduct(productId, rating);
    }

}

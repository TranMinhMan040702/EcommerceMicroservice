package com.criscode.likeproduct.controller;

import com.criscode.likeproduct.service.ILikeProductService;
import com.criscode.likeproduct.service.impl.LikeProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/like-product-service/")
@CrossOrigin({ "https://thunderous-basbousa-75b1ca.netlify.app/", "http://localhost:3000/" })
@RequiredArgsConstructor
public class LikeProductController {

    private final ILikeProductService likeProductService;

    @GetMapping("users/admin/follow-product")
    public ResponseEntity<?> getAllLikeProduct() {
        return ResponseEntity.ok(likeProductService.findAllLikeProduct());
    }

    @GetMapping("users/follow-product")
    public ResponseEntity<?> getLikeProductByUser(@RequestParam("userId") Integer userId) {
        return ResponseEntity.ok(likeProductService.findLikeProductByUser(userId));
    }

    @PostMapping("users/follow-product")
    public ResponseEntity<?> likeProduct(
            @RequestParam("userId") Integer userId, @RequestParam("productId") Integer productId) {
        return ResponseEntity.ok(likeProductService.likeProduct(userId, productId));
    }

    @PutMapping("users/follow-product")
    public ResponseEntity<?> unLikeProduct(
            @RequestParam("userId") Integer userId, @RequestParam("productId") Integer productId) {
        return ResponseEntity.ok(likeProductService.unLikeProduct(userId, productId));
    }
}

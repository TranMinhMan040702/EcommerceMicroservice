package com.criscode.cart.controller;

import com.criscode.cart.service.CartService;
import com.criscode.clients.cart.dto.CartDto;
import com.criscode.clients.cart.dto.CartItemDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("cart/user/{id}")
    public ResponseEntity<?> findCartByUser(@PathVariable Integer id) {
        return ResponseEntity.ok(cartService.findCartByUser(id));
    }

    @PostMapping("create-cart")
    @ResponseStatus(HttpStatus.OK)
    public CartDto save(@RequestBody @Valid CartDto cartDto) {
        return cartService.save(cartDto);
    }

    @PostMapping("cart")
    public ResponseEntity<?> addToCart(@RequestBody CartItemDto cartItemDto){
        return ResponseEntity.ok(cartService.addToCart(cartItemDto));
    }

    @PutMapping("cart/deleteOne")
    public ResponseEntity<?> deleteOneProductInCart(@RequestParam("cartItemId") Integer cartItemId) {
        return ResponseEntity.ok(cartService.deleteOneItemInCartItem(cartItemId));
    }

    @DeleteMapping("cart/deleteAll")
    public ResponseEntity<?> deleteAllProductInCart(@RequestParam("cartItemId") Integer cartItemId) {
        return ResponseEntity.ok(cartService.deleteAllItemInCartItem(cartItemId));
    }

}

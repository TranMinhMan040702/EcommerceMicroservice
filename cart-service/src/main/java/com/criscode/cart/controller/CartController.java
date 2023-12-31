package com.criscode.cart.controller;

import com.criscode.cart.service.ICartService;
import com.criscode.clients.cart.dto.CartDto;
import com.criscode.clients.cart.dto.CartItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/cart-service/cart")
@CrossOrigin({"https://thunderous-basbousa-75b1ca.netlify.app/", "http://localhost:3000/"})
@RequiredArgsConstructor
public class CartController {

    private final ICartService cartService;

    @GetMapping("/user/{id}")
    public CartDto findCartByUser(@PathVariable("id") Integer id) {
        return cartService.findCartByUser(id);
    }

    @PostMapping("/create-cart")
    @ResponseStatus(HttpStatus.OK)
    public CartDto save(@RequestBody @Valid CartDto cartDto) {
        return cartService.save(cartDto);
    }

    @PostMapping
    public ResponseEntity<?> addToCart(@RequestBody CartItemDto cartItemDto) {
        return ResponseEntity.ok(cartService.addToCart(cartItemDto));
    }

    @PutMapping("/deleteOne")
    public ResponseEntity<?> deleteOneProductInCart(@RequestParam("cartItemId") Integer cartItemId) {
        return ResponseEntity.ok(cartService.deleteOneItemInCartItem(cartItemId));
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllProductInCart(@RequestParam("cartItemId") Integer cartItemId) {
        return ResponseEntity.ok(cartService.deleteAllItemInCartItem(cartItemId));
    }

    @PostMapping("/clear-cart/{cartId}")
    public void clearedCart(@PathVariable("cartId") Integer cartId) {
        cartService.clearedCart(cartId);
    }

}

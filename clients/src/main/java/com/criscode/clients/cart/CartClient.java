package com.criscode.clients.cart;

import com.criscode.clients.cart.dto.CartDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = "cart")
public interface CartClient {
    @PostMapping("/api/v1/create-cart")
    CartDto save(@RequestBody @Valid CartDto cartDto);
    @PostMapping("/clear-cart/{cartId}")
    void clearedCart(@PathVariable("cartId") Integer cartId);
    @GetMapping("cart/user/{id}")
    ResponseEntity<CartDto> findCartByUser(@PathVariable("id") Integer id);

}

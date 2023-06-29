package com.criscode.cart.controller;

import com.criscode.cart.service.CartService;
import com.criscode.clients.cart.dto.CartDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart")
    @ResponseStatus(HttpStatus.OK)
    public CartDto save (
            @RequestBody
            @Valid CartDto cartDto) {
        return cartService.save(cartDto);
    }

}

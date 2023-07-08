package com.criscode.cart.service;

import com.criscode.clients.cart.dto.CartDto;
import com.criscode.clients.cart.dto.CartItemDto;
import org.springframework.stereotype.Service;

@Service
public interface ICartService {
    CartDto save(CartDto cartDto);

    CartDto findCartByUser(Integer userId);

    CartDto addToCart(CartItemDto cartItemDto);

    CartDto deleteOneItemInCartItem(Integer cartItemId);

    CartDto deleteAllItemInCartItem(Integer cartItemId);

    void clearedCart(Integer cartId);
}

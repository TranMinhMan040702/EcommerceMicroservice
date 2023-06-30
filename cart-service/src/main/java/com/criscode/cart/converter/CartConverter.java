package com.criscode.cart.converter;

import com.criscode.cart.enitty.Cart;
import com.criscode.clients.cart.dto.CartDto;
import org.springframework.stereotype.Component;

@Component
public class CartConverter {

    public CartDto map(Cart cart) {
        return CartDto.builder()
                .cartId(cart.getId())
                .userId(cart.getUserId())
                .build();
    }

    public Cart map(CartDto cartDto) {
        return Cart.builder()
                .userId(cartDto.getUserId())
                .build();
    }

}

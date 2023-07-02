package com.criscode.cart.converter;

import com.criscode.cart.enitty.Cart;
import com.criscode.clients.cart.dto.CartDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CartConverter {

    private final CartItemConverter cartItemConverter;

    public CartDto map(Cart cart) {
        CartDto cartDto = new CartDto();
        BeanUtils.copyProperties(cart, cartDto);
        if (cart.getCartItems() != null){
            cartDto.setCartItemDtos(cartItemConverter.map(cart.getCartItems()));
        }
        return cartDto;
    }

    public Cart map(CartDto cartDto) {
        return Cart.builder()
                .userId(cartDto.getUserId())
                .build();
    }

}

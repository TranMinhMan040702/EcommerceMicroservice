package com.criscode.cart.converter;

import com.criscode.cart.enitty.Cart;
import com.criscode.cart.enitty.CartItem;
import com.criscode.cart.repository.CartRepository;
import com.criscode.clients.cart.dto.CartItemDto;
import com.criscode.clients.product.ProductClient;
import com.criscode.exceptionutils.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartItemConverter {

//    private final ProductClient productClient;
    private final CartRepository cartRepository;

    public CartItemDto map(CartItem cartItem) {
        CartItemDto cartItemDto = new CartItemDto();
        BeanUtils.copyProperties(cartItem, cartItemDto);
        cartItemDto.setCartId(cartItem.getCart().getId());
//        cartItemDto.setProductDto(productClient.getProductById(cartItem.getProductId()).getBody());
        return cartItemDto;
    }

    public List<CartItemDto> map(List<CartItem> cartItems) {
        return cartItems.stream().map(this::map).collect(Collectors.toList());
    }

    public CartItem map(CartItemDto cartItemDto) {
        CartItem cartItem = new CartItem();
        BeanUtils.copyProperties(cartItemDto, cartItem);
        Cart cart = cartRepository.findById(cartItemDto.getCartId()).orElseThrow(
                () -> new NotFoundException("Cart not exist with id: " + cartItemDto.getCartId())
        );
        cartItem.setCart(cart);
        cartItem.setProductId(cartItemDto.getProductDto().getId());
        return cartItem;
    }

}

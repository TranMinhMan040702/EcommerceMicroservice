package com.criscode.cart.service;

import com.criscode.cart.converter.CartConverter;
import com.criscode.cart.converter.CartItemConverter;
import com.criscode.cart.enitty.Cart;
import com.criscode.cart.enitty.CartItem;
import com.criscode.cart.repository.CartItemRepository;
import com.criscode.cart.repository.CartRepository;
import com.criscode.clients.cart.CartClient;
import com.criscode.clients.cart.dto.CartDto;
import com.criscode.clients.cart.dto.CartItemDto;
import com.criscode.clients.user.UserClient;
import com.criscode.clients.user.dto.UserExistResponse;
import com.criscode.exceptionutils.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartConverter cartConverter;
    private final CartItemConverter cartItemConverter;
    private final UserClient userClient;

    /**
     * @param cartDto
     * @return
     */
    public CartDto save(CartDto cartDto) {
        log.info("*** CartDto, service; save cart *");
        return cartConverter.map(cartRepository.save(cartConverter.map(cartDto)));
    }

    /**
     * @param userId
     * @return
     */
//    public CartDto findCartByUser(Integer userId) {
//        UserExistResponse userExistResponse = userClient.existed(userId);
//        if (!userExistResponse.existed()) {
//            throw new NotFoundException("User is not exist with id:" + userId);
//        }
//        return cartConverter.map(cartRepository.findByUserId(userId));
//    }

    /**
     * @param cartItemDto
     * @return
     */
    public CartDto addToCart(CartItemDto cartItemDto) {
        Optional<Cart> cart = cartRepository.findById(cartItemDto.getCartId());
        List<CartItem> cartItems = cart.get().getCartItems();
        boolean hasInCart = false;
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProductId() == cartItemDto.getProductDto().getId()) {
                cartItem.setCount(cartItem.getCount() + cartItemDto.getCount());
                cartItemRepository.save(cartItem);
                hasInCart = true;
                break;
            }
        }
        if (!hasInCart) {
            cart.get().getCartItems().add(cartItemConverter.map(cartItemDto));
            cartRepository.save(cart.get());
        }
        return cartConverter.map(cart.get());
    }

    /**
     * @param cartItemId
     * @return
     */
    public CartDto deleteOneItemInCartItem(Integer cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new NotFoundException("CartItem is not exist with id: " + cartItemId)
        );
        Optional<Cart> cart = cartRepository.findById(cartItem.getCart().getId());
        cartItem.setCount(cartItem.getCount() - 1);
        cartRepository.save(cart.get());
        return cartConverter.map(cart.get());
    }

    /**
     * @param cartItemId
     * @return
     */
    public CartDto deleteAllItemInCartItem(Integer cartItemId) {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        Optional<Cart> cart = cartRepository.findById(cartItem.get().getCart().getId());
        cart.get().getCartItems().removeIf(item -> item.getId().equals(cartItemId));
        cartRepository.save(cart.get());
        return cartConverter.map(cart.get());
    }

    /**
     * @param cartId
     */
    public void clearedCart(Integer cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        cart.get().getCartItems().clear();
        cartRepository.save(cart.get());
    }

}

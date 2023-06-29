package com.criscode.cart.service;

import com.criscode.cart.converter.CartConverter;
import com.criscode.cart.repository.CartRepository;
import com.criscode.clients.cart.dto.CartDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final CartConverter cartConverter;

    public CartDto save(CartDto cartDto) {
        log.info("*** CartDto, service; save cart *");
        return cartConverter.map(cartRepository.save(cartConverter.map(cartDto)));
    }

}

package com.criscode.clients.cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class CartDto {

    private Integer id;

    private Integer userId;

    @JsonProperty("cartItems")
    private List<CartItemDto> cartItemDtos;

}

package com.criscode.clients.cart.dto;

import com.criscode.clients.product.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {

    private Integer id;

    private Integer cartId;

    private Integer count;

    @JsonProperty("product")
    private ProductDto productDto;
}

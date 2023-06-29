package com.criscode.clients.cart.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class CartDto {

    private Integer cartId;
    private Integer userId;

    @JsonProperty("user")
    private UserDto userDto;

}

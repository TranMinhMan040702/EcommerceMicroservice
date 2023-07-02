package com.criscode.clients.user.dto;

import com.criscode.clients.cart.dto.CartDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class UserDto {

    private Integer userId;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String gender;

    private Date birthday;

    private String avatar;

    @JsonProperty("cart")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CartDto cartDto;
}

package com.criscode.clients.user.dto;

import com.criscode.clients.cart.dto.CartDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class UserDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String gender;

    private String birthday;

    private Set<String> roles;

    private String avatar;

    private Integer cartId;
    private List<AddressDto> addresses;

//    @JsonProperty("cart")
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private CartDto cartDto;
}

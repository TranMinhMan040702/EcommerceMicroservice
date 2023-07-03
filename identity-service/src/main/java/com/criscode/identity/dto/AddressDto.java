package com.criscode.identity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AddressDto {

    private Integer id;
    @NotBlank
    private String username;

    @NotBlank
    private String phone;

    @NotBlank
    private String ward;

    @NotBlank
    private String district;

    @NotBlank
    private String province;

    @NotBlank
    private String street;

    @NotBlank
    private Boolean status;

    @NotBlank
    private Integer userId;

}

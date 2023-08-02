package com.criscode.product.dto;

import com.criscode.clients.product.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CategoryDto {

    private Integer id;

    @NotBlank
    private String name;

    //    @NotBlank
    private String image;

    private boolean isDeleted = false;

    @JsonProperty("products")
    private List<ProductDto> productDtos;

}

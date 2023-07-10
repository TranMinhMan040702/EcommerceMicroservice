package com.criscode.clients.order.dto;

import com.criscode.clients.product.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderItemDto {

    private Integer id;
    @NotBlank
    private Integer count;
    @NotBlank
    private Integer orderId;
    @NotBlank
    private Integer productId;
    @JsonProperty("product")
    private ProductDto productDto;
    private Boolean rating;

}

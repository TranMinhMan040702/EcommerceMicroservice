package com.criscode.clients.order.dto;

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
    private Boolean rating;

}

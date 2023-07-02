package com.criscode.order.dto;

import com.criscode.clients.order.dto.OrderItemDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderDto {

    private Integer id;

    @NotBlank
    private String address;

    @NotBlank
    private String phone;

    private String status;

    private Boolean isPaidBefore = false;

    private Double amountFromUser;

    @NotBlank
    private Integer userId;

    @JsonProperty("orderItems")
    private List<OrderItemDto> orderItemDtos;

    @JsonProperty("delivery")
    private DeliveryDto deliveryDto;

}

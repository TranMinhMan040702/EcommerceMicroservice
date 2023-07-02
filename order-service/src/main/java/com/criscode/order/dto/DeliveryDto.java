package com.criscode.order.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class DeliveryDto {

    private Integer id;

    @NotBlank
    private String name;

    @Min(0)
    @NotNull
    private Double price;

    @NotBlank
    private String description;

    private boolean isDeleted = false;
}

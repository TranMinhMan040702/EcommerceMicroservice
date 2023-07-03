package com.criscode.clients.product.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ProductDto {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    @Min(0)
    private Double price;

    @NotNull
    @Min(0)
    private Double promotionalPrice;

    @NotNull
    @Min(0)
    private Integer quantity;

    private Integer sold;

    private Integer rating;

    @NotNull
    private Integer category;

    private List<String> images;

}

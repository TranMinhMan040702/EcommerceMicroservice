package com.criscode.product.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
    private double price;

    @NotNull
    @Min(0)
    private double promotionalPrice;

    @NotNull
    @Min(0)
    private int quantity;

    private int sold;

    private int rating;

    @NotNull
    private long category;

    private List<String> images;

}

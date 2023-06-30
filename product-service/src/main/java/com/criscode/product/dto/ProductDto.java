package com.criscode.product.dto;

import com.criscode.common.dto.AbstractDto;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ProductDto extends AbstractDto {

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

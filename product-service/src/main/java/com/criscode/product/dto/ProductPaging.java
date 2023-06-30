package com.criscode.product.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPaging {
    private Integer page;
    private Integer limit;
    private Integer totalPage;
    private List<ProductDto> products;
}

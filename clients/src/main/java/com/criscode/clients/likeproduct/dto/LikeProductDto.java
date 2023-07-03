package com.criscode.clients.likeproduct.dto;

import com.criscode.clients.product.dto.ProductDto;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class LikeProductDto {
    private Integer id;
    private Integer userId;
    private Boolean exist = false;
    private List<ProductDto> products;
}

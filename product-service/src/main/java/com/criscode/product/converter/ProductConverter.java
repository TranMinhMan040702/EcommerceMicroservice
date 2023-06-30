package com.criscode.product.converter;

import com.criscode.product.dto.ProductDto;
import com.criscode.product.dto.ProductPaging;
import com.criscode.product.entity.ImageProduct;
import com.criscode.product.entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ProductConverter {

    private final ObjectMapper objectMapper;

    public Product map(ProductDto productDto) {
        Product product = objectMapper.convertValue(productDto, Product.class);
        return product;
    }

    public ProductDto map(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        productDto.setImages(product.getImages().stream()
                .map(ImageProduct::getPath).collect(Collectors.toList()));
        productDto.setCategory(product.getCategory().getId());
        return productDto;
    }

    public List<ProductDto> map(List<Product> products) {
        return products.stream().map(this::map).collect(Collectors.toList());
    }

    public ProductPaging map(Page<Product> products) {
        return ProductPaging.builder()
                .page(products.getPageable().getPageNumber())
                .limit(products.getPageable().getPageSize())
                .totalPage(products.getTotalPages())
                .products(products.stream().map(this::map).collect(Collectors.toList()))
                .build();
    }

}

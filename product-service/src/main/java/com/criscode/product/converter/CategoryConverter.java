package com.criscode.product.converter;

import com.criscode.product.dto.CategoryDto;
import com.criscode.product.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    public CategoryDto map(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .image(category.getImage())
                .isDeleted(category.isDeleted())
                .build();
    }

    public Category map(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        category.setImage(categoryDto.getImage());
        return category;
    }

}

package com.criscode.product.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.criscode.exceptionutils.AlreadyExistsException;
import com.criscode.exceptionutils.NotFoundException;
import com.criscode.product.converter.CategoryConverter;
import com.criscode.product.dto.CategoryDto;
import com.criscode.product.entity.Category;
import com.criscode.product.repository.CategoryRepository;
import com.criscode.product.service.ICategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final Cloudinary cloudinary;
    private CategoryConverter categoryConverter;

    @Override
    public CategoryDto save(CategoryDto categoryDto, MultipartFile file) {

        Optional<Category> c = categoryRepository.findByName(categoryDto.getName());
        if (c.isPresent()) {
            throw new AlreadyExistsException("Category name is exist !!");
        }
        // todo: converter into entity
        Category category = categoryConverter.map(categoryDto);

        if (categoryDto.getId() != null) {
            // todo: update category
            String imageOld = category.getImage();
            if (file != null
                    && (imageOld == null || !imageOld.equals(file.getOriginalFilename()))) {
                category.setImage(saveImageCloudinary(file));
            }
        } else {
            // todo: create new category
            if (file != null) {
                category.setImage(saveImageCloudinary(file));
            }
        }
        return categoryConverter.map(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDto> findAll() {
        List<Category> categoryDtos = categoryRepository.findAll();
        return categoryDtos.stream().map(
                categoryDto -> categoryConverter.map(categoryDto)
        ).collect(Collectors.toList());
    }

    @Override
    public Map<String, String> deleteOne(Integer id) {
        Optional<Category> category = Optional.ofNullable(categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not exist with id: " + id)));
        categoryRepository.delete(category.get());
        return (Map<String, String>) new HashMap<>().put("Delete", "Success");
    }

    // todo: delete soft

    @Override
    public String saveImageCloudinary(MultipartFile file) {
        Map<?, ?> r;
        try {
            r = this.cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            return (String) r.get("secure_url");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}

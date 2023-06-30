package com.criscode.product.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.criscode.exceptionutils.AlreadyExistsException;
import com.criscode.exceptionutils.NotFoundException;
import com.criscode.product.converter.CategoryConverter;
import com.criscode.product.dto.CategoryDto;
import com.criscode.product.entity.Category;
import com.criscode.product.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final Cloudinary cloudinary;
    private CategoryConverter categoryConverter;

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

    public List<CategoryDto> findAll() {
        List<Category> categoryDtos = categoryRepository.findAll();
        return categoryDtos.stream().map(
                categoryDto -> categoryConverter.map(categoryDto)
        ).collect(Collectors.toList());
    }

    public Map<String, String> deleteOne(Integer id) {
        Optional<Category> category = Optional.ofNullable(categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not exist with id: " + id)));
        categoryRepository.delete(category.get());
        return (Map<String, String>) new HashMap<>().put("Delete", "Success");
    }

    // todo: delete soft

    private String saveImageCloudinary(MultipartFile file) {
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

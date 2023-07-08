package com.criscode.product.service;

import com.criscode.product.dto.CategoryDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public interface ICategoryService {
    CategoryDto save(CategoryDto categoryDto, MultipartFile file);

    List<CategoryDto> findAll();

    Map<String, String> deleteOne(Integer id);

    String saveImageCloudinary(MultipartFile file);
}

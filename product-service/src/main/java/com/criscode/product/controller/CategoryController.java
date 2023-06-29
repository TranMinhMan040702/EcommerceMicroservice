package com.criscode.product.controller;

import com.criscode.product.dto.CategoryDto;
import com.criscode.product.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ObjectMapper objectMapper;

    @PostMapping("/admin/categorise")
    public ResponseEntity<?> save(
            @RequestParam("model") String JsonObject,
            @RequestParam(name = "file", required = false) MultipartFile file)
            throws JsonProcessingException {
        CategoryDto categoryDto =  objectMapper.readValue(JsonObject, CategoryDto.class);
        return ResponseEntity.ok(categoryService.save(categoryDto, file));
    }

    @GetMapping("/categorise")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @DeleteMapping("/admin/categorise/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.deleteOne(id));
    }

}
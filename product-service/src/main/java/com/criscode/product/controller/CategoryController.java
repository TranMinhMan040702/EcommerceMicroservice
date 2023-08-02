package com.criscode.product.controller;

import com.criscode.product.dto.CategoryDto;
import com.criscode.product.service.ICategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/product-service")
@CrossOrigin({"https://thunderous-basbousa-75b1ca.netlify.app/", "http://localhost:3000/"})
@AllArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;
    private final ObjectMapper objectMapper;

    @PostMapping("/admin/categorise")
    public ResponseEntity<?> save(
            @RequestParam("model") String JsonObject,
            @RequestParam(name = "file", required = false) MultipartFile file)
            throws JsonProcessingException {

        CategoryDto categoryDto = objectMapper.readValue(JsonObject, CategoryDto.class);
        return ResponseEntity.ok(categoryService.save(categoryDto, file));
    }

    @GetMapping("/public/categorise")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @DeleteMapping("/admin/categorise/{id}")
    public ResponseEntity<?> deleteOne(
            @PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.deleteOne(id));
    }

}

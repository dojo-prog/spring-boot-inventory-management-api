package com.doj0.inventory_management_api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doj0.inventory_management_api.dto.request.CreateCategoryRequestDto;
import com.doj0.inventory_management_api.dto.response.ApiResponse;
import com.doj0.inventory_management_api.model.Category;
import com.doj0.inventory_management_api.service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor 
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping 
    public ResponseEntity<?> getCategories(Pageable pageable) {
        Page<Category> categories = categoryService.getCategories(pageable);

        return ResponseEntity.ok(
            ApiResponse.success(
                categories
            )
        );
    }

    @GetMapping("/{categoryId}") 
    public ResponseEntity<?> getCategoryById(
        @PathVariable String categoryId
    ) {
        Category category = categoryService.getCategoryById(categoryId);

        return ResponseEntity.ok(
            ApiResponse.success(
                category
            )
        );
    }

    @PostMapping  
    public ResponseEntity<?> createCategory(
        @Valid @RequestBody CreateCategoryRequestDto requestDto
    ) {
        Category category = categoryService.createCategory(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.success(
                "Category successfully created",
                category
            )
        );
    }

    @DeleteMapping ("/{categoryId}") 
    public ResponseEntity<?> deleteCategory(
        @PathVariable String categoryId
    ) {
        categoryService.deleteCategory(categoryId);

        return ResponseEntity.ok(
            ApiResponse.message(
                "Category sucessfully deleted"
            )
        );
    }
}

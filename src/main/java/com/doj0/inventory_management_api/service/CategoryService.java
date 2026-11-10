package com.doj0.inventory_management_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.doj0.inventory_management_api.dto.request.CreateCategoryRequestDto;
import com.doj0.inventory_management_api.exception.ResourceNotFoundException;
import com.doj0.inventory_management_api.model.Category;
import com.doj0.inventory_management_api.repository.CategoryRepository;
import com.doj0.inventory_management_api.util.SlugUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Page<Category> getCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Category getCategoryById(String categoryId) {
        return categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Transactional 
    public Category createCategory(CreateCategoryRequestDto requestDto) {
        boolean existing = categoryRepository.findByName(requestDto.getName()).isPresent();

        if (existing) { 
            throw new IllegalArgumentException("This category name is already registered");
        }

        String baseSlug = SlugUtils.toSlug(requestDto.getName());
        String uniqueSlug = baseSlug;
        
        int counter = 1;
        while (categoryRepository.findBySlug(uniqueSlug).isPresent()) {
            uniqueSlug = baseSlug + "-" + counter;
            counter++;
        }

        Category category = Category.builder()
            .name(requestDto.getName())
            .slug(uniqueSlug)
            .description(requestDto.getDescription())
            .build();

        return categoryRepository.save(category);
    }

    @Transactional 
    public void deleteCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        categoryRepository.delete(category);
    }
}

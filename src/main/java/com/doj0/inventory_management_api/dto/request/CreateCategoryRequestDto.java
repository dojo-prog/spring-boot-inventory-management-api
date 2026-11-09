package com.doj0.inventory_management_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class CreateCategoryRequestDto {
    @NotBlank(message = "Category name is required")
    @Size(max = 50, message = "Category name cannot exceed 50 characters")
    private String name; 

    @Size(max = 360, message = "Category description cannot exceed 360 characters")
    private String description;
}

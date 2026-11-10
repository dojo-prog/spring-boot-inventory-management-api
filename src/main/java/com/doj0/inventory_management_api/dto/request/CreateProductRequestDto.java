package com.doj0.inventory_management_api.dto.request;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.UUID;

import com.doj0.inventory_management_api.enums.ProductStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class CreateProductRequestDto  {
    @NotBlank(message = "Category is required")
    @UUID(message = "Invalid category ID")
    private String categoryId;    

    @NotBlank(message = "Product name is required")
    @Size(min = 2, message = "Product name must be at least 2 characters long")
    @Size(max = 150, message = "Product name cannot exceed 150 characters")
    private String name;    
    
    @NotBlank(message = "Product description is required")
    @Size(min = 10, message = "Product decription must be at least 10 characters long")
    @Size(max = 700, message = "Product description cannot exceed 700 characters")
    private String description;    
    
    @NotBlank(message = "Product unit of measurment is required")
    private String unit;    
    
    @NotNull(message = "Product cost per unit is required")
    @DecimalMin(value = "0.01", message = "Unit cost must be greated than 0")
    private BigDecimal unitCost;    
    
    @NotNull(message = "Initial stock quantity is required")
    @PositiveOrZero(message = "Stock quantity cannot be negative")
    private Integer stockQuantity;    
    
    @NotNull(message = "Reorder level is required")
    @PositiveOrZero(message = "Reorder level cannot be negative")
    private Integer reorderLevel;    
    
    @NotNull(message = "Initial product status is required")
    private ProductStatus status;    
}

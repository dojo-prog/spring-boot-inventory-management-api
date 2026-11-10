package com.doj0.inventory_management_api.dto.request;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.UUID;

import com.doj0.inventory_management_api.enums.ProductStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class UpdateProductRequestDto {
    @UUID(message = "Invalid category ID")
    private String categoryId;    

    @Size(min = 2, message = "Product name must be at least 2 characters long")
    @Size(max = 150, message = "Product name cannot exceed 150 characters")
    private String name;    
    
    @Size(max = 150, message = "Product description cannot exceed 700 characters")
    private String description;    
    
    @NotBlank(message = "Product unit of measurment is required")
    private String unit;    
    
    @DecimalMin(value = "0.01", message = "Unit cost must be greated than 0")
    private BigDecimal unitCost;    
    
    @PositiveOrZero(message = "Stock quantity cannot be negative")
    private Integer stockQuantity;    
    
    @PositiveOrZero(message = "Reorder level cannot be negative")
    private Integer reorderLevel;    
    
    private ProductStatus status;
}

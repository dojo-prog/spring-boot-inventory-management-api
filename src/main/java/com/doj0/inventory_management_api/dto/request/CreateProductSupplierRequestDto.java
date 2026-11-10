package com.doj0.inventory_management_api.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class CreateProductSupplierRequestDto {
    @NotBlank(message = "Product is required")
    private String productId;
    
    @NotBlank(message = "Supplier is required")
    private String supplierId;
    
    @Size(max = 30, message = "Supplier sku cannot exceed 30 characters")
    private String supplierSku; 
    
    @DecimalMin(value = "0.01", message = "Unit cost must be greated than 0")
    private BigDecimal unitCost;
}

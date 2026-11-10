package com.doj0.inventory_management_api.dto.request;

import com.doj0.inventory_management_api.enums.StockMovementType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data 
public class UpdateProductStockRequestDto {
    @NotNull(message = "Stock movement type is required")
    private StockMovementType type;

    @Positive(message = "Quantity must be greater than 0")
    private Integer quantity;
    
    @NotBlank(message = "Stock update reason is required")
    @Size(max = 250, message = "Stock update reson cannot exceed 250 characters")
    private String reason;

    private String reference;
}

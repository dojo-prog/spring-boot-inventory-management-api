package com.doj0.inventory_management_api.dto.request;

import org.openapitools.jackson.nullable.JsonNullable;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class UpdateSupplierRequestDto {
    @Size(max = 200, message = "Supplier name cannot exceed 200 characters")
    private String name;
    
    @Size(max = 150, message = "Contact persion cannot exceed 150 characters")
    private JsonNullable<String> contactPerson;
    
    @Size(max = 150, message = "Email cannot exceed 150 characters")
    private JsonNullable<String> email;

    @Pattern (
        regexp = "^(09|\\+639|639)\\d{9}$", 
        message = "Invalid Philippine mobile number format. Use 09XXXXXXXXX or +639XXXXXXXXX"
    )
    private JsonNullable<String> phone;

    @Size(max = 150, message = "Address cannot exceed 400 characters")
    private JsonNullable<String> address;
}

package com.doj0.inventory_management_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateSupplierRequestDto {
    @NotBlank(message = "Supplier name is required (e.g. Company Name)")
    @Size(max = 200, message = "Supplier name cannot exceed 200 characters")
    private String name;
    
    @Size(max = 150, message = "Contact persion cannot exceed 150 characters")
    private String contact_person;
    
    @Size(max = 150, message = "Email cannot exceed 150 characters")
    private String email;

    @Pattern (
        regexp = "^(09|\\+639|639)\\d{9}$", 
        message = "Invalid Philippine mobile number format. Use 09XXXXXXXXX or +639XXXXXXXXX"
    )
    private String phone;

    @Size(max = 150, message = "Address cannot exceed 400 characters")
    private String address;
}

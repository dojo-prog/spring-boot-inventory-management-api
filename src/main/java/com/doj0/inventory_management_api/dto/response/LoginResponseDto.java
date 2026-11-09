package com.doj0.inventory_management_api.dto.response;

import lombok.Builder;

@Builder 
public record LoginResponseDto(
    String token, 
    UserResponseDto user
) {}

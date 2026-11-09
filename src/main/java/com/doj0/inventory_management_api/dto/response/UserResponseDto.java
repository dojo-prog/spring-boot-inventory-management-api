package com.doj0.inventory_management_api.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.doj0.inventory_management_api.enums.Role;

import lombok.Builder;

@Builder 
public record UserResponseDto(
    String id,
    String name,
    String email,
    List<Role> roles,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}

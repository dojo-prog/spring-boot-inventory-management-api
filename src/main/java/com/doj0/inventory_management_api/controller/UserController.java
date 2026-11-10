package com.doj0.inventory_management_api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doj0.inventory_management_api.dto.request.RegisterRequestDto;
import com.doj0.inventory_management_api.dto.response.ApiResponse;
import com.doj0.inventory_management_api.dto.response.UserResponseDto;
import com.doj0.inventory_management_api.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor 
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize ("hasRole('ADMIN')")
    public ResponseEntity<?> getUsers(Pageable pageable) {
        Page<UserResponseDto> users = userService.getUsers(pageable);

        return ResponseEntity.ok(
            ApiResponse.success(
                users
            )
        );
    }

    @GetMapping("/{userId}")
    @PreAuthorize ("hasRole('ADMIN')")
    public ResponseEntity<?> getUserById(
        @PathVariable String userId
    ) {
        UserResponseDto user = userService.getUserById(userId);

        return ResponseEntity.ok(
            ApiResponse.success(
                user
            )
        );
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> register(
        @Valid @RequestBody RegisterRequestDto requestDto
    ) {
        userService.register(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.message("User registered successfully")
        );
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize ("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(
        @PathVariable String userId
    ) {
        userService.deleteUser(userId);

        return ResponseEntity.ok(
            ApiResponse.message(
                "User successfully deleted"
            )
        );
    }

}

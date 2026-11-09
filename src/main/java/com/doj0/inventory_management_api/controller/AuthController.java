package com.doj0.inventory_management_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.doj0.inventory_management_api.dto.request.LoginRequestDto;
import com.doj0.inventory_management_api.dto.response.ApiResponse;
import com.doj0.inventory_management_api.dto.response.LoginResponseDto;
import com.doj0.inventory_management_api.dto.response.UserResponseDto;
import com.doj0.inventory_management_api.model.User;
import com.doj0.inventory_management_api.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor 
public class AuthController {

    private final AuthService authService;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(
        @AuthenticationPrincipal User user
    ) {
        UserResponseDto userResponseDto = authService.getCurrentUser(user);

        return ResponseEntity.ok(
            ApiResponse.success(userResponseDto)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
        @Valid @RequestBody LoginRequestDto requestDto
    ) {
        LoginResponseDto loginResponseDto = authService.login(requestDto);

        return ResponseEntity.ok(
            ApiResponse.success(
                "Login Successful",
                loginResponseDto
            )
        );
    }
    
}

package com.doj0.inventory_management_api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.doj0.inventory_management_api.dto.request.RegisterRequestDto;
import com.doj0.inventory_management_api.dto.response.UserResponseDto;
import com.doj0.inventory_management_api.model.User;
import com.doj0.inventory_management_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponseDto> getUsers() {
        return userRepository.findAll().stream()
            .map(user -> UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .roles(user.getRoles() != null ? new ArrayList<>(user.getRoles()) : List.of())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build()
            )
            .collect(Collectors.toList());
    }

    public void register(RegisterRequestDto requestDto) {
        Boolean existing = userRepository.findByEmail(requestDto.getEmail()).isPresent();
        
        if (existing) {
            throw new IllegalArgumentException("This email is already used");
        }

        if (!requestDto.getPassword().equals(requestDto.getConfirmationPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        User user = User.builder()
            .name(requestDto.getName())
            .email(requestDto.getEmail())
            .password(passwordEncoder.encode(requestDto.getPassword()))
            .roles(Set.of(requestDto.getRole()))
            .build();

        userRepository.save(user);
    }
}

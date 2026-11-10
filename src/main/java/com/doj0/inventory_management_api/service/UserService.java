package com.doj0.inventory_management_api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<UserResponseDto> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
            .map(user -> mapToDto(user)
            );
    }

    public UserResponseDto getUserById(String userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return mapToDto(user);
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

    public void deleteUser(String userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        userRepository.delete(user);
    }

    // Helpers

    private UserResponseDto mapToDto(User user) {
        return UserResponseDto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .roles(user.getRoles() != null ? new ArrayList<>(user.getRoles()) : List.of())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
    }
}

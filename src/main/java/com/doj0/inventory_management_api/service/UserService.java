package com.doj0.inventory_management_api.service;

import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.doj0.inventory_management_api.dto.request.RegisterRequestDto;
import com.doj0.inventory_management_api.model.User;
import com.doj0.inventory_management_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequestDto requestDto) {
        Boolean existing = userRepository.findByEmail(requestDto.getEmail()).isPresent();
        
        if (existing) {
            throw new IllegalArgumentException("This email is already used");
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

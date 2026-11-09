package com.doj0.inventory_management_api.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.doj0.inventory_management_api.dto.request.LoginRequestDto;
import com.doj0.inventory_management_api.dto.response.LoginResponseDto;
import com.doj0.inventory_management_api.dto.response.UserResponseDto;
import com.doj0.inventory_management_api.model.User;
import com.doj0.inventory_management_api.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor  
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserResponseDto getCurrentUser(User currentUser) {
        return UserResponseDto.builder()
            .id(currentUser.getId())
            .name(currentUser.getName())
            .email(currentUser.getEmail())
            .createdAt(currentUser.getCreatedAt())
            .updatedAt(currentUser.getUpdatedAt())
            .build();
    }

    public LoginResponseDto login(LoginRequestDto requestDto) {
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    requestDto.getEmail(),
                    requestDto.getPassword()
                )
            );
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository.findByEmail(requestDto.getEmail())
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String token = jwtService.generateToken(userDetails);

        UserResponseDto userResponseDto = UserResponseDto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();

        return new LoginResponseDto(token, userResponseDto);
    }
}

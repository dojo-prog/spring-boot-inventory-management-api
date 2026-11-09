package com.doj0.inventory_management_api.security;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration 
@EnableWebSecurity 
@RequiredArgsConstructor 
@EnableMethodSecurity 
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/*/auth/login").permitAll()
                .requestMatchers("/error").permitAll()
                .anyRequest().authenticated()   
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
             )
            .exceptionHandling(handling -> handling
                .authenticationEntryPoint(restAuthenticationEntryPoint())
                .accessDeniedHandler(restAccessDeniedHandler())
            )
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(
                jwtAuthenticationFilter, 
                UsernamePasswordAuthenticationFilter.class
            );

        return httpSecurity.build();
    }

    @Bean 
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
            = new DaoAuthenticationProvider(userDetailsService);

        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager(); 
    }

    @Bean 
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Helpers
    private AuthenticationEntryPoint restAuthenticationEntryPoint() {
        return (request, response, authException) -> writeError(
            response, 
            HttpServletResponse.SC_UNAUTHORIZED, 
            "Unauthorized", 
            "Authentication is required to access this resource"
        );
    }

    private AccessDeniedHandler restAccessDeniedHandler() {
        return (request, response, accessDeniedException) -> writeError(
            response, 
            HttpServletResponse.SC_FORBIDDEN, 
            "Forbidden", 
            "You do not have permission to access this resource"
        );
    }

    private void writeError(
        HttpServletResponse response, 
        int status, 
        String error,
        String message
    ) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(
             "{\"status\": " + status + ", \"error\": \"" + error + "\", \"message\": \"" + message + "\"}"
        );

    }
}

package com.indomaret.backend.service.user;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;

import java.util.Collections;
import com.indomaret.backend.model.User;

import com.indomaret.backend.dto.auth.LoginRequest;
import com.indomaret.backend.dto.auth.LoginResponse;
import com.indomaret.backend.dto.auth.RegisterRequest;
import com.indomaret.backend.payload.GenericResponse;
import com.indomaret.backend.repository.UserRepository;
import com.indomaret.backend.security.JwtTokenProvider;

@Service
public class AuthService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  JwtTokenProvider jwtTokenProvider;


    public GenericResponse<String> register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            return GenericResponse.<String>builder()
                    .success(false)
                    .message("Username already exists")
                    .data(null)
                    .build();
        }

        User user = User.builder()
                .fullName(req.getFullName())
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .roles(Collections.singleton("ROLE_USER"))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        return GenericResponse.<String>builder()
                .success(true)
                .message("User registered")
                .data(null)
                .build();
    }

    public GenericResponse<LoginResponse> login(LoginRequest req) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
            );
        } catch (AuthenticationException ex) {
            return GenericResponse.<LoginResponse>builder()
                    .success(false)
                    .message("Invalid username or password")
                    .data(null)
                    .build();
        }

        String token = jwtTokenProvider.generateToken(req.getUsername());
        return GenericResponse.<LoginResponse>builder()
                .success(true)
                .message("Login successful")
                .data(new LoginResponse(token))
                .build();
    }
    
}

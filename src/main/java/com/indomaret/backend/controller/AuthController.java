package com.indomaret.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.indomaret.backend.dto.auth.LoginRequest;
import com.indomaret.backend.dto.auth.LoginResponse;
import com.indomaret.backend.dto.auth.RegisterRequest;
import com.indomaret.backend.payload.GenericResponse;
import com.indomaret.backend.service.user.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private  AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<GenericResponse<String>> register(@Valid @RequestBody RegisterRequest req) {
        GenericResponse<String> response = authService.register(req);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public GenericResponse<LoginResponse> login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }
}

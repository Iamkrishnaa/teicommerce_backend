package com.teispace.teicommerce_backend.controllers;

import com.teispace.teicommerce_backend.dtos.login.LoginRequest;
import com.teispace.teicommerce_backend.dtos.login.LoginResponse;
import com.teispace.teicommerce_backend.dtos.register.RegisterRequest;
import com.teispace.teicommerce_backend.dtos.register.RegisterResponse;
import com.teispace.teicommerce_backend.serviceImplementations.AuthServiceImplementation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthServiceImplementation authService;

    public AuthController(AuthServiceImplementation authService) {
        this.authService = authService;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(
                authService.login(loginRequest),
                HttpStatus.OK
        );
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) throws Exception {
        return new ResponseEntity<>(
                authService.register(registerRequest),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(HttpServletRequest request) {
        return new ResponseEntity<>(
                authService.refreshToken(request),
                HttpStatus.OK
        );
    }

}

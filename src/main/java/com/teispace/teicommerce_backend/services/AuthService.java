package com.teispace.teicommerce_backend.services;

import com.teispace.teicommerce_backend.dtos.login.LoginRequest;
import com.teispace.teicommerce_backend.dtos.login.LoginResponse;
import com.teispace.teicommerce_backend.dtos.register.RegisterRequest;
import com.teispace.teicommerce_backend.dtos.register.RegisterResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);

    LoginResponse refreshToken(HttpServletRequest request);

    RegisterResponse register(RegisterRequest registerRequest) throws Exception;
    
}
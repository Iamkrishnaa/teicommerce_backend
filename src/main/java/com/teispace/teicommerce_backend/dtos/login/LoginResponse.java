package com.teispace.teicommerce_backend.dtos.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private final String token;
    private final String refreshToken;
    private final String tokenType = "Bearer";

    public LoginResponse(String accessToken, String refreshToken) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
    }
}
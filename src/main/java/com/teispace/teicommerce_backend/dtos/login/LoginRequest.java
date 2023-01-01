package com.teispace.teicommerce_backend.dtos.login;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotNull(message = "Email is required")
    @NotEmpty(message = "Email can't be empty")
    @Email(message = "Invalid email")
    private String email;

    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password can't be empty")
    @Size(min = 6, max = 20, message = "Password must be between than 6-20 character long")
    private String password;
}

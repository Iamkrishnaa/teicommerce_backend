package com.teispace.teicommerce_backend.dtos.register;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotNull(message = "Email is required")
    @NotEmpty(message = "Email can't be empty")
    @Email(message = "Invalid email")
    private String email;

    @NotNull(message = "User Name is required")
    @NotEmpty(message = "User Name can't be empty")
    @Size(min = 4, max = 30, message = "User Name should be between 4-30 characters")
    private String userName;

    @NotNull(message = "Phone Number is required")
    @NotEmpty(message = "Phone Number can't be empty")
    @Size(min = 10, max = 15, message = "Invalid Phone Number")
    private String phoneNumber;

    @NotNull(message = "Full Name is required")
    @NotEmpty(message = "Full name can't be empty")
    @Size(min = 4, message = "Full name should be minimum 4 characters")
    private String fullName;

    @Size(min = 6, max = 20, message = "Password must be between than 6-20 character long")
    @NotNull(message = "Password is required")
    @NotEmpty(message = "Password can't be empty")
    private String password;


}
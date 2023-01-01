package com.teispace.teicommerce_backend.dtos.register;

import com.teispace.teicommerce_backend.dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse {
    private String status;
    private UserDto user;
}

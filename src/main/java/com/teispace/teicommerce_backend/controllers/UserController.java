package com.teispace.teicommerce_backend.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/api/v1/users")
public class UserController {
    @GetMapping("/api/v1/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getUsers() {
        return "Hello World";
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "Hello Admin";
    }
}

package com.teispace.teicommerce_backend.services;

import com.teispace.teicommerce_backend.dtos.RoleDto;
import com.teispace.teicommerce_backend.dtos.UserDto;

public interface UserService {
    UserDto saveUser(UserDto userDto);

    RoleDto saveRole(RoleDto roleDto);

    void addRoleToUser(String userName, String roleName);

    UserDto getUser(String userName);
}

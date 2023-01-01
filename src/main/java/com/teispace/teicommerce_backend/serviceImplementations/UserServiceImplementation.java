package com.teispace.teicommerce_backend.serviceImplementations;

import com.teispace.teicommerce_backend.dtos.RoleDto;
import com.teispace.teicommerce_backend.dtos.UserDto;
import com.teispace.teicommerce_backend.models.User;
import com.teispace.teicommerce_backend.repos.UserRepo;
import com.teispace.teicommerce_backend.services.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserServiceImplementation implements UserService, UserDetailsService {
    private final UserRepo userRepo;

    public UserServiceImplementation(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    public UserDto saveUser(UserDto userDto) {
        return null;
    }

    @Override
    public RoleDto saveRole(RoleDto roleDto) {
        return null;
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {

    }

    @Override
    public UserDto getUser(String userName) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findUserByUserName(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with given details")
                );

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(
                role -> {
                    authorities.add(new SimpleGrantedAuthority(role.getName()));
                }
        );

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                authorities
        );
    }
}

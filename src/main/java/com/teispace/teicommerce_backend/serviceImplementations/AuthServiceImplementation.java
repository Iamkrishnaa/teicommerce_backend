package com.teispace.teicommerce_backend.serviceImplementations;

import com.teispace.teicommerce_backend.config.JwtAuthFilter;
import com.teispace.teicommerce_backend.dtos.UserDto;
import com.teispace.teicommerce_backend.dtos.login.LoginRequest;
import com.teispace.teicommerce_backend.dtos.login.LoginResponse;
import com.teispace.teicommerce_backend.dtos.register.RegisterRequest;
import com.teispace.teicommerce_backend.dtos.register.RegisterResponse;
import com.teispace.teicommerce_backend.exceptions.InvalidJwtException;
import com.teispace.teicommerce_backend.exceptions.ResourceAlreadyExistsException;
import com.teispace.teicommerce_backend.models.User;
import com.teispace.teicommerce_backend.repos.RoleRepo;
import com.teispace.teicommerce_backend.repos.UserRepo;
import com.teispace.teicommerce_backend.services.AuthService;
import com.teispace.teicommerce_backend.utils.JwtUtils;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthServiceImplementation implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserServiceImplementation userServiceImplementation;
    private final UserRepo userRepo;

    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    public AuthServiceImplementation(
            AuthenticationManager authenticationManager,
            UserServiceImplementation userServiceImplementation,
            UserRepo userRepo,
            JwtUtils jwtUtils,
            ModelMapper modelMapper,
            PasswordEncoder passwordEncoder,
            RoleRepo roleRepo
    ) {
        this.authenticationManager = authenticationManager;
        this.userServiceImplementation = userServiceImplementation;
        this.userRepo = userRepo;
        this.jwtUtils = jwtUtils;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (UsernameNotFoundException ex) {
            throw new UsernameNotFoundException("User with email " + loginRequest.getEmail() + "Not Exists");
        }

        UserDetails userDetails = userServiceImplementation.loadUserByUsername(loginRequest.getEmail());
        String token = jwtUtils.generateToken(userDetails);
        String refreshToken = jwtUtils.generateRefreshToken(userDetails);
        return new LoginResponse(token, refreshToken);
    }

    @Override
    public LoginResponse refreshToken(HttpServletRequest request) {
        String jwtRefreshToken = JwtAuthFilter.getJwtFromRequest(request);
        try {
            UserDetails userDetails =
                    userServiceImplementation
                            .loadUserByUsername(
                                    jwtUtils.extractUsername(jwtRefreshToken)
                            );

            boolean isRefreshTokenValid = jwtUtils.validateToken(jwtRefreshToken, userDetails);

            if (isRefreshTokenValid) {
                String email = jwtUtils.extractUsername(jwtRefreshToken);
                UserDetails details = userServiceImplementation.loadUserByUsername(email);

                String refreshedAccessToken = jwtUtils.generateRefreshToken(details);

                return new LoginResponse(refreshedAccessToken, jwtRefreshToken);

            } else {
                throw new InvalidJwtException("Invalid Token");
            }
        } catch (JwtException e) {
            throw new InvalidJwtException("Token is either invalid or expired");
        }
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) throws Exception {
        boolean isEmailExists = userRepo.existsUserByEmail(registerRequest.getEmail());
        boolean isUsernameExists = userRepo.existsUsersByUserName(registerRequest.getUserName());

        boolean isPhoneNumberExists = userRepo.existsUsersByPhoneNumber(registerRequest.getPhoneNumber());

        if (isEmailExists) {
            throw new ResourceAlreadyExistsException("Email Already Exists");
        }
        if (isUsernameExists) {
            throw new ResourceAlreadyExistsException("Username Already Exists");
        }
        if (isPhoneNumberExists) {
            throw new ResourceAlreadyExistsException("Phone Number Already Exists");
        }

        User user = new User();

        user.setEmail(registerRequest.getEmail());
        user.setUserName(registerRequest.getUserName());
        user.setFullName(registerRequest.getFullName());
        user.setPhoneNumber(registerRequest.getPhoneNumber());

        roleRepo.findRoleByName("USER").ifPresent(role -> user.setRoles(Set.of(role)));

        if (registerRequest.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        } else {
            user.setPassword(passwordEncoder.encode(registerRequest.getEmail()));
        }

        User savedUser = userRepo.save(user);

        return new RegisterResponse(
                "User created Successfully",
                modelMapper.map(savedUser, UserDto.class)
        );
    }
}

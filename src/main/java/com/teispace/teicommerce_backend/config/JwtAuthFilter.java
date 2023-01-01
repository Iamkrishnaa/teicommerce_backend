package com.teispace.teicommerce_backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teispace.teicommerce_backend.dtos.exceptions.ExceptionResponse;
import com.teispace.teicommerce_backend.serviceImplementations.UserServiceImplementation;
import com.teispace.teicommerce_backend.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserServiceImplementation userServiceImplementation;
    private final JwtUtils jwtUtils;

    public JwtAuthFilter(
            UserServiceImplementation userServiceImplementation,
            JwtUtils jwtUtils
    ) {
        this.userServiceImplementation = userServiceImplementation;
        this.jwtUtils = jwtUtils;
    }

    static public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String token = getJwtFromRequest(request);
            if (token != null) {
                String userName;
                userName = jwtUtils.extractUsername(token);
                if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userServiceImplementation.loadUserByUsername(userName);
                    if (jwtUtils.validateToken(token, userDetails)) {

                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                );

                        authenticationToken.setDetails(
                                new WebAuthenticationDetailsSource()
                                        .buildDetails(request)
                        );
                        SecurityContextHolder
                                .getContext()
                                .setAuthentication(authenticationToken);
                    }
                }
            }
            filterChain.doFilter(request, response);
        } catch (UsernameNotFoundException ex) {
            ExceptionResponse exceptionResponse = new ExceptionResponse(
                    new Date(),
                    HttpStatus.UNAUTHORIZED,
                    List.of(ex.getMessage()),
                    ex.getMessage(),
                    request.getRequestURI()
            );
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ObjectMapper objectMapper = new ObjectMapper();
            
            response.getOutputStream().println(
                    objectMapper.writeValueAsString(exceptionResponse)
            );
        }
    }
}

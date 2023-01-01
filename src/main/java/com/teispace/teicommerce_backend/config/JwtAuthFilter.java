package com.teispace.teicommerce_backend.config;

import com.teispace.teicommerce_backend.serviceImplementations.UserServiceImplementation;
import com.teispace.teicommerce_backend.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

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
        } catch (ExpiredJwtException ex) {
            System.out.println("Jwt expired");
        } catch (JwtException ex) {
            System.out.println("JWT Error");
        } catch (Exception ex) {
            System.out.println("Error during filter");
        }
        filterChain.doFilter(request, response);
    }
}

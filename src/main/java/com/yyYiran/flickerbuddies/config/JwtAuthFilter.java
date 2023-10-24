package com.yyYiran.flickerbuddies.config;

import com.yyYiran.flickerbuddies.model.Token;
import com.yyYiran.flickerbuddies.model.User;
import com.yyYiran.flickerbuddies.repo.TokenRepo;
import com.yyYiran.flickerbuddies.service.FlickService;
import com.yyYiran.flickerbuddies.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Intercept everything request to validate if it is an autheticated user
 */
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepo tokenRepo;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = null;
        String token = null;

        // Get JWT token auth header
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        token = authHeader.substring(7);

        // Extract input username from JWT token
        username = jwtService.extractUsername(token);

        // Generate token for input username
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            // Use input username to find matching user in database
            UserDetails userFound = this.userDetailsService.loadUserByUsername(username);
//            System.err.println("loadUserByUsername: " + userFound);

            // Check if token matches this user's most recent token in db
            boolean tokenFound = (tokenRepo.findByToken(token) != null);

            // Check if user extract from token == user from db && token still valid
            if (jwtService.isTokenValid(token, userFound) && tokenFound){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userFound, null, userFound.getAuthorities());
//                authToken.setDetails(
//                        new WebAuthenticationDetailsSource().buildDetails(request)
//                );
                System.err.println("authToken: " + authToken);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }
        filterChain.doFilter(request, response);
    }
}

package com.example.springboot;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class OncePerRequestFilterCustom extends OncePerRequestFilter {
    @Autowired
    private UserDetailsServicesCustom userDetailsService;


    @Autowired
    private TokenValidator tokenValidator;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                try {
                    String jeton = recupererJetonJWT(request);
                    
                    if (jeton != null && tokenValidator.validateJwtToken(jeton)) {
                // Parse the username from the token
                String username = tokenValidator.getUserNameFromJwtToken(jeton);

                // Load user details from the database
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Validate the token and set up the authentication
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication in the SecurityContextHolder
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }  catch (Exception e) {
            logger.error("Erreur d'authentification", e);
        }
        filterChain.doFilter(request, response);
                }
    

    private String recupererJetonJWT(HttpServletRequest request) {
        // Extract the token from the Authorization header
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

}

package com.demo.security;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	
	 private static final String BEARER_PREFIX = "Bearer ";
	
	public JwtAuthenticationFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}		
	
	@Override
    protected void doFilterInternal(HttpServletRequest request,
						            HttpServletResponse response,
						            FilterChain filterChain)
						throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
            String token = authHeader.substring(7);

            if (jwtUtil.isTokenValid(token)) {
            	String username = jwtUtil.extractUsername(token);
            	
            	  UsernamePasswordAuthenticationToken authentication =
                          new UsernamePasswordAuthenticationToken(username, null, List.of()); // List.of() significa sin roles.

                  authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                  // Establece la autenticación en el contexto de seguridad
                  SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
	
    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
	
}

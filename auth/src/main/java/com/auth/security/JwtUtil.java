package com.auth.security;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth.service.JwtUtilService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil implements JwtUtilService {

	@Value("${spring.jwt.secret}")
	private String SECRET_KEY;

	@Override
	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1h
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes()).compact();
	}

	@Override
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String extractUsername(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token).getBody().getSubject();
	}
}

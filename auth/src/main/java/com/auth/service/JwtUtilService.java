package com.auth.service;

public interface JwtUtilService {

	String generateToken(String username);

	boolean validateToken(String token);

	String extractUsername(String token);
}

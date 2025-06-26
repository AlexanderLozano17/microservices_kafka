package com.demo.service;

public interface JwtUtilService {

	String generateToken(String username);

	boolean isTokenValid(String token);
	
	String extractUsername(String token);
}

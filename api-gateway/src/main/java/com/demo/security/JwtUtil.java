package com.demo.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.demo.service.JwtUtilService;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component
public class JwtUtil implements JwtUtilService {

	@Value("${spring.jwt.secret}")
	private String SECRET_KEY;
	
	byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
	Key secretKey = new SecretKeySpec(decodedKey, SignatureAlgorithm.HS256.getJcaName());


	  public String generateToken(String username) {
	        return Jwts.builder()
	                .setSubject(username)
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))  // Expira en 10 horas
	                .signWith(secretKey)
	                .compact();
	    }

	    public boolean isTokenValid(String token) {
	        try {
	        	Jwts.parserBuilder()
                .setSigningKey(secretKey) // Asegúrate de que la clave secreta sea la correcta
                .build()
                .parseClaimsJws(token); // Verifica la firma y otros parámetros del token
	            return true;
	        } catch (JwtException e) {
	            return false;  // El token no es válido
	        }
	    }

	    public String extractUsername(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(secretKey)
	                .build()
	                .parseClaimsJws(token)
	                .getBody()
	                .getSubject();
	    }
}

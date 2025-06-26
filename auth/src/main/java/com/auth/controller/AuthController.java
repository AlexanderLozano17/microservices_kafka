package com.auth.controller;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.entities.User;
import com.auth.service.JwtUtilService;
import com.auth.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final JwtUtilService jwtUtilService;
    private final UserService userService;

    public AuthController(JwtUtilService jwtUtilService, UserService userService) {
        this.jwtUtilService = jwtUtilService;
        this.userService = userService;
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        if (userService.validateUser(user.getUsername(), user.getPassword())) {
            String token = jwtUtilService.generateToken(user.getUsername());
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }
}

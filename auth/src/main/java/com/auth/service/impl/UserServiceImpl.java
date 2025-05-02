package com.auth.service.impl;

import org.springframework.stereotype.Service;

import com.auth.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public boolean validateUser(String username, String password) {
		return username.equals("admin") && password.equals("12345");
	}

}

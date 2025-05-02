package com.auth.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String username;
    private String password;

}

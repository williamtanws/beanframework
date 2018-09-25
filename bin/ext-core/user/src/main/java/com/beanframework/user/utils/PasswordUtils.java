package com.beanframework.user.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
	
	public static final String encode(String password){
		return new BCryptPasswordEncoder().encode(password);
	}
	
	public static final boolean isMatch(String rawPassword, String encodedPassword){
		return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
	}
}

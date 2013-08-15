package com.heaptrip.domain.entity.account.user;

/**
 * 
 * entity use for user registration on the website and through the social network
 * 
 */

public class UserRegistration extends User {
	
	private String password;
	
	public UserRegistration() {
		super();
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

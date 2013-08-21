package com.heaptrip.domain.entity.adm;

import com.heaptrip.domain.entity.category.SimpleCategory;

public class User {

	private String firstName;
	private String secondName;
	private String password;
	private String[] roles;
	private SimpleCategory[] categories;

	
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return this.secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void clearPassword() {
		setPassword(null);
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public SimpleCategory[] getCategories() {
		return categories;
	}

	public void setCategories(SimpleCategory[] categories) {
		this.categories = categories;
	}

}

package com.heaptrip.domain.entity.user;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.heaptrip.domain.entity.BaseObject;

@JsonTypeInfo(use = Id.CLASS, property = "_class")
public abstract class User extends BaseObject {

	public static final String COLLECTION_NAME = "users";

	private String[] roles;
	
	// entity class name
	private String _class;
	
	// first name
	private String firstName;

	// second name
	private String secondName;
	
	// user status
	private UserStatusEnum status;

	public String get_class() {
		return _class;
	}

	public void set_class(String _class) {
		this._class = _class;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public UserStatusEnum getStatus() {
		return status;
	}

	public void setStatus(UserStatusEnum status) {
		this.status = status;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}
	

}

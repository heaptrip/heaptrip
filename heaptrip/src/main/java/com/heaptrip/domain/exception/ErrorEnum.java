package com.heaptrip.domain.exception;

public enum ErrorEnum {

	LOGIN_FAILURE("err.login.failure"),
	REGISTRATION_FAILURE("err.registration.failure");

	public String KEY;

	ErrorEnum(String KEY) {
		this.KEY = KEY;
	}

}

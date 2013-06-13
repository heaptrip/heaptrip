package com.heaptrip.domain.exception;

public enum ErrorEnum {

	LOGIN_FAILURE("err.login.failure"), 
	REGISTRATION_FAILURE("err.registration.failure"),

	REMOVE_TRIP_FAILURE("err.remove.trip.failure");

	public String KEY;

	ErrorEnum(String KEY) {
		this.KEY = KEY;
	}

}

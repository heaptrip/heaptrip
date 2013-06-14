package com.heaptrip.domain.exception;

public enum ErrorEnum {

	LOGIN_FAILURE("err.login.failure"), 
	REGISTRATION_FAILURE("err.registration.failure"),
	REMOVE_TRIP_FAILURE("err.remove.trip.failure"),
	ERR_SYSTEM_DB("err.system.bd"), 
	ERR_SYSTEM_SOLR("err.system.solr");

	public String KEY;

	ErrorEnum(String KEY) {
		this.KEY = KEY;
	}

}

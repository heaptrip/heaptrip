package com.heaptrip.domain.exception;

/**
 *  Error list from:
 *  
 *  messages_ru.properties
 *  messages_en.properties 
 */
public enum ErrorEnum {

	LOGIN_FAILURE("err.login.failure"), 
	REGISTRATION_FAILURE("err.registration.failure"),
	REMOVE_TRIP_FAILURE("err.remove.trip.failure"),
	REMOVE_TRIP_LANGUAGE_FAILURE("err.remove.trip.language.failure"),
	ERR_SYSTEM_DB("err.system.bd"), 
	ERR_SYSTEM_SOLR("err.system.solr");

	public String KEY;

	ErrorEnum(String KEY) {
		this.KEY = KEY;
	}

}

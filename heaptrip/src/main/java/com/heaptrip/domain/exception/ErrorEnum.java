package com.heaptrip.domain.exception;

/**
 *  Error list from:
 *  
 *  errors_ru.properties
 *  errors_en.properties 
 */
public enum ErrorEnum {

	LOGIN_FAILURE("err.login.failure"), 
	REGISTRATION_FAILURE("err.registration.failure"),
	
	REMOVE_TRIP_FAILURE("err.remove.trip.failure"),
	REMOVE_TRIP_LANGUAGE_FAILURE("err.remove.trip.language.failure"),
	
	ERR_SYSTEM_CREATE("err.system.create.exception"),
	ERR_SYSTEM_DB("err.system.bd"), 
	ERR_SYSTEM_SOLR("err.system.solr"),
	ERR_SYSTEM_SEND_MAIL("err.system.send.mail");
	
	public String KEY;

	ErrorEnum(String KEY) {
		this.KEY = KEY;
	}

}

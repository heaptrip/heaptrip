package com.heaptrip.domain.exception;

public class RestException extends RuntimeException {

	private static final long serialVersionUID = 2461073977949763503L;

	public RestException(String key) {
		super(key);
	}

}

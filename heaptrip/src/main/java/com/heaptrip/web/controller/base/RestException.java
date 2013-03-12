package com.heaptrip.web.controller.base;

public class RestException extends RuntimeException {

	private static final long serialVersionUID = 2461073977949763503L;

	public RestException(Throwable exc) {
		super(exc);
	}

}

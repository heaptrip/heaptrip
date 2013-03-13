package com.heaptrip.domain.exception;

import java.util.Locale;

public class BusinessExeption extends BaseExeption {

	private static final long serialVersionUID = 8320988710630318178L;

	public BusinessExeption(ErrorEnum ERROR, Locale locale) {
		super(ERROR, locale);
	}

	public BusinessExeption(ErrorEnum ERROR, Locale locale, Object... arguments) {
		super(ERROR, locale, arguments);
	}

}

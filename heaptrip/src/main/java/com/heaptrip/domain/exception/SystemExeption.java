package com.heaptrip.domain.exception;

import java.util.Locale;

public class SystemExeption extends BaseExeption {

	private static final long serialVersionUID = 8320988710630318178L;

	public SystemExeption(ErrorEnum ERROR, Locale locale) {
		super(ERROR, locale);
	}

	public SystemExeption(ErrorEnum ERROR, Locale locale, Object... arguments) {
		super(ERROR, locale, arguments);
	}

}

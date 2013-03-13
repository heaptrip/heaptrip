package com.heaptrip.domain.exception;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class BaseExeption extends RuntimeException {

	private static final long serialVersionUID = 7613126628186143461L;

	private ErrorEnum ERROR;
	private Object arguments;
	private Locale locale;

	public BaseExeption(ErrorEnum ERROR, Locale locale) {
		this.ERROR = ERROR;
		this.locale = locale;
	}

	public BaseExeption(ErrorEnum ERROR, Locale locale, Object... arguments) {
		this.ERROR = ERROR;
		this.arguments = arguments;
		this.locale = locale;
	}

	@Override
	public String getMessage() {
		return getLocalizedMessage(ERROR.KEY, Locale.getDefault(), arguments);
	}

	public String getLocalizedMessage() {
		return getLocalizedMessage(ERROR.KEY, locale, arguments);
	}

	protected String getLocalizedMessage(String key, Locale locale, Object... arguments) {

		String result = "Message not found " + key + "...";

		ResourceBundle messages = ResourceBundle.getBundle("locale/messages", locale);

		if (messages.containsKey(key)) {

			result = messages.getString(key);

			if (arguments != null && arguments.length > 0) {
				result = MessageFormat.format(result, arguments);
			}
		}

		return result;
	}

}

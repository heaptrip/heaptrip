package com.heaptrip.domain.service.system;

import java.util.Locale;

public interface LocaleService {

	Locale getCurrentLocale();

	String getMessage(String key);

}

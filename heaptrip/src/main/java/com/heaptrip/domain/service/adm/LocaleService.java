package com.heaptrip.domain.service.adm;

import java.util.Locale;

public interface LocaleService {

	Locale getCurrentLocale();

	String getMessage(String key);

	
}

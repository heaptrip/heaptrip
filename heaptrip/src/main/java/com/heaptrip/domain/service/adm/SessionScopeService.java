package com.heaptrip.domain.service.adm;

import java.util.Locale;

import com.heaptrip.domain.entity.adm.User;

public interface SessionScopeService {

	Locale getCurrentLocale();

	String getMessage(String key);

	User getCurrentUser();

}

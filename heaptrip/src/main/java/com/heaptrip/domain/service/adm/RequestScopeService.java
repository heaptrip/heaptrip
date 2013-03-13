package com.heaptrip.domain.service.adm;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.heaptrip.domain.entity.adm.User;

public interface RequestScopeService {

	ErrorService getErrorServise();

	User getCurrentUser();

	HttpServletRequest getCurrentRequest();

	String getCurrentContextPath();

	String getCurrentUrl(boolean isWithParameters);

	Locale getCurrentLocale();

	String getMessage(String key);

}

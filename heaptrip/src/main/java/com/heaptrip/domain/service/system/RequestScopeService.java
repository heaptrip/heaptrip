package com.heaptrip.domain.service.system;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.heaptrip.domain.entity.account.user.User;

public interface RequestScopeService {

	ErrorService getErrorServise();

	User getCurrentUser();

	HttpServletRequest getCurrentRequest();

	String getCurrentContextPath();

	String getCurrentRequestUrl(boolean isWithParameters);

	String getCurrentRequestIP();

	Locale getCurrentLocale();

	String getMessage(String key);

}

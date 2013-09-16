package com.heaptrip.service.system;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.service.system.ErrorService;
import com.heaptrip.domain.service.system.LocaleService;
import com.heaptrip.domain.service.system.RequestScopeService;
import com.heaptrip.domain.service.system.UserService;

@Service("requestScopeService")
public class RequestScopeServiceImpl implements RequestScopeService {

	@Autowired(required = false)
	private HttpServletRequest request;

	@Autowired
	private LocaleService localeService;

	@Autowired
	private ErrorService errorService;

	@Autowired
	private UserService userService;

	@Override
	public User getCurrentUser() {
		return userService.getCurrentUser();
	}

	@Override
	public String getCurrentRequestUrl(boolean isWithParameters) {

		String url = null;

		if (request != null) {
			url = getCurrentContextPath() + request.getServletPath();
			if (isWithParameters) {
				url = "?" + request.getQueryString();
			}
		}

		return url;
	}

	@Override
	public String getCurrentContextPath() {
		return (request != null) ? request.getScheme() + "://" + request.getServerName() + ":"
				+ request.getServerPort() + request.getContextPath() : "http://apptest.heaptrip.com";
	}

	@Override
	public ErrorService getErrorServise() {
		return errorService;
	}

	@Override
	public HttpServletRequest getCurrentRequest() {
		return request;
	}

	@Override
	public Locale getCurrentLocale() {
		return localeService.getCurrentLocale();
	}

	@Override
	public String getMessage(String key) {
		return localeService.getMessage(key);
	}

	@Override
	public String getCurrentRequestIP() {
		return getCurrentRequest().getRemoteAddr();
	}
}

package com.heaptrip.service.adm;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.adm.User;
import com.heaptrip.domain.service.adm.LocaleService;
import com.heaptrip.domain.service.adm.RequestScopeService;

@Service
public class RequestScopeServiceImpl implements RequestScopeService {

	@Autowired(required = false)
	private HttpServletRequest request;

	@Autowired
	LocaleService localeService;

	@Override
	public Locale getCurrentLocale() {
		return localeService.getCurrentLocale();
	}

	@Override
	public String getMessage(String key) {
		return localeService.getMessage(key);
	}

	@Override
	public User getCurrentUser() {
		User result = null;
		try {
			result = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Throwable e) {
		}
		return result;
	}

	@Override
	public String getCurrentUrl(boolean isWithParameters) {

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
				+ request.getServerPort() + request.getContextPath() : null;
	}
}

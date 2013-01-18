package com.heaptrip.service.adm;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.heaptrip.domain.entity.adm.User;
import com.heaptrip.domain.service.adm.LocaleService;
import com.heaptrip.domain.service.adm.SessionScopeService;

public class SessionScopeServiceImpl implements SessionScopeService {

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

}

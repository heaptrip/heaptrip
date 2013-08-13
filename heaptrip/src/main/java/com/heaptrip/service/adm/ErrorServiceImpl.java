package com.heaptrip.service.adm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.exception.BaseException;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.SystemException;
import com.heaptrip.domain.service.adm.ErrorService;
import com.heaptrip.domain.service.adm.LocaleService;

@Service
public class ErrorServiceImpl implements ErrorService {

	@Autowired
	private LocaleService localeService;

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BaseException> T createException(Class<? extends BaseException> clazz, ErrorEnum error) {
		BaseException exeption = null;
		try {
			exeption = clazz.newInstance();
			exeption.setError(error);
			exeption.setLocale(localeService.getCurrentLocale());
		} catch (InstantiationException | IllegalAccessException e) {
			exeption = new SystemException(e);
			exeption.setError(ErrorEnum.ERR_SYSTEM_CREATE);
			exeption.setLocale(localeService.getCurrentLocale());
		}
		return (T) exeption;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends BaseException> T createException(Class<? extends BaseException> clazz, ErrorEnum error,
			Object... arguments) {
		BaseException exeption = null;
		try {
			exeption = clazz.newInstance();
			exeption.setError(error);
			exeption.setLocale(localeService.getCurrentLocale());
			exeption.setArguments(arguments);
		} catch (InstantiationException | IllegalAccessException e) {
			exeption = new SystemException(e);
			exeption.setError(ErrorEnum.ERR_SYSTEM_CREATE);
			exeption.setLocale(localeService.getCurrentLocale());
		}
		return (T) exeption;
	}

}

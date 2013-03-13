package com.heaptrip.service.adm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.exception.BusinessExeption;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.SystemExeption;
import com.heaptrip.domain.service.adm.ErrorService;
import com.heaptrip.domain.service.adm.LocaleService;

@Service
public class ErrorServiceImpl implements ErrorService {

	@Autowired
	private LocaleService localeService;

	@Override
	public SystemExeption createSystemException(ErrorEnum ERROR) {
		return new SystemExeption(ERROR, localeService.getCurrentLocale());
	}

	@Override
	public SystemExeption createSystemException(ErrorEnum ERROR, Object... arguments) {
		return new SystemExeption(ERROR, localeService.getCurrentLocale(), arguments);
	}

	@Override
	public BusinessExeption createBusinessExeption(ErrorEnum ERROR) {
		return new BusinessExeption(ERROR, localeService.getCurrentLocale());
	}

	@Override
	public BusinessExeption createBusinessExeption(ErrorEnum ERROR, Object... arguments) {
		return new BusinessExeption(ERROR, localeService.getCurrentLocale(), arguments);
	}

}

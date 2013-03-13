package com.heaptrip.domain.service.adm;

import com.heaptrip.domain.exception.BusinessExeption;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.SystemExeption;

public interface ErrorService {

	SystemExeption createSystemException(ErrorEnum ERROR);

	SystemExeption createSystemException(ErrorEnum ERROR, Object... arguments);

	BusinessExeption createBusinessExeption(ErrorEnum ERROR);

	BusinessExeption createBusinessExeption(ErrorEnum ERROR, Object... arguments);

}

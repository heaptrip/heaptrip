package com.heaptrip.domain.service.adm;

import com.heaptrip.domain.exception.BaseException;
import com.heaptrip.domain.exception.ErrorEnum;

public interface ErrorService {

	<T extends BaseException> T createException(Class<? extends BaseException> clazz, ErrorEnum error);

	<T extends BaseException> T createException(Class<? extends BaseException> clazz, ErrorEnum error,
			Object... arguments);
}

package com.heaptrip.domain.service.system;

import com.heaptrip.domain.exception.BaseException;
import com.heaptrip.domain.exception.ErrorEnum;

public interface ErrorService {

	<T extends BaseException> T createException(Class<? extends BaseException> clazz);

	<T extends BaseException> T createException(Class<? extends BaseException> clazz, ErrorEnum error);

	<T extends BaseException> T createException(Class<? extends BaseException> clazz, ErrorEnum error,
			Object... arguments);

	<T extends BaseException> T createException(Class<? extends BaseException> clazz, Exception cause);

	<T extends BaseException> T createException(Class<? extends BaseException> clazz, Exception cause, ErrorEnum error);

	<T extends BaseException> T createException(Class<? extends BaseException> clazz, Exception cause, ErrorEnum error,
			Object... arguments);

}

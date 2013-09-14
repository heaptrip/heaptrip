package com.heaptrip.domain.exception.rating;

import com.heaptrip.domain.entity.journal.ModuleEnum;
import com.heaptrip.domain.exception.BusinessException;

public class RatingException extends BusinessException {

	private static final long serialVersionUID = 92348079450358083L;

	@Override
	public ModuleEnum getModule() {
		return ModuleEnum.RATING;
	}
}

package com.heaptrip.domain.exception.trip;

import com.heaptrip.domain.entity.journal.ModuleEnum;
import com.heaptrip.domain.exception.BusinessException;

public class TripException extends BusinessException {

	private static final long serialVersionUID = 3162402207733206558L;

	@Override
	public ModuleEnum getModule() {
		return ModuleEnum.TRIP;
	}

}

package com.heaptrip.domain.exception.event;

import com.heaptrip.domain.entity.journal.ModuleEnum;
import com.heaptrip.domain.exception.BusinessException;

public class EventException extends BusinessException {

	private static final long serialVersionUID = 2988869310897482467L;

	@Override
	public ModuleEnum getModule() {
		return ModuleEnum.EVENT;
	}

}

package com.heaptrip.domain.exception.account;

import com.heaptrip.domain.entity.journal.ModuleEnum;
import com.heaptrip.domain.exception.BusinessException;

public class AccountException extends BusinessException {

	private static final long serialVersionUID = -4077299340036763921L;

	@Override
	public ModuleEnum getModule() {
		return ModuleEnum.ACCOUNT;
	}

}

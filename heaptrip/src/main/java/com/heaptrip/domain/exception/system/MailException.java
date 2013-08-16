package com.heaptrip.domain.exception.system;

import com.heaptrip.domain.entity.journal.ModuleEnum;
import com.heaptrip.domain.exception.Journalable;
import com.heaptrip.domain.exception.SystemException;

public class MailException extends SystemException implements Journalable {

	private static final long serialVersionUID = 5463957881998636824L;

	@Override
	public ModuleEnum getModule() {
		return ModuleEnum.MAIL;
	}
}

package com.heaptrip.domain.exception.system;

import com.heaptrip.domain.entity.journal.ModuleEnum;
import com.heaptrip.domain.exception.Journalable;
import com.heaptrip.domain.exception.Mailable;
import com.heaptrip.domain.exception.SystemException;

public class SolrException extends SystemException implements Journalable, Mailable {

	private static final long serialVersionUID = 7944284831373380151L;

	@Override
	public ModuleEnum getModule() {
		return ModuleEnum.SOLR;
	}

}

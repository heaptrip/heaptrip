package com.heaptrip.domain.service.content.event.criteria;

import com.heaptrip.domain.service.content.criteria.ForeignAccountCriteria;
import com.heaptrip.domain.service.criteria.IDListCriteria;

/**
 * 
 * This criterion is used to find events for foreign account.
 * 
 */
public class EventForeignAccountCriteria extends ForeignAccountCriteria {

	// criteria for event types
	private IDListCriteria types;

	public IDListCriteria getTypes() {
		return types;
	}

	public void setTypes(IDListCriteria types) {
		this.types = types;
	}

}

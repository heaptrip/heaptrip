package com.heaptrip.domain.service.content.trip.criteria;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.service.content.criteria.ForeignAccountCriteria;

/**
 * 
 * This criterion is used to find trip for foreign account.
 * 
 */
public class TripForeignAccountCriteria extends ForeignAccountCriteria {

	// search period
	protected SearchPeriod period;

	public TripForeignAccountCriteria() {
		super();
		contentType = ContentEnum.TRIP;
	}

	public SearchPeriod getPeriod() {
		return period;
	}

	public void setPeriod(SearchPeriod period) {
		this.period = period;
	}

}

package com.heaptrip.domain.service.trip;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.service.content.ForeignAccountCriteria;

/**
 * 
 * This criterion is used to find trip for foreign account.
 * 
 */
public class ForeignAccountTripCriteria extends ForeignAccountCriteria {

	// search period
	protected SearchPeriod period;

	public ForeignAccountTripCriteria() {
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

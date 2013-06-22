package com.heaptrip.domain.service.trip;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.service.content.MyAccountCriteria;

/**
 * 
 * This criterion is used to find trip for a current user account.
 * 
 */
public class MyAccountTripCriteria extends MyAccountCriteria {

	// search period
	protected SearchPeriod period;

	public MyAccountTripCriteria() {
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

package com.heaptrip.domain.service.trip;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.service.content.FeedCriteria;

/**
 * 
 * Criteria for finding a list of trips from the whole database
 * 
 */
public class FeedTripCriteria extends FeedCriteria {

	// search period
	protected SearchPeriod period;

	public FeedTripCriteria() {
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

package com.heaptrip.domain.service.content.trip.criteria;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;

/**
 * 
 * Criteria for finding a list of trips from the whole database
 * 
 */
public class TripFeedCriteria extends FeedCriteria {

	// search period
	protected SearchPeriod period;

	public TripFeedCriteria() {
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

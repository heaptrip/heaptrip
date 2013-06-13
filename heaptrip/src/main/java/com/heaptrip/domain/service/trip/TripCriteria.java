package com.heaptrip.domain.service.trip;

import com.heaptrip.domain.service.ContentCriteria;
import com.heaptrip.domain.service.SearchPeriod;

/**
 * 
 * Criteria for finding trips
 * 
 */
public class TripCriteria extends ContentCriteria {

	// search trips by trip member id
	private String memberId;

	// search period
	private SearchPeriod period;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public SearchPeriod getPeriod() {
		return period;
	}

	public void setPeriod(SearchPeriod period) {
		this.period = period;
	}
}

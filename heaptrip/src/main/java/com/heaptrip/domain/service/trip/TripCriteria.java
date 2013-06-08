package com.heaptrip.domain.service.trip;

import com.heaptrip.domain.service.ContentCriteria;
import com.heaptrip.domain.service.SearchPeriod;

/**
 * 
 * Criteria for finding trips
 * 
 */
public class TripCriteria extends ContentCriteria {

	// search trips where the current user was a trip member
	private boolean member;

	// search periodsl
	private SearchPeriod period;

	public boolean isMember() {
		return member;
	}

	public void setMember(boolean member) {
		this.member = member;
	}

	public SearchPeriod getPeriod() {
		return period;
	}

	public void setPeriod(SearchPeriod period) {
		this.period = period;
	}
}

package com.heaptrip.domain.service.trip;

import com.heaptrip.domain.service.ContentCriteria;
import com.heaptrip.domain.service.SearchPeriod;

public class TripCriteria extends ContentCriteria {

	private String ownerId;

	private String memberId;

	private SearchPeriod period;

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

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

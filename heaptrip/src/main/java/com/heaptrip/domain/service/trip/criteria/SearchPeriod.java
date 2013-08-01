package com.heaptrip.domain.service.trip.criteria;

import java.util.Date;

/**
 * 
 * Search period
 * 
 */
public class SearchPeriod {

	// from which date
	private Date dateBegin;

	// until what date
	private Date dateEnd;

	public SearchPeriod(Date dateBegin, Date dateEnd) {
		super();
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
	}

	public SearchPeriod() {
		super();
	}

	public Date getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

}

package com.heaptrip.domain.service;

import java.util.Date;

public class SearchPeriod {

	private Date dateBegin;

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

package com.heaptrip.web.model.travel;

import com.heaptrip.web.model.content.DateModel;
import com.heaptrip.web.model.content.PriceModel;
import com.heaptrip.web.model.content.StatusModel;

public class ScheduleModel {

	// begin date
	private DateModel begin;

	// end date
	private DateModel end;

	// minimum number of members
	private Long min;

	// maximum number of members
	private Long max;

	// the cost of participation
	private PriceModel price;

	// trip status
	private StatusModel status;

	// The number of members
	private Long members;

	public DateModel getBegin() {
		return begin;
	}

	public void setBegin(DateModel begin) {
		this.begin = begin;
	}

	public DateModel getEnd() {
		return end;
	}

	public void setEnd(DateModel end) {
		this.end = end;
	}

	public PriceModel getPrice() {
		return price;
	}

	public void setPrice(PriceModel price) {
		this.price = price;
	}

	public StatusModel getStatus() {
		return status;
	}

	public void setStatus(StatusModel status) {
		this.status = status;
	}

	public Long getMin() {
		return min;
	}

	public void setMin(Long min) {
		this.min = min;
	}

	public Long getMax() {
		return max;
	}

	public void setMax(Long max) {
		this.max = max;
	}

	public Long getMembers() {
		return members;
	}

	public void setMembers(Long members) {
		this.members = members;
	}
}

package com.heaptrip.domain.entity.trip;

import java.util.Date;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.Price;

/**
 * 
 * Table item
 * 
 */
public class TableItem extends BaseObject {

	// begin date
	private Date begin;

	// end date
	private Date end;

	// minimum number of members
	private Long min;

	// maximum number of members
	private Long max;

	// the cost of participation
	private Price price;

	// trip status
	private TableStatus status;

	// The number of users
	private Long users;

	// The number of invites
	private Long invites;

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public TableStatus getStatus() {
		return status;
	}

	public void setStatus(TableStatus status) {
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

	public Long getUsers() {
		return users;
	}

	public void setUsers(Long users) {
		this.users = users;
	}

	public Long getInvites() {
		return invites;
	}

	public void setInvites(Long invites) {
		this.invites = invites;
	}
}

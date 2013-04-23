package com.heaptrip.domain.entity.trip;

import java.util.Date;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.Price;

public class TableItem extends BaseObject {

	private Date begin;

	private Date end;

	private Long min;

	private Long max;

	private Price price;

	private TableStatus status;

	private TableUser[] users;

	private TableInvite[] invites;

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

	public TableUser[] getUsers() {
		return users;
	}

	public void setUsers(TableUser[] users) {
		this.users = users;
	}

	public TableInvite[] getInvites() {
		return invites;
	}

	public void setInvites(TableInvite[] invites) {
		this.invites = invites;
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

}

package com.heaptrip.domain.entity.account.user;

import java.util.Date;

import com.heaptrip.domain.entity.BaseObject;

public class Practice extends BaseObject {
	
	// begin date
	private Date begin;

	// end date
	private Date end;
	
	private String desc;

	public Practice() {
		super();
	}
	
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}

package com.heaptrip.domain.service.account.criteria;

import com.heaptrip.domain.service.criteria.Criteria;


public class NotificationCriteria extends Criteria {

	// recipient notification
	private String toId;
	
	// notification type
	private String type;
	
	// notification status
	private String status;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}
}

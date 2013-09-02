package com.heaptrip.domain.entity.account.notification;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.heaptrip.domain.entity.BaseObject;

@JsonTypeInfo(use = Id.CLASS, property = "_class")
public class Notification extends BaseObject {
	
	public static final String COLLECTION_NAME = "notifications";
	
	private String fromId;
	
	private String toId;
	
	private Date created;
	
	private NotificationStatusEnum status;
	
	private NotificationTypeEnum type;
	
	public Notification() {
		super();
		status = NotificationStatusEnum.NEW;
		created = new Date();
	}
	
	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public NotificationStatusEnum getStatus() {
		return status;
	}

	public void setStatus(NotificationStatusEnum status) {
		this.status = status;
	}

	public NotificationTypeEnum getType() {
		return type;
	}

	public void setType(NotificationTypeEnum type) {
		this.type = type;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}

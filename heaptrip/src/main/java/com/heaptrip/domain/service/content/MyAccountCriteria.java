package com.heaptrip.domain.service.content;

import com.heaptrip.domain.entity.content.ContentStatusEnum;

/**
 * 
 * This criterion is used to find content for a current user account.
 * 
 */
public class MyAccountCriteria extends ContentCriteria {

	// content statuses
	protected ContentStatusEnum[] status;

	// relation between the current user and the requested content
	protected RelationEnum relation;

	public ContentStatusEnum[] getStatus() {
		return status;
	}

	public void setStatus(ContentStatusEnum[] status) {
		this.status = status;
	}

	public RelationEnum getRelation() {
		return relation;
	}

	public void setRelation(RelationEnum relation) {
		this.relation = relation;
	}

}

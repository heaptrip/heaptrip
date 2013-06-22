package com.heaptrip.domain.service.content;

/**
 * 
 * This criterion is used to find content for foreign account.
 * 
 */
public class ForeignAccountCriteria extends ContentCriteria {

	// id of the content owner
	protected String ownerId;

	// relation between the foreign account and the requested content
	protected RelationEnum relation;

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public RelationEnum getRelation() {
		return relation;
	}

	public void setRelation(RelationEnum relation) {
		this.relation = relation;
	}

}

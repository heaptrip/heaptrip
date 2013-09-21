package com.heaptrip.domain.service.content.criteria;

/**
 * 
 * This criterion is used to find content for foreign account.
 * 
 */
public class ForeignAccountCriteria extends ContentSortCriteria {

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

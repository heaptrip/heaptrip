package com.heaptrip.domain.entity.content;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.Collectionable;

/**
 * 
 * Base entity for all members (members of trips and members of events)
 * 
 */
@JsonTypeInfo(use = Id.CLASS, property = "_class")
public abstract class Member extends BaseObject implements Collectionable {

	// content id
	private String contentId;

	@Override
	public String getCollectionName() {
		return CollectionEnum.MEMBERS.getName();
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
}

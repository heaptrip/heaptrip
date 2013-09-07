package com.heaptrip.domain.entity.rating;

import java.util.Date;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.Collectionable;

/**
 * 
 * Entity for storing ratings
 * 
 */
public class Rating extends BaseObject implements Collectionable {

	// ID who rated
	private String userId;

	// when rated
	private Date date;

	// rating value from 0 to 1:
	// from 0 to 0.19 - one star
	// from 0.2 to 0.39 - two stars
	// from 0.4 to 0.59 - three stars
	// from 0.6 to 0.79 - four stars
	// from 0.8 to 1 - five stars
	private double value;

	// accountId or contentId
	private String targetId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	@Override
	public String getCollectionName() {
		return CollectionEnum.RATINGS.getName();
	}

}

package com.heaptrip.domain.entity.rating;

/**
 * 
 * Store information about total rating
 * 
 */
public abstract class TotalRating {

	// rating value
	protected double value;

	// number of ratings
	protected int count;

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}

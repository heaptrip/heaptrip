package com.heaptrip.domain.entity.trip;

import com.heaptrip.domain.entity.content.Content;

/**
 * 
 * Trip
 * 
 */
public class Trip extends Content {

	// travel schedule
	private TableItem[] table;

	// route
	private Route route;

	// rating
	private Double rating;

	// number of comments
	private Long comments;

	public TableItem[] getTable() {
		return table;
	}

	public void setTable(TableItem[] table) {
		this.table = table;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Long getComments() {
		return comments;
	}

	public void setComments(Long comments) {
		this.comments = comments;
	}

}

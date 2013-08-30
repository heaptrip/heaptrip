package com.heaptrip.domain.entity.trip;

import com.heaptrip.domain.entity.comment.Commentsable;
import com.heaptrip.domain.entity.content.Content;

/**
 * 
 * Trip
 * 
 */
public class Trip extends Content implements Commentsable {

	private static final String COMMENTS_NUMBER_FIELD_NAME = "comments";

	// travel schedule
	private TableItem[] table;

	// route
	private Route route;

	// rating
	private Double rating;

	// number of comments
	private Long comments;

	@Override
	public String getCommentsNumberFieldName() {
		return COMMENTS_NUMBER_FIELD_NAME;
	}

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

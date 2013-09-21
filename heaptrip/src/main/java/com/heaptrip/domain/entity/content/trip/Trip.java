package com.heaptrip.domain.entity.content.trip;

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

	// number of comments
	private long comments;

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

	public long getComments() {
		return comments;
	}

	public void setComments(long comments) {
		this.comments = comments;
	}

}

package com.heaptrip.domain.entity.trip;

import com.heaptrip.domain.entity.Content;
import com.heaptrip.domain.entity.MultiLangText;

/**
 * 
 * Trip
 * 
 */
public class Trip extends Content {

	// multilingual short description
	private MultiLangText summary;

	// multilingual description
	private MultiLangText description;

	// travel schedule
	private TableItem[] table;

	// route
	private Route route;

	// rating
	private Double rating;

	// number of comments
	private Long comments;

	public MultiLangText getSummary() {
		return summary;
	}

	public void setSummary(MultiLangText summary) {
		this.summary = summary;
	}

	public MultiLangText getDescription() {
		return description;
	}

	public void setDescription(MultiLangText description) {
		this.description = description;
	}

	public TableItem[] getTable() {
		return table;
	}

	public void setTable(TableItem[] table) {
		this.table = table;
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

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

}

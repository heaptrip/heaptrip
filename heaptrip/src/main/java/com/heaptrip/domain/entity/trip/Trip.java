package com.heaptrip.domain.entity.trip;

import com.heaptrip.domain.entity.Content;

public class Trip extends Content {

	private Summary summary;

	private Description description;

	private TableItem[] table;

	private Double rating;

	private Long comments;

	public Summary getSummary() {
		return summary;
	}

	public void setSummary(Summary summary) {
		this.summary = summary;
	}

	public Description getDescription() {
		return description;
	}

	public void setDescription(Description description) {
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

}

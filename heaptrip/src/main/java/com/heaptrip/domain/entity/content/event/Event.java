package com.heaptrip.domain.entity.content.event;

import com.heaptrip.domain.entity.Price;
import com.heaptrip.domain.entity.comment.Commentsable;
import com.heaptrip.domain.entity.content.Content;

/**
 * 
 * Event
 * 
 */
public class Event extends Content implements Commentsable {

	private static final String COMMENTS_NUMBER_FIELD_NAME = "comments";

	// event types
	private EventType[] types;

	// number of comments
	private long comments;

	// number of members
	private long members;

	// the cost of participation
	private Price price;

	// route map
	private String map;

	// show map or not
	private boolean showMap;

	@Override
	public String getCommentsNumberFieldName() {
		return COMMENTS_NUMBER_FIELD_NAME;
	}

	public long getComments() {
		return comments;
	}

	public void setComments(long comments) {
		this.comments = comments;
	}

	public EventType[] getTypes() {
		return types;
	}

	public void setTypes(EventType[] types) {
		this.types = types;
	}

	public long getMembers() {
		return members;
	}

	public void setMembers(long members) {
		this.members = members;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public boolean isShowMap() {
		return showMap;
	}

	public void setShowMap(boolean showMap) {
		this.showMap = showMap;
	}

}

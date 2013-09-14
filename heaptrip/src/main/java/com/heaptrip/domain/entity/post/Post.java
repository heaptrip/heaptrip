package com.heaptrip.domain.entity.post;

import com.heaptrip.domain.entity.comment.Commentsable;
import com.heaptrip.domain.entity.content.Content;

public class Post extends Content implements Commentsable {

	private static final String COMMENTS_NUMBER_FIELD_NAME = "comments";

	// number of comments
	private long comments;

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

}

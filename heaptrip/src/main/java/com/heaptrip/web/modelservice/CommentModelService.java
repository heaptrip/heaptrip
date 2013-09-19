package com.heaptrip.web.modelservice;

import java.util.List;

import com.heaptrip.web.model.content.CommentModel;

public interface CommentModelService {

	/**
	 * Get comments list by id of discussion object
	 * 
	 * @param targetId
	 *            id of discussion object
	 * @return comments list
	 */
	public List<CommentModel> getComments(String targetId);
	
}

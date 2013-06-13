package com.heaptrip.domain.service;

import java.util.List;

import com.heaptrip.domain.entity.Comment;

/**
 * 
 * Comment service
 * 
 */
public interface CommentService {

	/**
	 * Add root comment
	 * 
	 * @param targetId
	 *            id of discussion object (trip, post, etc.)
	 * @param userId
	 *            user id
	 * @param text
	 *            comment text
	 * @return comment
	 */
	public Comment addComment(String targetId, String userId, String text);

	/**
	 * Add child comment
	 * 
	 * @param parentId
	 *            id of the parent comment
	 * @param userId
	 *            user id
	 * @param text
	 *            comment text
	 * @return comment
	 */
	public Comment addChildComment(String parentId, String userId, String text);

	/**
	 * Get comments list by id of discussion object
	 * 
	 * @param targetId
	 *            id of discussion object
	 * @return comments list
	 */
	public List<Comment> getComments(String targetId);

	/**
	 * Remove comments by id of discussion object
	 * 
	 * @param targetId
	 *            id of discussion object
	 */
	public void removeComments(String targetId);
}

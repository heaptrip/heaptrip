package com.heaptrip.domain.service.content.post;

import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.service.content.ContentService;

/**
 * Basic service to work with posts
 * 
 */
public interface PostService extends ContentService {

	/**
	 * Save a new content
	 * 
	 * @param post
	 * 
	 * @return post
	 */
	public Post save(Post post);

	/**
	 * Update post information
	 * 
	 * @param post
	 * 
	 */
	public void update(Post post);
}

package com.heaptrip.domain.service.content.post;

import java.util.Locale;

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
	 * @param locale
	 *            the locale for which to create the content
	 * @return post
	 */
	public Post save(Post post, Locale locale);

	/**
	 * Update post information
	 * 
	 * @param post
	 * 
	 */
	public void update(Post post);
}

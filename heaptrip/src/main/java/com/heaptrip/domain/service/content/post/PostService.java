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
	 * @param content
	 * @param locale
	 *            the locale for which to create the content
	 * @return content
	 */
	public Post save(Post content, Locale locale);
}

package com.heaptrip.domain.service.content.post;

import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.service.content.ContentService;

import java.util.List;
import java.util.Locale;

/**
 * Basic service to work with posts
 */
public interface PostService extends ContentService {

    /**
     * Save a new content
     *
     * @param post post
     * @return post
     */
    public Post save(Post post);

    /**
     * Update post information
     *
     * @param post post
     */
    public void update(Post post);

    /**
     * Get post by id list
     *
     * @param postIds list of post id
     * @param locale  requested locale
     * @return list of post
     */
    // TODO konovalov: move to post service
    public List<Post> getPosts(String[] postIds, Locale locale);
}

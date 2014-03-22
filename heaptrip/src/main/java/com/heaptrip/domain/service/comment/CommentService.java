package com.heaptrip.domain.service.comment;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.Collectionable;
import com.heaptrip.domain.entity.comment.Comment;
import com.heaptrip.domain.entity.comment.Commentsable;

import java.util.List;

/**
 * Comment service
 */
public interface CommentService {

    /**
     * Add root comment
     *
     * @param targetClass class of discussion object (trip, post, etc.). Must be extends
     *                    BaseObject and implement Collectionable and Commentsable
     * @param targetId    id of discussion object (trip, post, etc.)
     * @param userId      id of the comment author
     * @param text        comment text
     * @return comment
     */
    public <T extends BaseObject & Collectionable & Commentsable> Comment addComment(Class<T> targetClass,
                                                                                     String targetId, String userId, String text);

    /**
     * Add root comment
     *
     * @param targetId id of discussion object (trip, post, etc.)
     * @param userId   id of the comment author
     * @param text     comment text
     * @return comment
     */
    public Comment addComment(String targetId, String userId, String text);

    /**
     * Add child comment
     *
     * @param targetClass class of discussion object (trip, post, etc.). Must be extends
     *                    BaseObject and implement Collectionable and Commentsable
     * @param targetId    id of discussion object (trip, post, etc.)
     * @param parentId    id of the parent comment
     * @param userId      id of the comment author
     * @param text        comment text
     * @return comment
     */
    public <T extends BaseObject & Collectionable & Commentsable> Comment addChildComment(Class<T> targetClass,
                                                                                          String targetId, String parentId, String userId, String text);

    /**
     * Add child comment
     *
     * @param parentId id of the parent comment
     * @param userId   id of the comment author
     * @param text     comment text
     * @return comment
     */
    public Comment addChildComment(String parentId, String userId, String text);

    /**
     * Get comments list by id of discussion object
     *
     * @param targetId id of discussion object
     * @return comments list
     */
    public List<Comment> getComments(String targetId);

    /**
     * Remove comments by id of discussion object
     *
     * @param targetId id of discussion object
     */
    public void removeComments(String targetId);
}

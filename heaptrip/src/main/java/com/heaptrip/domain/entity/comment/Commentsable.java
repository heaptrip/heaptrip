package com.heaptrip.domain.entity.comment;

/**
 * The interface for objects that store the number of comments
 */
public interface Commentsable {

    /**
     * Get field name for storing count of commets
     *
     * @return field name
     */
    public String getCommentsNumberFieldName();
}

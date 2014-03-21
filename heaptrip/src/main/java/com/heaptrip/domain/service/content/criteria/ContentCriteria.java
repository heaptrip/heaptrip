package com.heaptrip.domain.service.content.criteria;

import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.service.criteria.CategoryRegionCriteria;

/**
 * Base class for all content criterias
 */
public abstract class ContentCriteria extends CategoryRegionCriteria {

    // content type
    protected ContentEnum contentType;

    // current user id
    protected String userId;

    public ContentEnum getContentType() {
        return contentType;
    }

    public void setContentType(ContentEnum contentType) {
        this.contentType = contentType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

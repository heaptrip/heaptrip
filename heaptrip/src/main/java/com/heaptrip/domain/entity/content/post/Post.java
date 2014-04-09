package com.heaptrip.domain.entity.content.post;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;

/**
 * Post
 */
public class Post extends Content {

    @Override
    public ContentEnum getContentType() {
        return ContentEnum.POST;
    }

}

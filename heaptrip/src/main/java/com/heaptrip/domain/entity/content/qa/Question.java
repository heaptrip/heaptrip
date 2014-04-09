package com.heaptrip.domain.entity.content.qa;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;


/**
 * Question
 */
public class Question extends Content {

    @Override
    public ContentEnum getContentType() {
        return ContentEnum.QA;
    }
}

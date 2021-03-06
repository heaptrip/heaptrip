package com.heaptrip.domain.entity.content;

import com.heaptrip.domain.entity.content.event.Event;
import com.heaptrip.domain.entity.content.post.Post;
import com.heaptrip.domain.entity.content.qa.Question;
import com.heaptrip.domain.entity.content.trip.Trip;

/**
 * Enumeration of the types of content
 */
public enum ContentEnum {

    TRIP(Trip.class.getName()),
    POST(Post.class.getName()),
    QA(Question.class.getName()),
    EVENT(Event.class.getName());

    private String clazz;

    private ContentEnum(String clazz) {
        this.clazz = clazz;
    }

    public String getClazz() {
        return clazz;
    }
}

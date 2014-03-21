package com.heaptrip.domain.entity.content.event;

import com.heaptrip.domain.entity.content.Member;

/**
 * Event member
 */
public class EventMember extends Member {

    // user id
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}

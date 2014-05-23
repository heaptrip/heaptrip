package com.heaptrip.domain.entity.content.event;

import com.heaptrip.domain.entity.content.Member;
import com.heaptrip.domain.entity.content.MemberEnum;

/**
 * Event member
 */
public class EventMember extends Member {

    // user id
    private String userId;

    @Override
    public MemberEnum getMemberType() {
        return MemberEnum.EVENT_MEMBER;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}

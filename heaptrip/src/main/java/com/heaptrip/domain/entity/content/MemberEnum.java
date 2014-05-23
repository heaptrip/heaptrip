package com.heaptrip.domain.entity.content;

import com.heaptrip.domain.entity.content.event.EventMember;
import com.heaptrip.domain.entity.content.trip.TripInvite;
import com.heaptrip.domain.entity.content.trip.TripUser;

/**
 * Enumeration of the types of members
 */
public enum MemberEnum {

    EVENT_MEMBER(EventMember.class.getName()),
    TRIP_USER(TripUser.class.getName()),
    TRIP_INVITE(TripInvite.class.getName());

    private String clazz;

    private MemberEnum(String clazz) {
        this.clazz = clazz;
    }

    public String getClazz() {
        return clazz;
    }

}

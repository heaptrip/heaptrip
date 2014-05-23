package com.heaptrip.domain.entity.content.trip;

import com.heaptrip.domain.entity.content.MemberEnum;

/**
 * Trip user
 */
public class TripUser extends TripMember {

    // user id
    private String userId;

    // a sign that the user is the organizer
    private Boolean isOrganizer;

    // user status
    private TableUserStatusEnum status;

    public TripUser() {
        super();
    }

    @Override
    public MemberEnum getMemberType() {
        return MemberEnum.TRIP_USER;
    }

    public Boolean getIsOrganizer() {
        return isOrganizer;
    }

    public void setIsOrganizer(Boolean isOrganizer) {
        this.isOrganizer = isOrganizer;
    }

    public TableUserStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TableUserStatusEnum status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}

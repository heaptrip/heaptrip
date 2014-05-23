package com.heaptrip.domain.entity.content.trip;

import com.heaptrip.domain.entity.content.MemberEnum;

/**
 * Invitation to an external email address
 */
public class TripInvite extends TripMember {

    // email address
    private String email;

    @Override
    public MemberEnum getMemberType() {
        return MemberEnum.TRIP_INVITE;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

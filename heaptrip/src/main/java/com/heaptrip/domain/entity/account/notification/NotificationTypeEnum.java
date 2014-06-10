package com.heaptrip.domain.entity.account.notification;

public enum NotificationTypeEnum {

    MSG(false),
    EMPLOYEE(true),
    FRIEND(true),
    MEMBER(true),
    OWNER(true),
    TRIP_SET_ORGANIZER(false),
    TRIP_INNER_INVITE(true),
    TRIP_REQUEST(true),
    TRIP_REMOVE_MEMBER(false),
    TRIP_MEMBER_REFUSE(false);

    private boolean needAccept;

    NotificationTypeEnum(boolean needAccept) {
        setNeedAccept(needAccept);
    }

    public boolean isNeedAccept() {
        return needAccept;
    }

    public void setNeedAccept(boolean needAccept) {
        this.needAccept = needAccept;
    }

}

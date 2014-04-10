package com.heaptrip.domain.service.account.criteria.notification;

/**
 * Common notification criteria, use for testing or get notifications between users
 */
public class NotificationCriteria extends AbstractNotificationCriteria {

    // acount id, which fire notification
    private String fromId;

    // recipient notification
    private String toId;

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }
}

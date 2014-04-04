package com.heaptrip.domain.entity.account.notification;

/**
 * Base notification for contents
 */
public class ContentNotification extends Notification {

    // content id
    private String contentId;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
}

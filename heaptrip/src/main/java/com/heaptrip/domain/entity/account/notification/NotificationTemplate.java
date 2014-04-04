package com.heaptrip.domain.entity.account.notification;


import java.util.Map;

public class NotificationTemplate {
    private Map<String, String> text;

    public Map<String, String> getText() {
        return text;
    }

    public void setText(Map<String, String> text) {
        this.text = text;
    }
}

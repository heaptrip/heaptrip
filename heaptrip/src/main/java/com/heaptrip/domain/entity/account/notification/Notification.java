package com.heaptrip.domain.entity.account.notification;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.MultiLangText;

import java.util.Date;

/**
 * Base notification object
 */
@JsonTypeInfo(use = Id.CLASS, property = "_class")
public class Notification extends BaseObject {

    // acount id, which fire notification
    private String fromId;

    // account id, which will receive notification
    private String toId;

    // date of create
    private Date created;

    // notification status
    private NotificationStatusEnum status;

    // notification type
    private NotificationTypeEnum type;

    // notification text
    private MultiLangText text;

    public Notification() {
        super();
        status = NotificationStatusEnum.NEW;
        created = new Date();
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public NotificationStatusEnum getStatus() {
        return status;
    }

    public void setStatus(NotificationStatusEnum status) {
        this.status = status;
    }

    public NotificationTypeEnum getType() {
        return type;
    }

    public void setType(NotificationTypeEnum type) {
        this.type = type;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public MultiLangText getText() {
        return text;
    }

    public void setText(MultiLangText text) {
        this.text = text;
    }
}

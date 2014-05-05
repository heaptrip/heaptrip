package com.heaptrip.web.model.profile;

import com.heaptrip.web.model.content.DateModel;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 18.04.14
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
public class NotificationModel {

    private String id;
    private DateModel created;
    private String status;
    private String text;
    private String type;
    private AccountModel accountFrom;
    private AccountModel accountTo;
    private String isAwaitingAction;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DateModel getCreated() {
        return created;
    }

    public void setCreated(DateModel created) {
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AccountModel getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(AccountModel accountFrom) {
        this.accountFrom = accountFrom;
    }

    public AccountModel getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(AccountModel accountTo) {
        this.accountTo = accountTo;
    }

    public String getIsAwaitingAction() {
        return isAwaitingAction;
    }

    public void setIsAwaitingAction(String awaitingAction) {
        isAwaitingAction = awaitingAction;
    }
}

package com.heaptrip.web.model.travel;

import com.heaptrip.domain.entity.content.trip.TableUserStatusEnum;
import com.heaptrip.web.model.profile.AccountModel;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 06.05.14
 * Time: 12:01
 * To change this template use File | Settings | File Templates.
 */
public class ParticipantUserModel extends ParticipantModel {

    // a sign that the user is the organizer
    private boolean isOrganizer;

    // user status
    private String status;

    AccountModel account;

    public AccountModel getAccount() {
        return account;
    }

    public void setAccount(AccountModel account) {
        this.account = account;
    }

    public boolean getIsOrganizer() {
        return isOrganizer;
    }

    public void setIsOrganizer(boolean organizer) {
        isOrganizer = organizer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

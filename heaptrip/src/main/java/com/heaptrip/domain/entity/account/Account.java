package com.heaptrip.domain.entity.account;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.rating.AccountRating;

import java.util.Date;

@JsonTypeInfo(use = Id.CLASS, property = "_class")
public abstract class Account<P extends Profile, S extends Setting> extends BaseObject {

    private String name;

    private String email;

    private Image image;

    private P profile;

    private S setting;

    private AccountStatusEnum status;

    private AccountRating rating;

    private Date created;

    // value is sent to e-mail confirmation of registration, etc.
    private String sendValue;

    public abstract AccountEnum getAccountType();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountStatusEnum getStatus() {
        return status;
    }

    public void setStatus(AccountStatusEnum status) {
        this.status = status;
    }

    public P getProfile() {
        return profile;
    }

    public void setProfile(P profile) {
        this.profile = profile;
    }

    public S getSetting() {
        return setting;
    }

    public void setSetting(S setting) {
        this.setting = setting;
    }

    public AccountRating getRating() {
        return rating;
    }

    public void setRating(AccountRating rating) {
        this.rating = rating;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getSendValue() {
        return sendValue;
    }

    public void setSendValue(String sendValue) {
        this.sendValue = sendValue;
    }
}

package com.heaptrip.domain.entity.account;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.rating.AccountRating;

import java.util.Date;

@JsonTypeInfo(use = Id.CLASS, property = "_class")
public abstract class Account extends BaseObject {

    private String name;

    private String email;

    private Image image;

    private Profile profile;

    private Setting setting;

    private AccountStatusEnum status;

    private AccountRating rating;

    private Date created;

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

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
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
}

package com.heaptrip.domain.repository.redis.entity;

import com.heaptrip.domain.entity.account.AccountEnum;

public class RedisAccount {

    private String id;
    private String name;
    private String email;
    private double rating;
    private String imageId;
    private String smallId;
    private String mediumId;
    private AccountEnum accountType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getSmallId() {
        return smallId;
    }

    public void setSmallId(String smallId) {
        this.smallId = smallId;
    }

    public String getMediumId() {
        return mediumId;
    }

    public void setMediumId(String mediumId) {
        this.mediumId = mediumId;
    }

    public AccountEnum getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountEnum accountType) {
        this.accountType = accountType;
    }
}

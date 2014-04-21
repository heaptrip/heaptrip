package com.heaptrip.domain.entity.account.user;

import com.heaptrip.domain.entity.account.Profile;

import java.util.Date;

public class UserProfile extends Profile {

    private Date birthday;

    private Knowledge[] knowledgies;

    private Practice[] practices;

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Knowledge[] getKnowledgies() {
        return knowledgies;
    }

    public void setKnowledgies(Knowledge[] knowledgies) {
        this.knowledgies = knowledgies;
    }

    public Practice[] getPractices() {
        return practices;
    }

    public void setPractices(Practice[] practices) {
        this.practices = practices;
    }
}
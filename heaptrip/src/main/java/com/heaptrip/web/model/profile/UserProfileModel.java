package com.heaptrip.web.model.profile;

import com.heaptrip.domain.entity.account.user.Knowledge;
import com.heaptrip.domain.entity.account.user.Practice;
import com.heaptrip.web.model.content.CategoryModel;
import com.heaptrip.web.model.content.DateModel;
import com.heaptrip.web.model.content.RegionModel;

import java.util.Date;

public class UserProfileModel {

    private DateModel birthday;

    private KnowledgeModel[] knowledgies;

    private PracticeModel[] practices;

    public DateModel getBirthday() {
        return birthday;
    }

    public void setBirthday(DateModel birthday) {
        this.birthday = birthday;
    }

    public KnowledgeModel[] getKnowledgies() {
        return knowledgies;
    }

    public void setKnowledgies(KnowledgeModel[] knowledgies) {
        this.knowledgies = knowledgies;
    }

    public PracticeModel[] getPractices() {
        return practices;
    }

    public void setPractices(PracticeModel[] practices) {
        this.practices = practices;
    }
}

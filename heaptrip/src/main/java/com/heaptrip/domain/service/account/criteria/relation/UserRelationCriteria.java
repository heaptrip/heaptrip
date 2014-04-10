package com.heaptrip.domain.service.account.criteria.relation;

import com.heaptrip.domain.service.criteria.Criteria;

public class UserRelationCriteria extends Criteria {

    private String toId;

    private String[] types;

    public UserRelationCriteria(String toId) {
        this.toId = toId;
    }

    public UserRelationCriteria(String toId, String[] types) {
        this.toId = toId;
        this.types = types;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String[] getRelationTypes() {
        return types;
    }

    public void setRelationTypes(String[] types) {
        this.types = types;
    }
}

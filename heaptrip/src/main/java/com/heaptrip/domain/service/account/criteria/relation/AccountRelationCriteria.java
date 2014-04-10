package com.heaptrip.domain.service.account.criteria.relation;

import com.heaptrip.domain.service.criteria.Criteria;

/**
 *  Criterion for relations user or user group community
 */

public class AccountRelationCriteria extends Criteria {

    private String fromId;

    private String[] types;

    public AccountRelationCriteria(String fromId) {
        this.fromId = fromId;
    }

    public AccountRelationCriteria(String fromId, String[] types) {
        this.fromId = fromId;
        this.types = types;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String[] getRelationTypes() {
        return types;
    }

    public void setRelationTypes(String[] types) {
        this.types = types;
    }
}

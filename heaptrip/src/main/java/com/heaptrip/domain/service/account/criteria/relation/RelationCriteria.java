package com.heaptrip.domain.service.account.criteria.relation;

import com.heaptrip.domain.service.criteria.Criteria;

public class RelationCriteria extends Criteria {

    private String fromId;

    private String toId;

    private String[] types;

    public RelationCriteria() {
    }

    public RelationCriteria(String fromId, String toId, String[] types) {
        this.fromId = fromId;
        this.toId = toId;
        this.types = types;
    }

    public String[] getRelationTypes() {
        return types;
    }

    public void setRelationTypes(String[] types) {
        this.types = types;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }
}

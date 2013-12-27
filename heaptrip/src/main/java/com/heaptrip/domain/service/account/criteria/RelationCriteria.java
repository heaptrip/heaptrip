package com.heaptrip.domain.service.account.criteria;

import com.heaptrip.domain.entity.account.relation.TypeRelationEnum;
import com.heaptrip.domain.service.criteria.Criteria;

public class RelationCriteria extends Criteria {

    private String fromId;

    private String toId;

    private TypeRelationEnum typeRelation;

    public RelationCriteria () {};

    public RelationCriteria(String fromId, String toId, TypeRelationEnum typeRelation) {
        this.fromId = fromId;
        this.toId = toId;
        this.typeRelation = typeRelation;

    }

    public TypeRelationEnum getTypeRelation() {
        return typeRelation;
    }

    public void setTypeRelation(TypeRelationEnum typeRelation) {
        this.typeRelation = typeRelation;
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

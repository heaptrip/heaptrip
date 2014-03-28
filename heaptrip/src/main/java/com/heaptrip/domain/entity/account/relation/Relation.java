package com.heaptrip.domain.entity.account.relation;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.Collectionable;

public class Relation extends BaseObject implements Collectionable {

    // user relations
    private String fromId;

    // my frend, user community is, user works in, user owns the community
    private String toId;

    private TypeRelationEnum typeRelation;

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

    @Override
    public String getCollectionName() {
        return CollectionEnum.RELATIONS.getName();
    }

    public static Relation getRelation(String userId, String accountId, TypeRelationEnum typeRelation) {
        Relation relation = new Relation();
        relation.setFromId(userId);
        relation.setToId(accountId);
        relation.setTypeRelation(typeRelation);
        return relation;
    }
}

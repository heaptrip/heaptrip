package com.heaptrip.domain.entity.account.relation;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.Collectionable;

public class Relation extends BaseObject implements Collectionable {

    // user relations
    private String fromId;

    // my frend, user community is, user works in, user owns the community
    private String[] userIds;

    private RelationTypeEnum type;

    public RelationTypeEnum getType() {
        return type;
    }

    public void setType(RelationTypeEnum type) {
        this.type = type;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String[] getUserIds() {
        return userIds;
    }

    public void setUserIds(String[] userIds) {
        this.userIds = userIds;
    }

    @Override
    public String getCollectionName() {
        return CollectionEnum.RELATIONS.getName();
    }
}

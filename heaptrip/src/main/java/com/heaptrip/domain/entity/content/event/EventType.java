package com.heaptrip.domain.entity.content.event;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.Collectionable;
import com.heaptrip.domain.entity.MultiLangText;

/**
 * Event type
 */
public class EventType extends BaseObject implements Collectionable {

    // multilingual name of the event type
    private MultiLangText name;

    public EventType() {
        super();
    }

    public EventType(String id) {
        super();
        this.id = id;
    }

    public EventType(String id, MultiLangText name) {
        super();
        this.id = id;
        this.name = name;
    }

    @Override
    public String getCollectionName() {
        return CollectionEnum.EVENT_TYPES.getName();
    }

    public MultiLangText getName() {
        return name;
    }

    public void setName(MultiLangText name) {
        this.name = name;
    }

}

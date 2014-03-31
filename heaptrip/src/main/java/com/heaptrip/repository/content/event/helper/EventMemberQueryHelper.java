package com.heaptrip.repository.content.event.helper;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.event.EventMember;
import com.heaptrip.domain.service.content.event.criteria.EventMemberCriteria;
import com.heaptrip.repository.helper.AbstractQueryHelper;
import org.springframework.stereotype.Service;

@Service
public class EventMemberQueryHelper extends AbstractQueryHelper<EventMemberCriteria, EventMember> {

    @Override
    protected Class<EventMember> getCollectionClass() {
        return EventMember.class;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.MEMBERS.getName();
    }

    @Override
    public Class<EventMemberCriteria> getCriteriaClass() {
        return EventMemberCriteria.class;
    }

    @Override
    public String getQuery(EventMemberCriteria criteria) {
        return "{contentId: #}";
    }

    @Override
    public Object[] getParameters(EventMemberCriteria criteria, Object... arguments) {
        Object[] parameters = new Object[1];
        parameters[0] = criteria.getEventId();
        return parameters;
    }

    @Override
    public String getProjection(EventMemberCriteria criteria) {
        return null;
    }

    @Override
    public String getHint(EventMemberCriteria criteria) {
        return null;
    }

    @Override
    public String getSort(EventMemberCriteria criteria) {
        return null;
    }
}

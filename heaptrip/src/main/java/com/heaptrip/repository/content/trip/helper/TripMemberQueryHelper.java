package com.heaptrip.repository.content.trip.helper;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.trip.TripMember;
import com.heaptrip.domain.service.content.trip.criteria.TripMemberCriteria;
import com.heaptrip.repository.helper.AbstractQueryHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripMemberQueryHelper extends AbstractQueryHelper<TripMemberCriteria, TripMember> {

    @Override
    protected Class<TripMember> getCollectionClass() {
        return TripMember.class;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.MEMBERS.getName();
    }

    @Override
    public Class<TripMemberCriteria> getCriteriaClass() {
        return TripMemberCriteria.class;
    }

    @Override
    public String getQuery(TripMemberCriteria criteria) {
        String query;
        if (StringUtils.isNotEmpty(criteria.getUserId())) {
            query = "{userId: #, contentId: #";
        } else {
            query = "{contentId: #";
        }
        if (StringUtils.isNotEmpty(criteria.getTableId())) {
            query += ", tableId: #";
        }
        query += "}";
        return query;
    }

    @Override
    public Object[] getParameters(TripMemberCriteria criteria, Object... arguments) {
        List<Object> parameters = new ArrayList<>();
        if (StringUtils.isNotEmpty(criteria.getUserId())) {
            parameters.add(criteria.getUserId());
        }
        parameters.add(criteria.getTripId());
        if (StringUtils.isNotEmpty(criteria.getTableId())) {
            parameters.add(criteria.getTableId());
        }
        return parameters.toArray();
    }

    @Override
    public String getProjection(TripMemberCriteria criteria) {
        return null;
    }

    @Override
    public String getHint(TripMemberCriteria criteria) {
        if (StringUtils.isNotEmpty(criteria.getUserId())) {
            return "{userId: 1, contentId: 1}";
        } else {
            return "{contentId: 1, tableId: 1}";
        }
    }

    @Override
    public String getSort(TripMemberCriteria criteria) {
        return null;
    }
}

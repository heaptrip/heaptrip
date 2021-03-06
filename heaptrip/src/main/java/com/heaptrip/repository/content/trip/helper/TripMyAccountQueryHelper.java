package com.heaptrip.repository.content.trip.helper;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import com.heaptrip.domain.service.content.trip.criteria.TripMyAccountCriteria;
import com.heaptrip.repository.content.helper.ContentQueryHelper;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripMyAccountQueryHelper extends ContentQueryHelper<TripMyAccountCriteria, Trip> {

    @Override
    public String getQuery(TripMyAccountCriteria criteria) {
        String query = "{";
        if (criteria.getRelation().equals(RelationEnum.OWN)) {
            // OWNER
            query += "'ownerId': #, _class: #, deleted: null";
            if (ArrayUtils.isNotEmpty(criteria.getStatus())) {
                query += ", 'status.value': {$in: #}";
            }
        } else {
            // FAVORITES || MEMBER
            query += "_id: {$in: #}, _class: #, allowed: {$in: #}";
        }
        if (criteria.getCategories() != null && ArrayUtils.isNotEmpty(criteria.getCategories().getIds())) {
            query += ", categoryIds: {$in: #}";
        }
        if (criteria.getRegions() != null && ArrayUtils.isNotEmpty(criteria.getRegions().getIds())) {
            query += ", regionIds: {$in: #}";
        }
        if (criteria.getPeriod() != null) {
            if (criteria.getPeriod().getDateBegin() != null && criteria.getPeriod().getDateEnd() != null) {
                query += ", 'table.begin': {$gte: #, $lte: #}";
            } else if (criteria.getPeriod().getDateBegin() != null) {
                query += ", 'table.begin': {$gte: #}";
            } else if (criteria.getPeriod().getDateEnd() != null) {
                query += ", 'table.begin': {$lte: #}";
            }
        }
        query += "}";
        return query;
    }

    @Override
    public Object[] getParameters(TripMyAccountCriteria criteria, Object... objects) {
        List<Object> parameters = new ArrayList<>();
        if (criteria.getRelation().equals(RelationEnum.OWN)) {
            // OWNER
            parameters.add(criteria.getUserId());
            parameters.add(criteria.getContentType().getClazz());
            // status
            if (ArrayUtils.isNotEmpty(criteria.getStatus())) {
                parameters.add(criteria.getStatus());
            }
        } else {
            // FAVORITES || MEMBER
            // id list
            Assert.notNull(objects);
            Assert.notNull(objects[0]);
            parameters.add(objects[0]);
            // class
            parameters.add(criteria.getContentType().getClazz());
            // allowed
            List<String> allowed = new ArrayList<>();
            allowed.add(Content.ALLOWED_ALL_USERS);
            allowed.add(criteria.getUserId());
            parameters.add(allowed);
        }
        // categories
        if (criteria.getCategories() != null && ArrayUtils.isNotEmpty(criteria.getCategories().getIds())) {
            parameters.add(criteria.getCategories().getIds());
        }
        // regions
        if (criteria.getRegions() != null && ArrayUtils.isNotEmpty(criteria.getRegions().getIds())) {
            parameters.add(criteria.getRegions().getIds());
        }
        // period
        if (criteria.getPeriod() != null) {
            if (criteria.getPeriod().getDateBegin() != null) {
                parameters.add(criteria.getPeriod().getDateBegin());
            }
            if (criteria.getPeriod().getDateEnd() != null) {
                parameters.add(criteria.getPeriod().getDateEnd());
            }
        }
        return parameters.toArray();
    }

    @Override
    public Class<TripMyAccountCriteria> getCriteriaClass() {
        return TripMyAccountCriteria.class;
    }

    @Override
    protected Class<Trip> getCollectionClass() {
        return Trip.class;
    }

}

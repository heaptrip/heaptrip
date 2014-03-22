package com.heaptrip.repository.content.trip.helper;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.service.content.trip.criteria.TripFeedCriteria;
import com.heaptrip.repository.content.helper.ContentQueryHelper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class TripFeedQueryHelper extends ContentQueryHelper<TripFeedCriteria> {

    @Override
    public String getQuery(TripFeedCriteria criteria) {
        String query = "{_class: #, allowed: {$in: #}";
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
    public Object[] getParameters(TripFeedCriteria criteria, Object... objects) {
        List<Object> parameters = new ArrayList<>();
        // _class
        parameters.add(criteria.getContentType().getClazz());
        // allowed
        List<String> allowed = new ArrayList<>();
        allowed.add(Content.ALLOWED_ALL_USERS);
        if (StringUtils.isNotBlank(criteria.getUserId())) {
            allowed.add(criteria.getUserId());
        }
        parameters.add(allowed);
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
    public String getHint(TripFeedCriteria criteria) {
        if (criteria.getSort() != null) {
            switch (criteria.getSort()) {
                case RATING:
                    return "{_class: 1, 'rating.value': 1, allowed: 1}";
                default:
                    return "{_class: 1, created: 1, allowed: 1}";
            }
        } else {
            return "{_class: 1, created: 1, allowed: 1}";
        }
    }

    @Override
    public Class<TripFeedCriteria> getCriteriaClass() {
        return TripFeedCriteria.class;
    }

}

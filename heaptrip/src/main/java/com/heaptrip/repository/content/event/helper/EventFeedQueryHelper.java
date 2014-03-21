package com.heaptrip.repository.content.event.helper;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.service.content.event.criteria.EventFeedCriteria;
import com.heaptrip.repository.content.helper.ContentQueryHelper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventFeedQueryHelper extends ContentQueryHelper<EventFeedCriteria> {

    @Override
    public String getQuery(EventFeedCriteria criteria) {
        String query = "{";
        if (criteria.getContentType() != null) {
            query += "_class: #, allowed: {$in: #}";
        } else {
            query += "allowed: {$in: #}";
        }
        if (criteria.getCategories() != null && ArrayUtils.isNotEmpty(criteria.getCategories().getIds())) {
            query += ", categoryIds: {$in: #}";
        }
        if (criteria.getRegions() != null && ArrayUtils.isNotEmpty(criteria.getRegions().getIds())) {
            query += ", regionIds: {$in: #}";
        }
        if (criteria.getTypes() != null && ArrayUtils.isNotEmpty(criteria.getTypes().getIds())) {
            query += ", 'types._id': {$in: #}";
        }
        query += "}";
        return query;
    }

    @Override
    public Object[] getParameters(EventFeedCriteria criteria, Object... objects) {
        List<Object> parameters = new ArrayList<>();
        // _class
        if (criteria.getContentType() != null) {
            parameters.add(criteria.getContentType().getClazz());
        }
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
        // types
        if (criteria.getTypes() != null && ArrayUtils.isNotEmpty(criteria.getTypes().getIds())) {
            parameters.add(criteria.getTypes().getIds());
        }
        return parameters.toArray();
    }

    @Override
    public String getHint(EventFeedCriteria criteria) {
        if (criteria.getContentType() != null) {
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
        } else {
            if (criteria.getSort() != null) {
                switch (criteria.getSort()) {
                    case RATING:
                        return "{'rating.value': 1, allowed: 1}";
                    default:
                        return "{created: 1, allowed: 1}";
                }
            } else {
                return "{created: 1, allowed: 1}";
            }
        }
    }

    @Override
    public Class<EventFeedCriteria> getCriteriaClass() {
        return EventFeedCriteria.class;
    }
}

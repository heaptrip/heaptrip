package com.heaptrip.repository.content.helper;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentFeedQueryHelper extends ContentQueryHelper<FeedCriteria, Content> {

    @Override
    public String getQuery(FeedCriteria criteria) {
        String query = "{";
        if (criteria.getContentType() != null) {
            query += "allowed: {$in: #}, _class: #";
        } else {
            query += "allowed: {$in: #}";
        }
        if (criteria.getCategories() != null && ArrayUtils.isNotEmpty(criteria.getCategories().getIds())) {
            query += ", categoryIds: {$in: #}";
        }
        if (criteria.getRegions() != null && ArrayUtils.isNotEmpty(criteria.getRegions().getIds())) {
            query += ", regionIds: {$in: #}";
        }
        query += "}";
        return query;
    }

    @Override
    public Object[] getParameters(FeedCriteria criteria, Object... objects) {
        List<Object> parameters = new ArrayList<>();
        // allowed
        List<String> allowed = new ArrayList<>();
        allowed.add(Content.ALLOWED_ALL_USERS);
        if (StringUtils.isNotBlank(criteria.getUserId())) {
            allowed.add(criteria.getUserId());
        }
        parameters.add(allowed);
        // _class
        if (criteria.getContentType() != null) {
            parameters.add(criteria.getContentType().getClazz());
        }
        // categories
        if (criteria.getCategories() != null && ArrayUtils.isNotEmpty(criteria.getCategories().getIds())) {
            parameters.add(criteria.getCategories().getIds());
        }
        // regions
        if (criteria.getRegions() != null && ArrayUtils.isNotEmpty(criteria.getRegions().getIds())) {
            parameters.add(criteria.getRegions().getIds());
        }
        return parameters.toArray();
    }

    @Override
    public String getHint(FeedCriteria criteria) {
        if (criteria.getSort() != null) {
            switch (criteria.getSort()) {
                case RATING:
                    return "{allowed: 1, 'rating.value': -1, _class: 1}";
                default:
                    return "{allowed: 1, created: -1, _class: 1}";
            }
        } else {
            return "{allowed: 1, created: -1, _class: 1}";
        }
    }

    @Override
    public Class<FeedCriteria> getCriteriaClass() {
        return FeedCriteria.class;
    }

    @Override
    protected Class<Content> getCollectionClass() {
        return Content.class;
    }
}

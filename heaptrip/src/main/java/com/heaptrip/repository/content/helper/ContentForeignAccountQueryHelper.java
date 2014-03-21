package com.heaptrip.repository.content.helper;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.service.content.criteria.ForeignAccountCriteria;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentForeignAccountQueryHelper extends ContentQueryHelper<ForeignAccountCriteria> {

    @Override
    public String getQuery(ForeignAccountCriteria criteria) {
        String query = "{";
        if (criteria.getRelation().equals(RelationEnum.OWN)) {
            // OWNER
            query += "'ownerId': #, _class: #";
        } else {
            // FAVORITES
            query += "_id: {$in: #}, _class: #";
        }
        query += ", allowed: {$in: #}";
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
    public Object[] getParameters(ForeignAccountCriteria criteria, Object... objects) {
        List<Object> parameters = new ArrayList<>();
        if (criteria.getRelation().equals(RelationEnum.OWN)) {
            // owner
            parameters.add(criteria.getAccountId());
            // clazz
            parameters.add(criteria.getContentType().getClazz());
        } else {
            // FAVORITES || MEMBER
            // id list
            Assert.notNull(objects);
            Assert.notNull(objects[0]);
            parameters.add(objects[0]);
            // class
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
        return parameters.toArray();
    }

    @Override
    public String getHint(ForeignAccountCriteria criteria) {
        if (criteria.getRelation().equals(RelationEnum.OWN)) {
            // OWNER
            if (criteria.getSort() != null) {
                switch (criteria.getSort()) {
                    case RATING:
                        return "{'ownerId': 1, _class: 1, 'rating.value': 1}";
                    default:
                        return "{'ownerId': 1, _class: 1, created: 1}";
                }
            } else {
                return "{'ownerId': 1, _class: 1, created: 1}";
            }
        } else {
            // FAVORITES
            return "{_id: 1}";
        }
    }

    @Override
    public Class<ForeignAccountCriteria> getCriteriaClass() {
        return ForeignAccountCriteria.class;
    }
}

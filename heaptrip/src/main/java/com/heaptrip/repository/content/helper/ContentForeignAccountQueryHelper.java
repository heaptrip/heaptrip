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
public class ContentForeignAccountQueryHelper extends ContentQueryHelper<ForeignAccountCriteria, Content> {

    @Override
    public String getQuery(ForeignAccountCriteria criteria) {
        String query = "{";
        if (criteria.getRelation().equals(RelationEnum.OWN)) {
            // OWNER
            query += "'ownerId': #";
        } else {
            // FAVORITES
            query += "_id: {$in: #}";
        }
        if (criteria.getContentType() != null) {
            query += ", _class: #";
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

        } else {
            // FAVORITES || MEMBER
            // id list
            Assert.notNull(objects);
            Assert.notNull(objects[0]);
            parameters.add(objects[0]);
        }
        // clazz
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
        return parameters.toArray();
    }

    @Override
    public Class<ForeignAccountCriteria> getCriteriaClass() {
        return ForeignAccountCriteria.class;
    }

    @Override
    protected Class<Content> getCollectionClass() {
        return Content.class;
    }
}


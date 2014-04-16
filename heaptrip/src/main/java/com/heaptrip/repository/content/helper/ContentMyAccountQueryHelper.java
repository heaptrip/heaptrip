package com.heaptrip.repository.content.helper;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.service.content.criteria.MyAccountCriteria;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentMyAccountQueryHelper extends ContentQueryHelper<MyAccountCriteria, Content> {

    @Override
    public String getQuery(MyAccountCriteria criteria) {
        String query = "{";
        if (criteria.getRelation().equals(RelationEnum.OWN)) {
            // OWNER
            query += "'ownerId': #, _class: #";
            if (ArrayUtils.isNotEmpty(criteria.getStatus())) {
                query += ", 'status.value': {$in: #}";
            }
        } else {
            // FAVORITES
            query += "_id: {$in: #}, _class: #, allowed: {$in: #}";
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
    public Object[] getParameters(MyAccountCriteria criteria, Object... objects) {
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
        return parameters.toArray();
    }

    @Override
    public Class<MyAccountCriteria> getCriteriaClass() {
        return MyAccountCriteria.class;
    }

    @Override
    protected Class<Content> getCollectionClass() {
        return Content.class;
    }

}

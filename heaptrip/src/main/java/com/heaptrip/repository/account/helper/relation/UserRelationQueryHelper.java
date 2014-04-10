package com.heaptrip.repository.account.helper.relation;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.service.account.criteria.relation.UserRelationCriteria;
import com.heaptrip.repository.helper.AbstractQueryHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRelationQueryHelper extends AbstractQueryHelper<UserRelationCriteria, Relation> {
    @Override
    protected Class<Relation> getCollectionClass() {
        return Relation.class;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.RELATIONS.getName();
    }

    @Override
    public Class<UserRelationCriteria> getCriteriaClass() {
        return UserRelationCriteria.class;
    }

    @Override
    public String getQuery(UserRelationCriteria criteria) {
        String query = null;

        if (criteria.getToId() != null) {
            query = "{toId: #";
        }

        if (criteria.getRelationTypes() != null) {
            if (criteria.getRelationTypes().length == 1) {
                query = ((query == null) ? "{type: #" : query + ", type: #");
            } else if (criteria.getRelationTypes().length > 1) {
                query = ((query == null) ? "{type: #" : query + ", type: {$in: #}");
            }
        }

        query = ((query == null) ? "{}" : query + "}");

        return query;
    }

    @Override
    public Object[] getParameters(UserRelationCriteria criteria, Object... arguments) {
        List<Object> parameters = new ArrayList<>();

        if (criteria.getToId() != null) {
            parameters.add(criteria.getToId());
        }

        if (criteria.getRelationTypes() != null) {
            if (criteria.getRelationTypes().length == 1) {
                parameters.add(criteria.getRelationTypes()[0]);
            } else if (criteria.getRelationTypes().length > 1) {
                parameters.add(criteria.getRelationTypes());
            }
        }

        return parameters.toArray();
    }

    @Override
    public String getProjection(UserRelationCriteria criteria) {
        return null;
    }

    @Override
    public String getHint(UserRelationCriteria criteria) {
        return "{toId: 1, type: 1}";
    }

    @Override
    public String getSort(UserRelationCriteria criteria) {
        return null;
    }
}

package com.heaptrip.repository.account.helper.relation;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.service.account.criteria.relation.RelationCriteria;
import com.heaptrip.repository.helper.AbstractQueryHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RelationQueryHelper extends AbstractQueryHelper<RelationCriteria, Relation> {

    @Override
    public String getQuery(RelationCriteria criteria) {
        String query = null;

        if (criteria.getFromId() != null) {
            query = "{fromId: #";
        }

        if (criteria.getToId() != null) {
            query = ((query == null) ? "{userIds: #" : query + ", userIds: #");
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
    public Object[] getParameters(RelationCriteria criteria, Object... arguments) {
        List<Object> parameters = new ArrayList<>();

        if (criteria.getFromId() != null) {
            parameters.add(criteria.getFromId());
        }

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
    public String getProjection(RelationCriteria criteria) {
//        return "{_id: 1}";
        return null;
    }

    @Override
    public String getHint(RelationCriteria criteria) {
        return null;
    }

    @Override
    public String getSort(RelationCriteria criteria) {
        return null;
    }

    @Override
    public Class<RelationCriteria> getCriteriaClass() {
        return RelationCriteria.class;
    }

    @Override
    protected Class<Relation> getCollectionClass() {
        return Relation.class;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.RELATIONS.getName();
    }
}

package com.heaptrip.repository.account.helper.relation;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.service.account.criteria.relation.AccountRelationCriteria;
import com.heaptrip.repository.helper.AbstractQueryHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountRelationQueryHelper extends AbstractQueryHelper<AccountRelationCriteria, Relation> {
    @Override
    protected Class<Relation> getCollectionClass() {
        return Relation.class;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.RELATIONS.getName();
    }

    @Override
    public Class<AccountRelationCriteria> getCriteriaClass() {
        return AccountRelationCriteria.class;
    }

    @Override
    public String getQuery(AccountRelationCriteria criteria) {
        String query = null;

        if (criteria.getFromId() != null) {
            query = "{fromId: #";
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
    public Object[] getParameters(AccountRelationCriteria criteria, Object... arguments) {
        List<Object> parameters = new ArrayList<>();

        if (criteria.getFromId() != null) {
            parameters.add(criteria.getFromId());
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
    public String getProjection(AccountRelationCriteria criteria) {
        return null;
    }

    @Override
    public String getHint(AccountRelationCriteria criteria) {
        return "{fromId: 1, type: 1}";
    }

    @Override
    public String getSort(AccountRelationCriteria criteria) {
        return null;
    }
}

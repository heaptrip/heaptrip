package com.heaptrip.repository.account.relation;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.repository.account.relation.RelationRepository;
import com.heaptrip.domain.service.account.criteria.RelationCriteria;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.repository.helper.QueryHelper;
import com.heaptrip.repository.helper.QueryHelperFactory;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RelationRepositoryImpl extends CrudRepositoryImpl<Relation> implements RelationRepository {

    protected static final Logger logger = LoggerFactory.getLogger(RelationRepositoryImpl.class);

    @Autowired
    private QueryHelperFactory queryHelperFactory;

    @Override
    protected Class<Relation> getCollectionClass() {
        return Relation.class;
    }

    @Override
    protected String getCollectionName() {
        return CollectionEnum.RELATIONS.getName();
    }

    @Override
    public void delete(RelationCriteria criteria) {
        QueryHelper<RelationCriteria, Relation> queryHelper = queryHelperFactory.getHelperByCriteria(criteria);

        MongoCollection coll = getCollection();
        String query = queryHelper.getQuery(criteria);
        Object[] parameters = queryHelper.getParameters(criteria);

        coll.remove(query,parameters);
    }

    @Override
    public List<Relation> findByCriteria(RelationCriteria criteria) {
        QueryHelper<RelationCriteria, Relation> queryHelper = queryHelperFactory.getHelperByCriteria(criteria);
        return queryHelper.findByCriteria(criteria);
    }

    @Override
    public long countByCriteria(RelationCriteria criteria) {
        QueryHelper<RelationCriteria, Relation> queryHelper = queryHelperFactory.getHelperByCriteria(criteria);
        return queryHelper.findByCriteria(criteria).size();
    }
}

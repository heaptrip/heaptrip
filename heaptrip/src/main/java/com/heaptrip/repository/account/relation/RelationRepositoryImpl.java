package com.heaptrip.repository.account.relation;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.entity.account.relation.RelationTypeEnum;
import com.heaptrip.domain.repository.account.relation.RelationRepository;
import com.heaptrip.domain.service.account.criteria.relation.AccountRelationCriteria;
import com.heaptrip.domain.service.account.criteria.relation.RelationCriteria;
import com.heaptrip.domain.service.account.criteria.relation.UserRelationCriteria;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.repository.helper.QueryHelper;
import com.heaptrip.repository.helper.QueryHelperFactory;
import com.mongodb.WriteResult;
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

//    @Override
//    public void delete(RelationCriteria criteria) {
//        QueryHelper<RelationCriteria, Relation> queryHelper = queryHelperFactory.getHelperByCriteria(criteria);
//
//        MongoCollection coll = getCollection();
//        String query = queryHelper.getQuery(criteria);
//        Object[] parameters = queryHelper.getParameters(criteria);
//
//        coll.remove(query,parameters);
//    }

    @Override
    public void add(String fromId, String toId, RelationTypeEnum typeRelation) {
        MongoCollection coll = getCollection();
        Relation relation = coll.findOne("{fromId: #, type: #}", fromId, typeRelation.toString()).as(Relation.class);

        if (relation == null) {
            relation = new Relation();
            relation.setFromId(fromId);
            String[] userIds = new String[1];
            userIds[0] = toId;
            relation.setUserIds(userIds);
            relation.setType(typeRelation);

            save(relation);
        } else {
            WriteResult wr = coll.update("{_id: #}", relation.getId()).with("{$addToSet :{'userIds' : #}}", toId);
            logger.debug("WriteResult for add toId: ", wr);
        }

//
//        String query = "{fromId: #, type: #}";
//        WriteResult wr = coll.update(query, fromId, typeRelation).with(updateQuery, toId);
//        logger.debug("WriteResult for add toId: ", wr);
    }

    @Override
    public void remove(String fromId, String toId, RelationTypeEnum typeRelation) {
        MongoCollection coll = getCollection();
        String query = "{fromId: #, type: #}";
        String updateQuery = "{$pull :{'userIds' : #}}";
        WriteResult wr = coll.update(query, fromId, typeRelation).with(updateQuery, toId);
        logger.debug("WriteResult for remove toId: ", wr);
    }

    @Override
    public List<Relation> findByAccountRelationCriteria(AccountRelationCriteria criteria) {
        QueryHelper<AccountRelationCriteria, Relation> queryHelper = queryHelperFactory.getHelperByCriteria(criteria);
        return queryHelper.findByCriteria(criteria);
    }

    @Override
    public List<Relation> findByUserRelationCriteria(UserRelationCriteria criteria) {
        QueryHelper<UserRelationCriteria, Relation> queryHelper = queryHelperFactory.getHelperByCriteria(criteria);
        return queryHelper.findByCriteria(criteria);
    }

//    @Override
//    public Relation findByCommunityRelationCriteria(CommunityRelationCriteria criteria) {
//        QueryHelper<CommunityRelationCriteria, Relation> queryHelper = queryHelperFactory.getHelperByCriteria(criteria);
//        List<Relation> relations = queryHelper.findByCriteria(criteria);
//        return ((relations != null && relations.size() > 0) ? relations.get(0) : null);
//    }
//
//    @Override
//    public List<Relation> findByCriteria(RelationCriteria criteria) {
//        QueryHelper<RelationCriteria, Relation> queryHelper = queryHelperFactory.getHelperByCriteria(criteria);
//        return queryHelper.findByCriteria(criteria);
//    }

    @Override
    public long countByRelationCriteria(RelationCriteria criteria) {
        QueryHelper<RelationCriteria, Relation> queryHelper = queryHelperFactory.getHelperByCriteria(criteria);
        return queryHelper.findByCriteria(criteria).size();
    }
}

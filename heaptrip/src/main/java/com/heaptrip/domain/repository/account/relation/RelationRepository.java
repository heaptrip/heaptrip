package com.heaptrip.domain.repository.account.relation;

import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.entity.account.relation.RelationTypeEnum;
import com.heaptrip.domain.repository.CrudRepository;
import com.heaptrip.domain.service.account.criteria.relation.AccountRelationCriteria;
import com.heaptrip.domain.service.account.criteria.relation.RelationCriteria;
import com.heaptrip.domain.service.account.criteria.relation.UserRelationCriteria;

import java.util.List;

public interface RelationRepository extends CrudRepository<Relation> {

    public void add(String fromId, String toId, RelationTypeEnum typeRelation);

    public void remove(String fromId, String toId, RelationTypeEnum typeRelation);

    public List<Relation> findByAccountRelationCriteria(AccountRelationCriteria criteria);

    public List<Relation> findByUserRelationCriteria(UserRelationCriteria criteria);

    public List<Relation> findByRelationCriteria(RelationCriteria criteria);

    public long countByRelationCriteria(RelationCriteria criteria);
}

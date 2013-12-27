package com.heaptrip.domain.repository.account.relation;

import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.repository.CrudRepository;
import com.heaptrip.domain.service.account.criteria.RelationCriteria;

import java.util.List;

public interface RelationRepository extends CrudRepository<Relation> {

    public void delete(RelationCriteria criteria);

    public List<Relation> findByCriteria(RelationCriteria criteria);
}

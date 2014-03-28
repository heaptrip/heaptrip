package com.heaptrip.repository.helper;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.service.criteria.Criteria;

import java.util.List;

public interface QueryHelper<T extends Criteria, M extends BaseObject> {

    public Class<T> getCriteriaClass();

    public String getQuery(T criteria);

    public Object[] getParameters(T criteria, Object... arguments);

    public String getProjection(T criteria);

    public String getHint(T criteria);

    public String getSort(T criteria);

    public List<M> findByCriteria(T criteria, Object... arguments);

    public long countByCriteria(T criteria, Object... arguments);
}

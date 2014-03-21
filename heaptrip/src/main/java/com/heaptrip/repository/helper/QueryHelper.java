package com.heaptrip.repository.helper;

import com.heaptrip.domain.service.criteria.Criteria;

public interface QueryHelper<T extends Criteria> {

    public Class<T> getCriteriaClass();

    public String getQuery(T criteria);

    public Object[] getParameters(T criteria, Object... arguments);

    public String getProjection(T criteria);

    public String getHint(T criteria);

    public String getSort(T criteria);
}

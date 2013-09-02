package com.heaptrip.repository.account.helper;

import com.heaptrip.domain.service.account.criteria.Criteria;

public interface QueryHelper<T extends Criteria> {
	
	public String getQuery(T criteria);

	public Object[] getParameters(T criteria);

	public String getHint(T criteria);
	
	public String getSort(T criteria);
}

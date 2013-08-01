package com.heaptrip.repository.trip.helper;

import java.util.Locale;

import com.heaptrip.domain.service.content.criteria.ContentCriteria;
import com.heaptrip.domain.service.content.criteria.ContentSortEnum;

public interface QueryHelper<T extends ContentCriteria> {

	public String getQuery(T criteria);

	public Object[] getParameters(T criteria, Object... objects);

	public String getSort(ContentSortEnum sort);

	public String getProjection(Locale locale);

	public String getHint(T criteria);
}

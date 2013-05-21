package com.heaptrip.repository.trip.helper;

import com.heaptrip.domain.service.ContentSortEnum;
import com.heaptrip.domain.service.trip.TripCriteria;

public interface QueryHelper {

	public String getQuery(TripCriteria criteria);

	public Object[] getParameters(TripCriteria criteria, Object... objects);

	public String getSort(ContentSortEnum sort);

	public String getProjection(String lang);

	public String getHint(ContentSortEnum sort);
}

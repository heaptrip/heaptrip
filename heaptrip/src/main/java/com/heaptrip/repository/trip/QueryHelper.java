package com.heaptrip.repository.trip;

import com.heaptrip.domain.service.ContentSortEnum;
import com.heaptrip.domain.service.trip.TripCriteria;

public interface QueryHelper {

	public String getQuery(TripCriteria criteria);

	public Object[] getParameters(TripCriteria criteria);

	public String getSort(ContentSortEnum sort);

	public String getProjection(String lang);

	public String getHint(ContentSortEnum sort);
}

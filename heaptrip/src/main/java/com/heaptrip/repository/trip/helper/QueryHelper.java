package com.heaptrip.repository.trip.helper;

import java.util.Locale;

import com.heaptrip.domain.service.content.ContentSortEnum;
import com.heaptrip.domain.service.trip.TripCriteria;

public interface QueryHelper {

	public String getQuery(TripCriteria criteria);

	public Object[] getParameters(TripCriteria criteria, Object... objects);

	public String getSort(ContentSortEnum sort);

	public String getProjection(Locale locale);

	public String getHint(ContentSortEnum sort);
}

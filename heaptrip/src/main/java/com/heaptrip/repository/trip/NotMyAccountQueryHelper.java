package com.heaptrip.repository.trip;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.heaptrip.domain.service.ContentSortEnum;
import com.heaptrip.domain.service.trip.TripCriteria;

public class NotMyAccountQueryHelper extends AbstractQueryHelper {

	@Override
	public String getQuery(TripCriteria criteria) {
		String query = "{_class:'com.heaptrip.domain.entity.trip.Trip','owner._id':#,allowed:{$in:#}";
		if (ArrayUtils.isNotEmpty(criteria.getCategoryIds())) {
			query += ",categories._id:{$in:#}";
		}
		if (ArrayUtils.isNotEmpty(criteria.getRegionIds())) {
			query += ",regions._id:{$in:#}";
		}
		if (criteria.getPeriod() != null) {
			if (criteria.getPeriod().getDateBegin() != null) {
				query += ",'table.begin':{$gte:#}";
			}
			if (criteria.getPeriod().getDateEnd() != null) {
				query += ",'table.begin':{$lte:#}";
			}
		}
		query += "}";
		return query;
	}

	@Override
	public Object[] getParameters(TripCriteria criteria) {
		List<Object> parameters = new ArrayList<>();
		// owner
		parameters.add(criteria.getOwnerId());
		// allowed
		List<String> allowed = new ArrayList<>();
		allowed.add(ALL_USERS);
		if (StringUtils.isNotBlank(criteria.getUserId())) {
			allowed.add(criteria.getUserId());
		}
		parameters.add(allowed);
		// categories
		if (ArrayUtils.isNotEmpty(criteria.getCategoryIds())) {
			parameters.add(criteria.getCategoryIds());
		}
		// regions
		if (ArrayUtils.isNotEmpty(criteria.getRegionIds())) {
			parameters.add(criteria.getRegionIds());
		}
		// period
		if (criteria.getPeriod() != null) {
			if (criteria.getPeriod().getDateBegin() != null) {
				parameters.add(criteria.getPeriod().getDateBegin());
			}
			if (criteria.getPeriod().getDateEnd() != null) {
				parameters.add(criteria.getPeriod().getDateEnd());
			}
		}
		return parameters.toArray();
	}

	@Override
	public String getHint(ContentSortEnum sort) {
		if (sort != null) {
			switch (sort) {
			case RATING:
				return "{_class:1,'owner._id':1,rating:1}";
			default:
				return "{_class:1,'owner._id':1,created:1}";
			}
		} else {
			return "{_class:1,'owner._id:1',created:1}";
		}
	}

}

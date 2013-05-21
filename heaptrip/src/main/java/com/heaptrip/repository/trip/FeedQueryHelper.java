package com.heaptrip.repository.trip;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.heaptrip.domain.service.ContentSortEnum;
import com.heaptrip.domain.service.trip.TripCriteria;
import com.heaptrip.util.LanguageUtils;

class FeedQueryHelper extends AbstractQueryHelper {

	@Override
	public String getQuery(TripCriteria criteria) {
		String query = "{_class:'com.heaptrip.domain.entity.trip.Trip',allowed:{$in:#}";
		if (ArrayUtils.isNotEmpty(criteria.getCategoryIds())) {
			query += ",categories._id:{$in:#}";
		}
		if (ArrayUtils.isNotEmpty(criteria.getRegionIds())) {
			query += ",regions._id:{$in:#}";
		}
		query += ",langs:#";
		if (criteria.getPeriod() != null) {
			if (criteria.getPeriod().getDateBegin() != null && criteria.getPeriod().getDateEnd() != null) {
				query += ",'table.begin':{$gte:#, $lte:#}";
			} else if (criteria.getPeriod().getDateBegin() != null) {
				query += ",'table.begin':{$gte:#}";
			} else if (criteria.getPeriod().getDateEnd() != null) {
				query += ",'table.begin':{$lte:#}";
			}
		}
		query += "}";
		return query;
	}

	@Override
	public Object[] getParameters(TripCriteria criteria, Object... objects) {
		List<Object> parameters = new ArrayList<>();
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
		// lang
		String lang = LanguageUtils.getLanguageByLocale(criteria.getLocale());
		parameters.add(lang);
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
				return "{_class:1,rating:1,allowed:1}";
			default:
				return "{_class:1,created:1,allowed:1}";
			}
		} else {
			return "{_class:1,created:1,allowed:1}";
		}
	}
}

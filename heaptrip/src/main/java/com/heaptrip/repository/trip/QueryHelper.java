package com.heaptrip.repository.trip;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.heaptrip.domain.service.ContentSortEnum;
import com.heaptrip.domain.service.trip.TripCriteria;

class QueryHelper {

	private static String ALL_USERS = "0";

	static String createQueryByTripCriteria(TripCriteria criteria) {
		String query = "{_class:'com.heaptrip.domain.entity.trip.Trip',allowed:{$in:#}";
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

	static Object[] createParametersByTripCriteria(TripCriteria criteria) {
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

	static String getProjection(String lang) {
		String result = String.format("{_class:1,owner:1,'categories.name.%s':1,'regions.name.%s':1,"
				+ "'status':1,'name.%s':1,'summary.%s':1,'table._id':1,'table.begin':1, 'table.end':1,"
				+ "'table.price':1,photo:1,created:1,views:1,rating:1,comments:1}", lang, lang, lang, lang);
		return result;
	}

	static String getSort(ContentSortEnum sort) {
		if (sort != null) {
			switch (sort) {
			case RATING:
				return "{rating:1}";
			default:
				return "{created:1}";
			}
		} else {
			return "{created:1}";
		}
	}

	static String getHint(ContentSortEnum sort) {
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

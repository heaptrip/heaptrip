package com.heaptrip.repository.trip.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.heaptrip.domain.service.trip.criteria.TripFeedCriteria;

class FeedQueryHelper extends AbstractQueryHelper<TripFeedCriteria> {

	@Override
	public String getQuery(TripFeedCriteria criteria) {
		String query = "{_class: #, allowed: {$in: #}";
		if (ArrayUtils.isNotEmpty(criteria.getCategoryIds())) {
			query += ", categoryIds: {$in: #}";
		}
		if (ArrayUtils.isNotEmpty(criteria.getRegionIds())) {
			query += ", regionIds: {$in: #}";
		}
		if (criteria.getPeriod() != null) {
			if (criteria.getPeriod().getDateBegin() != null && criteria.getPeriod().getDateEnd() != null) {
				query += ", 'table.begin': {$gte: #, $lte: #}";
			} else if (criteria.getPeriod().getDateBegin() != null) {
				query += ", 'table.begin': {$gte: #}";
			} else if (criteria.getPeriod().getDateEnd() != null) {
				query += ", 'table.begin': {$lte: #}";
			}
		}
		query += "}";
		return query;
	}

	@Override
	public Object[] getParameters(TripFeedCriteria criteria, Object... objects) {
		List<Object> parameters = new ArrayList<>();
		// _class
		parameters.add(criteria.getContentType().getClazz());
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
	public String getHint(TripFeedCriteria criteria) {
		if (criteria.getSort() != null) {
			switch (criteria.getSort()) {
			case RATING:
				return "{_class: 1, rating: 1, allowed: 1}";
			default:
				return "{_class: 1, created: 1, allowed: 1}";
			}
		} else {
			return "{_class: 1, created: 1, allowed: 1}";
		}
	}

}

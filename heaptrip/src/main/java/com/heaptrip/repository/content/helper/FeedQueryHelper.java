package com.heaptrip.repository.content.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.heaptrip.domain.service.content.FeedCriteria;

class FeedQueryHelper extends AbstractQueryHelper<FeedCriteria> {

	@Override
	public String getQuery(FeedCriteria criteria) {
		String query = "{";
		if (criteria.getContentType() != null) {
			query += "_class: #, allowed: {$in: #}";
		} else {
			query += "allowed: {$in: #}";
		}
		if (ArrayUtils.isNotEmpty(criteria.getCategoryIds())) {
			query += ", allCategories: {$in: #}";
		}
		if (ArrayUtils.isNotEmpty(criteria.getRegionIds())) {
			query += ", allRegions: {$in: #}";
		}
		query += "}";
		return query;
	}

	@Override
	public Object[] getParameters(FeedCriteria criteria, Object... objects) {
		List<Object> parameters = new ArrayList<>();
		// _class
		if (criteria.getContentType() != null) {
			parameters.add(criteria.getContentType().getClazz());
		}
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
		return parameters.toArray();
	}

	@Override
	public String getHint(FeedCriteria criteria) {
		if (criteria.getContentType() != null) {
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
		} else {
			if (criteria.getSort() != null) {
				switch (criteria.getSort()) {
				case RATING:
					return "{rating: 1, allowed: 1}";
				default:
					return "{created: 1, allowed: 1}";
				}
			} else {
				return "{created: 1, allowed: 1}";
			}
		}
	}
}

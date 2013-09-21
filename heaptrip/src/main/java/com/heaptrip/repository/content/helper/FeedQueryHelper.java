package com.heaptrip.repository.content.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.heaptrip.domain.service.content.criteria.FeedCriteria;

class FeedQueryHelper extends AbstractQueryHelper<FeedCriteria> {

	@Override
	public String getQuery(FeedCriteria criteria) {
		String query = "{";
		if (criteria.getContentType() != null) {
			query += "_class: #, allowed: {$in: #}";
		} else {
			query += "allowed: {$in: #}";
		}
		if (criteria.getCategories() != null && ArrayUtils.isNotEmpty(criteria.getCategories().getIds())) {
			query += ", categoryIds: {$in: #}";
		}
		if (criteria.getRegions() != null && ArrayUtils.isNotEmpty(criteria.getRegions().getIds())) {
			query += ", regionIds: {$in: #}";
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
		if (criteria.getCategories() != null && ArrayUtils.isNotEmpty(criteria.getCategories().getIds())) {
			parameters.add(criteria.getCategories().getIds());
		}
		// regions
		if (criteria.getRegions() != null && ArrayUtils.isNotEmpty(criteria.getRegions().getIds())) {
			parameters.add(criteria.getRegions().getIds());
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

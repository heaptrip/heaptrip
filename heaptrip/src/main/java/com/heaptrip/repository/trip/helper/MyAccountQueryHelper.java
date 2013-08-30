package com.heaptrip.repository.trip.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.Assert;

import com.heaptrip.domain.service.content.criteria.RelationEnum;
import com.heaptrip.domain.service.trip.criteria.TripMyAccountCriteria;

public class MyAccountQueryHelper extends AbstractQueryHelper<TripMyAccountCriteria> {

	@Override
	public String getQuery(TripMyAccountCriteria criteria) {
		String query = "{";
		if (criteria.getRelation().equals(RelationEnum.OWN)) {
			// OWNER
			query += "_class: #, 'owner._id': #";
			if (ArrayUtils.isNotEmpty(criteria.getStatus())) {
				query += ", 'status.value': {$in: #}";
			}
		} else {
			// FAVORITES || MEMBER
			query += "_id: {$in: #}, _class: #, allowed: {$in: #}";
		}
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
	public Object[] getParameters(TripMyAccountCriteria criteria, Object... objects) {
		List<Object> parameters = new ArrayList<>();
		if (criteria.getRelation().equals(RelationEnum.OWN)) {
			// OWNER
			parameters.add(criteria.getContentType().getClazz());
			parameters.add(criteria.getUserId());
			// status
			if (ArrayUtils.isNotEmpty(criteria.getStatus())) {
				parameters.add(criteria.getStatus());
			}
		} else {
			// FAVORITES || MEMBER
			// id list
			Assert.notNull(objects);
			Assert.notNull(objects[0]);
			parameters.add(objects[0]);
			// class
			parameters.add(criteria.getContentType().getClazz());
			// allowed
			List<String> allowed = new ArrayList<>();
			allowed.add(ALL_USERS);
			allowed.add(criteria.getUserId());
			parameters.add(allowed);
		}
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
	public String getHint(TripMyAccountCriteria criteria) {
		if (criteria.getRelation().equals(RelationEnum.OWN)) {
			// OWNER
			if (criteria.getSort() != null) {
				switch (criteria.getSort()) {
				case RATING:
					return "{_class: 1, 'owner._id': 1, rating: 1}";
				default:
					return "{_class: 1, 'owner._id': 1, created: 1}";
				}
			} else {
				return "{_class: 1, 'owner._id': 1, created: 1}";
			}
		} else {
			// FAVORITES || MEMBER
			return "{_id: 1}";
		}
	}

}

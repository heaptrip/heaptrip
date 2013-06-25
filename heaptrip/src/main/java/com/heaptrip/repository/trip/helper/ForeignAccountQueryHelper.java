package com.heaptrip.repository.trip.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.heaptrip.domain.service.content.RelationEnum;
import com.heaptrip.domain.service.trip.ForeignAccountTripCriteria;

public class ForeignAccountQueryHelper extends AbstractQueryHelper<ForeignAccountTripCriteria> {

	@Override
	public String getQuery(ForeignAccountTripCriteria criteria) {
		String query = "{";
		if (criteria.getRelation().equals(RelationEnum.OWN)) {
			// OWNER
			query += "_class: #, 'owner._id': #";
		} else {
			// FAVORITES || MEMBER
			query += "_id: {$in: #}, _class: #";
		}
		query += ", allowed: {$in: #}";
		if (ArrayUtils.isNotEmpty(criteria.getCategoryIds())) {
			query += ", 'categories._id': {$in: #}";
		}
		if (ArrayUtils.isNotEmpty(criteria.getRegionIds())) {
			query += ", 'regions._id': {$in: #}";
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
	public Object[] getParameters(ForeignAccountTripCriteria criteria, Object... objects) {
		List<Object> parameters = new ArrayList<>();
		if (criteria.getRelation().equals(RelationEnum.OWN)) {
			// clazz
			parameters.add(criteria.getContentType().getClazz());
			// owner
			parameters.add(criteria.getOwnerId());
		} else {
			// FAVORITES || MEMBER
			// id list
			Assert.notNull(objects);
			Assert.notNull(objects[0]);
			parameters.add(objects[0]);
			// class
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
	public String getHint(ForeignAccountTripCriteria criteria) {
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
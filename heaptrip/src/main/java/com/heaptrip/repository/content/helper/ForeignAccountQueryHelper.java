package com.heaptrip.repository.content.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.heaptrip.domain.service.content.ForeignAccountCriteria;
import com.heaptrip.domain.service.content.RelationEnum;

public class ForeignAccountQueryHelper extends AbstractQueryHelper<ForeignAccountCriteria> {

	@Override
	public String getQuery(ForeignAccountCriteria criteria) {
		String query = "{";
		if (criteria.getRelation().equals(RelationEnum.OWN)) {
			// OWNER
			query += "_class: #, 'owner._id': #";
		} else {
			// FAVORITES
			query += "_id: {$in: #}, _class: #";
		}
		query += ", allowed: {$in: #}";
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
	public Object[] getParameters(ForeignAccountCriteria criteria, Object... objects) {
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
		return parameters.toArray();
	}

	@Override
	public String getHint(ForeignAccountCriteria criteria) {
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
			// FAVORITES
			return "{_id: 1}";
		}
	}
}

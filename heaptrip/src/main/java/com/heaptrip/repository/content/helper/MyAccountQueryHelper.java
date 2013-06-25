package com.heaptrip.repository.content.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.Assert;

import com.heaptrip.domain.service.content.MyAccountCriteria;
import com.heaptrip.domain.service.content.RelationEnum;

public class MyAccountQueryHelper extends AbstractQueryHelper<MyAccountCriteria> {

	@Override
	public String getQuery(MyAccountCriteria criteria) {
		String query = "{";
		if (criteria.getRelation().equals(RelationEnum.OWN)) {
			// OWNER
			query += "_class: #, 'owner._id': #";
			if (ArrayUtils.isNotEmpty(criteria.getStatus())) {
				query += ", 'status.value': {$in: #}";
			}
		} else {
			// FAVORITES
			query += "_id: {$in: #}, _class: #, allowed: {$in: #}";
		}
		if (ArrayUtils.isNotEmpty(criteria.getCategoryIds())) {
			query += ", 'categories._id': {$in: #}";
		}
		if (ArrayUtils.isNotEmpty(criteria.getRegionIds())) {
			query += ", 'regions._id': {$in: #}";
		}
		query += "}";
		return query;
	}

	@Override
	public Object[] getParameters(MyAccountCriteria criteria, Object... objects) {
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
		return parameters.toArray();
	}

	@Override
	public String getHint(MyAccountCriteria criteria) {
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
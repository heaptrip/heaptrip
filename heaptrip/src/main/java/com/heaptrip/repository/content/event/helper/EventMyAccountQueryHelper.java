package com.heaptrip.repository.content.event.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import com.heaptrip.domain.service.content.event.criteria.EventMyAccountCriteria;
import com.heaptrip.repository.content.helper.ContentQueryHelper;

@Service
public class EventMyAccountQueryHelper extends ContentQueryHelper<EventMyAccountCriteria> {

	@Override
	public String getQuery(EventMyAccountCriteria criteria) {
		String query = "{";
		if (criteria.getRelation().equals(RelationEnum.OWN)) {
			// OWNER
			query += "'owner._id': #, _class: #";
			if (ArrayUtils.isNotEmpty(criteria.getStatus())) {
				query += ", 'status.value': {$in: #}";
			}
		} else {
			// FAVORITES
			query += "_id: {$in: #}, _class: #, allowed: {$in: #}";
		}
		if (criteria.getCategories() != null && ArrayUtils.isNotEmpty(criteria.getCategories().getIds())) {
			query += ", categoryIds: {$in: #}";
		}
		if (criteria.getRegions() != null && ArrayUtils.isNotEmpty(criteria.getRegions().getIds())) {
			query += ", regionIds: {$in: #}";
		}
		if (criteria.getTypes() != null && ArrayUtils.isNotEmpty(criteria.getTypes().getIds())) {
			query += ", types: {$in: #}";
		}
		query += "}";
		return query;
	}

	@Override
	public Object[] getParameters(EventMyAccountCriteria criteria, Object... objects) {
		List<Object> parameters = new ArrayList<>();
		if (criteria.getRelation().equals(RelationEnum.OWN)) {
			// OWNER
			parameters.add(criteria.getUserId());
			parameters.add(criteria.getContentType().getClazz());
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
			allowed.add(Content.ALLOWED_ALL_USERS);
			allowed.add(criteria.getUserId());
			parameters.add(allowed);
		}
		// categories
		if (criteria.getCategories() != null && ArrayUtils.isNotEmpty(criteria.getCategories().getIds())) {
			parameters.add(criteria.getCategories().getIds());
		}
		// regions
		if (criteria.getRegions() != null && ArrayUtils.isNotEmpty(criteria.getRegions().getIds())) {
			parameters.add(criteria.getRegions().getIds());
		}
		// types
		if (criteria.getTypes() != null && ArrayUtils.isNotEmpty(criteria.getTypes().getIds())) {
			parameters.add(criteria.getTypes().getIds());
		}
		return parameters.toArray();
	}

	@Override
	public String getHint(EventMyAccountCriteria criteria) {
		if (criteria.getRelation().equals(RelationEnum.OWN)) {
			// OWNER
			if (criteria.getSort() != null) {
				switch (criteria.getSort()) {
				case RATING:
					return "{'owner._id': 1, _class: 1, 'rating.value': 1}";
				default:
					return "{'owner._id': 1, _class: 1, created: 1}";
				}
			} else {
				return "{'owner._id': 1, _class: 1, created: 1}";
			}
		} else {
			// FAVORITES
			return "{_id: 1}";
		}
	}

	@Override
	public Class<EventMyAccountCriteria> getCriteriaClass() {
		return EventMyAccountCriteria.class;
	}

}

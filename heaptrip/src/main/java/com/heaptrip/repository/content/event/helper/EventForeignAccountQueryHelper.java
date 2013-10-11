package com.heaptrip.repository.content.event.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import com.heaptrip.domain.service.content.event.criteria.EventForeignAccountCriteria;
import com.heaptrip.repository.content.helper.ContentQueryHelper;

@Service
public class EventForeignAccountQueryHelper extends ContentQueryHelper<EventForeignAccountCriteria> {

	@Override
	public String getQuery(EventForeignAccountCriteria criteria) {
		String query = "{";
		if (criteria.getRelation().equals(RelationEnum.OWN)) {
			// OWNER
			query += "'owner._id': #, _class: #";
		} else {
			// FAVORITES
			query += "_id: {$in: #}, _class: #";
		}
		query += ", allowed: {$in: #}";
		if (criteria.getCategories() != null && ArrayUtils.isNotEmpty(criteria.getCategories().getIds())) {
			query += ", categoryIds: {$in: #}";
		}
		if (criteria.getRegions() != null && ArrayUtils.isNotEmpty(criteria.getRegions().getIds())) {
			query += ", regionIds: {$in: #}";
		}
		if (criteria.getTypes() != null && ArrayUtils.isNotEmpty(criteria.getTypes().getIds())) {
			query += ", 'types._id': {$in: #}";
		}
		query += "}";
		return query;
	}

	@Override
	public Object[] getParameters(EventForeignAccountCriteria criteria, Object... objects) {
		List<Object> parameters = new ArrayList<>();
		if (criteria.getRelation().equals(RelationEnum.OWN)) {
			// owner
			parameters.add(criteria.getAccountId());
			// clazz
			parameters.add(criteria.getContentType().getClazz());
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
		allowed.add(Content.ALLOWED_ALL_USERS);
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
		// types
		if (criteria.getTypes() != null && ArrayUtils.isNotEmpty(criteria.getTypes().getIds())) {
			parameters.add(criteria.getTypes().getIds());
		}
		return parameters.toArray();
	}

	@Override
	public String getHint(EventForeignAccountCriteria criteria) {
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
	public Class<EventForeignAccountCriteria> getCriteriaClass() {
		return EventForeignAccountCriteria.class;
	}
}

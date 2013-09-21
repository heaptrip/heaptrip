package com.heaptrip.repository.content.trip.helper;

import org.springframework.util.Assert;

import com.heaptrip.domain.service.content.criteria.ContentSortCriteria;
import com.heaptrip.repository.helper.QueryHelper;
import com.heaptrip.util.language.LanguageUtils;

public abstract class TripQueryHelper<T extends ContentSortCriteria> implements QueryHelper<T> {

	protected static String ALL_USERS = "0";

	@Override
	public String getSort(T criteria) {
		String result = "{created:1}";
		if (criteria != null && criteria.getSort() != null) {
			switch (criteria.getSort()) {
			case RATING:
				return "{'rating.value':1}";
			default:
				return "{created:1}";
			}
		}
		return result;
	}

	@Override
	public String getProjection(T criteria) {
		Assert.notNull(criteria.getLocale(), "locale must not be null");
		String lang = LanguageUtils.getLanguageByLocale(criteria.getLocale());
		String result = String
				.format("{_class: 1, owner: 1, 'categories._id': 1, 'categories.name.%s': 1, 'regions._id': 1, 'regions.name.%s': 1, status: 1,"
						+ " 'name.%s': 1, 'name.main': 1, 'summary.%s': 1, 'summary.main': 1, 'table._id': 1, 'table.begin': 1, 'table.end': 1,"
						+ " 'table.price': 1, image: 1, created: 1, owners:1, 'views.count': 1, mainLang: 1, rating: 1, comments: 1}",
						lang, lang, lang, lang);
		return result;
	}

}

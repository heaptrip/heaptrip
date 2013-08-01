package com.heaptrip.repository.content.helper;

import java.util.Locale;

import com.heaptrip.domain.service.content.criteria.ContentCriteria;
import com.heaptrip.domain.service.content.criteria.ContentSortEnum;
import com.heaptrip.util.LanguageUtils;

public abstract class AbstractQueryHelper<T extends ContentCriteria> implements QueryHelper<T> {

	protected static String ALL_USERS = "0";

	@Override
	public String getSort(ContentSortEnum sort) {
		if (sort != null) {
			switch (sort) {
			case RATING:
				return "{rating:1}";
			default:
				return "{created:1}";
			}
		} else {
			return "{created:1}";
		}
	}

	@Override
	public String getProjection(Locale locale) {
		String lang = LanguageUtils.getLanguageByLocale(locale);
		String result = String
				.format("{_class: 1, owner: 1, 'categories._id': 1, 'categories.name.%s': 1, 'regions._id': 1, 'regions.name.%s': 1, status: 1,"
						+ " 'name.%s': 1, 'name.main': 1, 'summary.%s': 1, 'summary.main': 1, image: 1, created: 1, owners: 1, views: 1,"
						+ " mainLang: 1, rating: 1, comments: 1}", lang, lang, lang, lang);
		return result;
	}

}

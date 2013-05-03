package com.heaptrip.repository.trip;

import com.heaptrip.domain.service.ContentSortEnum;

public class AbstractQueryHelper {

	protected static String ALL_USERS = "0";

	static String getSort(ContentSortEnum sort) {
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

	static String getProjection(String lang) {
		String result = String.format("{_class:1,owner:1,'categories.name.%s':1,'regions.name.%s':1,"
				+ "'status':1,'name.%s':1,'summary.%s':1,'table._id':1,'table.begin':1, 'table.end':1,"
				+ "'table.price':1,photo:1,created:1,views:1,rating:1,comments:1}", lang, lang, lang, lang);
		return result;
	}

}

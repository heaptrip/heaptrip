package com.heaptrip.domain.service.criteria;

import java.util.Locale;

/**
 * 
 * Criteria for search data by locale
 * 
 */
public class LocaleCriteria extends Criteria {

	// locale
	private Locale locale;

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}

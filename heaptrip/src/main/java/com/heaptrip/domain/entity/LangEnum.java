package com.heaptrip.domain.entity;

/**
 * 
 * Enumeration of supported languages
 * 
 */
public enum LangEnum {
	RU("ru"), EN("en");

	private String value;

	private LangEnum(String language) {
		this.value = language;
	}

	public String getValue() {
		return value;
	}
}

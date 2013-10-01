package com.heaptrip.domain.entity;

import java.util.HashMap;
import java.util.Locale;

import com.heaptrip.util.language.LanguageUtils;

/**
 * 
 * MultiLangText is designed to work with multi-language text
 * 
 */
public class MultiLangText extends HashMap<String, String> {

	private static final long serialVersionUID = -248938959357861383L;

	private static final String MAIN_LANG = "main";

	public MultiLangText() {
		super();
	}

	public MultiLangText(String value) {
		super();
		setValue(value);
	}

	public MultiLangText(String value, Locale locale) {
		super();
		setValue(value, locale);
	}

	public MultiLangText(String textRu, String textEn) {
		super();
		put(LangEnum.RU.getValue(), textRu);
		put(LangEnum.EN.getValue(), textEn);
	}

	public String getValue(Locale locale) {
		String lang = LanguageUtils.getLanguageByLocale(locale);
		String value = get(lang);
		return (value == null) ? get(MAIN_LANG) : value;
	}

	public void setValue(String value, Locale locale) {
		String lang = LanguageUtils.getLanguageByLocale(locale);
		put(lang, value);
	}

	public void setMainLanguage(String lang) {
		put(MAIN_LANG, get(lang));
	}

	public String getValue() {
		return get(MAIN_LANG);
	}

	public void setValue(String value) {
		put(MAIN_LANG, value);
	}
}

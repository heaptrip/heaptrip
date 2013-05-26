package com.heaptrip.domain.entity;

import java.util.HashMap;
import java.util.Locale;

import com.heaptrip.util.LanguageUtils;

/**
 * 
 * MultiLangText is designed to work with multi-language text
 * 
 */
public class MultiLangText extends HashMap<String, String> {

	private static final long serialVersionUID = -248938959357861383L;

	public MultiLangText() {
		super();
	}

	public MultiLangText(String value, Locale locale) {
		super();
		setValue(value, locale);
	}

	public MultiLangText(String textRu, String textEn) {
		super();
		put(LangEnum.ru.toString(), textRu);
		put(LangEnum.en.toString(), textEn);
	}

	public String getValue(Locale locale) {
		String lang = LanguageUtils.getLanguageByLocale(locale);
		return get(lang);
	}

	public void setValue(String value, Locale locale) {
		String lang = LanguageUtils.getLanguageByLocale(locale);
		put(lang, value);
	}
}

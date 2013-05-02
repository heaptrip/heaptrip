package com.heaptrip.domain.entity;

import java.util.Locale;

public class MultiLangText {

	protected String textRu;

	protected String textEn;

	public MultiLangText() {
		super();
	}

	public MultiLangText(String textRu, String textEn) {
		super();
		this.textRu = textRu;
		this.textEn = textEn;
	}

	public MultiLangText(String value, Locale locale) {
		super();
		setValue(value, locale);
	}

	public String getTextRu() {
		return textRu;
	}

	public void setTextRu(String textRu) {
		this.textRu = textRu;
	}

	public String getTextEn() {
		return textEn;
	}

	public void setTextEn(String textEn) {
		this.textEn = textEn;
	}

	public String getValue(Locale locale) {
		if (locale.getLanguage() != null && locale.getLanguage().equals(LangEnum.ru.toString())) {
			return textRu;
		} else {
			return textEn;
		}
	}

	public void setValue(String value, Locale locale) {
		if (locale.getLanguage() != null && locale.getLanguage().equals(LangEnum.ru.toString())) {
			textRu = value;
		} else {
			textEn = value;
		}
	}
}

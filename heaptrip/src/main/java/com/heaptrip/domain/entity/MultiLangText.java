package com.heaptrip.domain.entity;

import com.heaptrip.util.language.LanguageUtils;

import java.util.HashMap;
import java.util.Locale;

/**
 * MultiLangText is designed to work with multi-language text
 */
public class MultiLangText extends HashMap<String, String> {

    private static final String MAIN_LANG = "mainLang";

    private static final String MAIN_TEXT = "mainText";

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

    public void setValue(String value, Locale locale) {
        String lang = LanguageUtils.getLanguageByLocale(locale);
        put(lang, value);
    }

    public String getValue(Locale locale) {
        String lang = LanguageUtils.getLanguageByLocale(locale);
        String value = get(lang);
        return (value == null) ? get(MAIN_TEXT) : value;
    }

    public String getValueByOnlyThisLocale(Locale locale) {
        String lang = LanguageUtils.getLanguageByLocale(locale);
        String mainLang = get(MAIN_LANG);
        if (lang != null && mainLang != null && mainLang.equals(lang)) {
            return get(MAIN_TEXT);
        } else {
            return get(lang);
        }
    }

    public void setValue(String value) {
        put(MAIN_TEXT, value);
    }

    public String getValue() {
        return get(MAIN_TEXT);
    }

    public void setMainLanguage(String lang) {
        put(MAIN_LANG, lang);
        put(MAIN_TEXT, get(lang));
        remove(lang); // main language stored only in main field
    }

    public String getMainLanguage() {
        return get(MAIN_LANG);
    }

    public int getCountLanguage() {
        return containsKey(MAIN_LANG) ? size() - 1 : size();
    }
}

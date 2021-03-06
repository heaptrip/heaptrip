package com.heaptrip.domain.entity.mail;

import com.heaptrip.util.language.LanguageUtils;

import java.util.Locale;
import java.util.Map;

public class MailTemplate {

    private Map<String, String> subject;

    private Map<String, String> text;

    public Map<String, String> getSubject() {
        return subject;
    }

    public void setSubject(Map<String, String> subject) {
        this.subject = subject;
    }

    public Map<String, String> getText() {
        return text;
    }

    public void setText(Map<String, String> text) {
        this.text = text;
    }

    public String getSubject(Locale locale) {
        String lang = LanguageUtils.getLanguageByLocale(locale);
        return subject.get(lang);
    }

    public String getText(Locale locale) {
        String lang = LanguageUtils.getLanguageByLocale(locale);
        return text.get(lang);
    }
}

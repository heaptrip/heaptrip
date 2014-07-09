package com.heaptrip.domain.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumeration of supported languages
 */
public enum LangEnum {
    RU("ru"), EN("en")
    //, DU("du"), FR("fr"), SW("sw")
    ;

    private String value;

    private LangEnum(String language) {
        this.value = language;
    }

    public String getValue() {
        return value;
    }

    public static String[] getValues() {
        List<String> result = new ArrayList<>();
        for (LangEnum value : values()) {
            result.add(value.getValue());

        }
        return result.toArray(new String[result.size()]);
    }
}

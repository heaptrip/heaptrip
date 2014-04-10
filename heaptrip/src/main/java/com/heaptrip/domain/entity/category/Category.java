package com.heaptrip.domain.entity.category;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.util.language.LanguageUtils;


/**
 * Category
 */
public class Category extends SimpleCategory {

    // id of the parent
    private String parent;

    // id list of all parents
    private String[] ancestors;

    public Category() {
        super();
    }

    public Category(String id, String parent, String[] ancestors, String nameRu, String nameEn) {
        super();
        this.id = id;
        this.parent = parent;
        this.ancestors = ancestors;
        MultiLangText name = new MultiLangText();
        name.setValue(nameRu, LanguageUtils.getRussianLocale());
        name.setValue(nameEn, LanguageUtils.getEnglishLocale());
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String[] getAncestors() {
        return ancestors;
    }

    public void setAncestors(String[] ancestors) {
        this.ancestors = ancestors;
    }
}

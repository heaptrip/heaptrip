package com.heaptrip.domain.entity.category;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.MultiLangText;

/**
 * Simple category
 */
public class SimpleCategory extends BaseObject {

    // multilingual name of the category
    protected MultiLangText name;

    public SimpleCategory() {
        super();
    }

    public SimpleCategory(String id) {
        super();
        this.id = id;
    }

    public MultiLangText getName() {
        return name;
    }

    public void setName(MultiLangText name) {
        this.name = name;
    }

}

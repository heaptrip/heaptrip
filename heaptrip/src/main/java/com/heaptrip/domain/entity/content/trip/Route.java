package com.heaptrip.domain.entity.content.trip;

import com.heaptrip.domain.entity.BaseObject;
import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.content.Map;

/**
 * Route of trip
 */
public class Route extends BaseObject {

    // route description
    private MultiLangText text;

    // route map
    private Map map;

    public MultiLangText getText() {
        return text;
    }

    public void setText(MultiLangText text) {
        this.text = text;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
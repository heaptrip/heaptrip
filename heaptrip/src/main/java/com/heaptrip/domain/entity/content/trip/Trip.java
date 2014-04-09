package com.heaptrip.domain.entity.content.trip;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;

/**
 * Trip
 */
public class Trip extends Content {

    // travel schedule
    private TableItem[] table;

    // route
    private Route route;

    // IDs of trip posts
    private String[] postIds;

    @Override
    public ContentEnum getContentType() {
        return ContentEnum.TRIP;
    }

    public TableItem[] getTable() {
        return table;
    }

    public void setTable(TableItem[] table) {
        this.table = table;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String[] getPostIds() {
        return postIds;
    }

    public void setPostIds(String[] postIds) {
        this.postIds = postIds;
    }

}

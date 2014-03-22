package com.heaptrip.domain.entity.content;

/**
 * Stores information about adding content to favorites
 */
public class Favorites {

    // the number of users who have added content to favorites
    private long count;

    // account IDs
    private String[] ids;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

}

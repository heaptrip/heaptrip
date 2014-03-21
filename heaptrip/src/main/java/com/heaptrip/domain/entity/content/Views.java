package com.heaptrip.domain.entity.content;

/**
 * Stores information about viewing the content
 */
public class Views {

    // number of views
    private long count;

    // unique identifier:
    // for registered users - userId
    // for non-registered users - ip address of the remote host
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

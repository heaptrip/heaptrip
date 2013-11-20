package com.heaptrip.domain.entity.content;

/**
 * Point of route
 */
public class Point {
    // latitude
    protected String ob;
    // longitude
    protected String pb;

    public Point() {
    }

    public Point(String ob, String pb) {
        this.ob = ob;
        this.pb = pb;
    }

    public String getOb() {
        return ob;
    }

    public void setOb(String ob) {
        this.ob = ob;
    }

    public String getPb() {
        return pb;
    }

    public void setPb(String pb) {
        this.pb = pb;
    }
}

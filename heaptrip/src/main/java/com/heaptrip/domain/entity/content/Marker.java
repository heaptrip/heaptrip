package com.heaptrip.domain.entity.content;

/**
 * Marker in map of route
 */
public class Marker extends Point {
    // markers text
    private String text;

    public Marker() {
    }

    public Marker(String ob, String pb, String text) {
        super(ob, pb);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

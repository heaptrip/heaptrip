package com.heaptrip.domain.entity.content;

/**
 * Content status
 */
public class ContentStatus {

    // status value
    private ContentStatusEnum value;

    // additional description
    private String text;

    public ContentStatus(ContentStatusEnum value) {
        super();
        this.value = value;
    }

    public ContentStatus() {
        super();
    }

    public ContentStatusEnum getValue() {
        return value;
    }

    public void setValue(ContentStatusEnum value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}

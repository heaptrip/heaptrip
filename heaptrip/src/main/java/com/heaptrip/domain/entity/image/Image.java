package com.heaptrip.domain.entity.image;

import java.util.Date;

/**
 * Image
 */
public class Image extends SimpleImage {

    // _id of associated object (account id, trip id, table item id, trip route id, etc.)
    private String target;

    // account id for owner of the image
    private String ownerId;

    // image text (description)
    // TODO konovalov: add multi language text
    private String text;

    // date uploaded
    private Date uploaded;

    // the number of likes
    private Long likes;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getUploaded() {
        return uploaded;
    }

    public void setUploaded(Date uploaded) {
        this.uploaded = uploaded;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }
}

package com.heaptrip.domain.entity.comment;

import com.heaptrip.domain.entity.BaseObject;

import java.util.Date;

/**
 * Comment
 */
public class Comment extends BaseObject {

    // _id of discussion object (trip, post, etc.)
    private String target;

    // _id of the parent comment
    private String parent;

    // path composed of the parent or parent’s slug and this comment’s unique
    // slug
    private String slug;

    // combines the slugs and time information to make it easier to sort
    // documents in a threaded discussion by date
    private String fullSlug;

    // posted timestamp,
    private Date created;

    // account id for author of the comment
    private String authorId;

    // comment text
    private String text;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getFullSlug() {
        return fullSlug;
    }

    public void setFullSlug(String fullSlug) {
        this.fullSlug = fullSlug;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

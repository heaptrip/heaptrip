package com.heaptrip.domain.entity.album;

import com.heaptrip.domain.entity.image.Image;

/**
 * Album image stores images for albums and trip routes
 */
public class AlbumImage extends Image {

    public static final String COLLECTION_NAME = "images";

    // _id of associated object (table item id or trip id, trip route id, etc.)
    private String target;

    // references to images in GridFS
    private ImageReferences refs;

    // account id for owner of the image
    private String ownerId;

    // the number of likes
    private Long likes;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public ImageReferences getRefs() {
        return refs;
    }

    public void setRefs(ImageReferences refs) {
        this.refs = refs;
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

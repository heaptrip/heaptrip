package com.heaptrip.web.model.content;

public class ImageModel {

    // id in image collection
    private String id;

    // id of file with small image
    private String smallId;

    // id of file with small image
    private String mediumId;

    // id of file with small image
    private String fullId;

    // image name
    private String name;

    // image text (description)
    private String text;

    public ImageModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSmallId() {
        return smallId;
    }

    public void setSmallId(String smallId) {
        this.smallId = smallId;
    }

    public String getMediumId() {
        return mediumId;
    }

    public void setMediumId(String mediumId) {
        this.mediumId = mediumId;
    }

    public String getFullId() {
        return fullId;
    }

    public void setFullId(String fullId) {
        this.fullId = fullId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

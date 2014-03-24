package com.heaptrip.domain.entity.image;

/**
 * References to file IDs in GridFS
 */
public class FileReferences {

    // _id of file with small image
    private String small;

    // _id of file with medium image
    private String medium;

    // _id of file with full image
    private String full;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }
}

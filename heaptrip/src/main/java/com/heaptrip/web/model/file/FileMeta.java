package com.heaptrip.web.model.file;

/**
 * Created with IntelliJ IDEA.
 * User: user
 * Date: 15.01.14
 * Time: 10:42
 * To change this template use File | Settings | File Templates.
 */
public class FileMeta {

    private String url;
    private String thumbnailUrl;
    private String highResolutionUrl;
    private String name;
    private String type;
    private String size;
    private String deleteUrl;
    private String deleteType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;


    public String getHighResolutionUrl() {
        return highResolutionUrl;
    }

    public void setHighResolutionUrl(String highResolutionUrl) {
        this.highResolutionUrl = highResolutionUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDeleteUrl() {
        return deleteUrl;
    }

    public void setDeleteUrl(String deleteUrl) {
        this.deleteUrl = deleteUrl;
    }

    public String getDeleteType() {
        return deleteType;
    }

    public void setDeleteType(String deleteType) {
        this.deleteType = deleteType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

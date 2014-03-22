package com.heaptrip.domain.service.album;

import com.heaptrip.domain.entity.image.ImageEnum;

/**
 * List of possible album images
 */
public enum AlbumImageEnum {
    TRIP_ALBUM_IMAGE(ImageEnum.TRIP_ALBUM_SMALL_IMAGE, ImageEnum.TRIP_ALBUM_MEDIUM_IMAGE, ImageEnum.TRIP_ALBUM_FULL_IMAGE),
    TRIP_ROUTE_IMAGE(ImageEnum.TRIP_ROUTE_SMALL_IMAGE, ImageEnum.TRIP_ROUTE_MEDIUM_IMAGE, ImageEnum.TRIP_ROUTE_FULL_IMAGE),
    EVENT_ALBUM_IMAGE(ImageEnum.EVENT_ALBUM_SMALL_IMAGE, ImageEnum.EVENT_ALBUM_MEDIUM_IMAGE, ImageEnum.EVENT_ALBUM_FULL_IMAGE);

    // small image
    private ImageEnum smallImage;

    // medium image
    private ImageEnum mediumImage;

    // full image
    private ImageEnum fullImage;

    private AlbumImageEnum(ImageEnum smallImage, ImageEnum mediumImage, ImageEnum fullImage) {
        this.smallImage = smallImage;
        this.mediumImage = mediumImage;
        this.fullImage = fullImage;
    }

    public ImageEnum getSmallImage() {
        return smallImage;
    }

    public ImageEnum getMediumImage() {
        return mediumImage;
    }

    public ImageEnum getFullImage() {
        return fullImage;
    }

}

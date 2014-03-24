package com.heaptrip.domain.entity.image;

/**
 * List of possible images
 */
public enum ImageEnum {
    ACCOUNT_IMAGE(new ImageSize(55, 55), new ImageSize(300, 200), null),
    CONTENT_IMAGE(null, new ImageSize(800, 600), null),
    TRIP_ALBUM_IMAGE(new ImageSize(150, 150), new ImageSize(611, 375), new ImageSize(1920, 1080)),
    TRIP_ROUTE_IMAGE(new ImageSize(199, 184), new ImageSize(597, 552), new ImageSize(1920, 1080)),
    EVENT_ALBUM_IMAGE(new ImageSize(150, 150), new ImageSize(611, 375), new ImageSize(1920, 1080));

    // small image
    private ImageSize smallSize;

    // medium image
    private ImageSize mediumSize;

    // full image
    private ImageSize fullSize;

    private ImageEnum(ImageSize smallSize, ImageSize mediumSize, ImageSize fullSize) {
        this.smallSize = smallSize;
        this.mediumSize = mediumSize;
        this.fullSize = fullSize;
    }

    public ImageSize getSmallSize() {
        return smallSize;
    }

    public void setSmallSize(ImageSize smallSize) {
        this.smallSize = smallSize;
    }

    public ImageSize getMediumSize() {
        return mediumSize;
    }

    public void setMediumSize(ImageSize mediumSize) {
        this.mediumSize = mediumSize;
    }

    public ImageSize getFullSize() {
        return fullSize;
    }

    public void setFullSize(ImageSize fullSize) {
        this.fullSize = fullSize;
    }
}

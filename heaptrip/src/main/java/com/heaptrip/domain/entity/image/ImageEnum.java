package com.heaptrip.domain.entity.image;

/**
 * List of possible images
 */
public enum ImageEnum {
    ACCOUNT_IMAGE(new ImageSize(55, 55), new ImageSize(300, 200), null, true),
    CONTENT_IMAGE(null, new ImageSize(800, 600), null, false),
    TRIP_ALBUM_IMAGE(new ImageSize(150, 150), new ImageSize(611, 375), new ImageSize(1920, 1080), false),
    TRIP_ROUTE_IMAGE(new ImageSize(199, 184), new ImageSize(597, 552), new ImageSize(1920, 1080), false),
    EVENT_ALBUM_IMAGE(new ImageSize(150, 150), new ImageSize(611, 375), new ImageSize(1920, 1080), false);

    // small image
    private ImageSize smallSize;

    // medium image
    private ImageSize mediumSize;

    // full image
    private ImageSize fullSize;

    private boolean useCRC;

    private ImageEnum(ImageSize smallSize, ImageSize mediumSize, ImageSize fullSize, boolean useCRC) {
        this.smallSize = smallSize;
        this.mediumSize = mediumSize;
        this.fullSize = fullSize;
        this.useCRC = useCRC;
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

    public boolean isUseCRC() {
        return useCRC;
    }

    public void setUseCRC(boolean useCRC) {
        this.useCRC = useCRC;
    }
}

package com.heaptrip.domain.service.album;

import com.heaptrip.domain.entity.album.AlbumImage;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Service for working with images in an album
 */
public interface AlbumService {

    public String SERVICE_NAME = "albumService";

    /**
     * Add image to associated object
     *
     * @param targetId  _id of associated object (table item id or trip id, trip route
     *                  id, etc.)
     * @param ownerId   owner id
     * @param imageType image type
     * @param fileName  file name
     * @param is        input stream
     * @return album image
     * @throws IOException
     */
    public AlbumImage addAlbumImage(String targetId, String ownerId, AlbumImageEnum imageType, String fileName,
                                    InputStream is) throws IOException;

    /**
     * Get images by id
     *
     * @param albumImageId album image id
     * @return image
     */
    public AlbumImage getAlbumImage(String albumImageId);

    /**
     * Get images by id of associated object
     *
     * @param targetId _id of associated object
     * @return list of images
     */
    public List<AlbumImage> getAlbumImages(String targetId);

    /**
     * Get images by id of associated object and limit size
     *
     * @param targetId _id of associated object
     * @return list of images
     */
    // TODO konovalov: add start offset and return total count (maybe total count move to separate method)
    public List<AlbumImage> getAlbumImages(String targetId, int limit);

    /**
     * Update album image
     *
     * @param albumImage image
     */
    public void updateAlbumImage(AlbumImage albumImage);

    /**
     * Remove album image by albumImageId
     *
     * @param albumImageId album image id
     */
    public void removeAlbumImage(String albumImageId);

    /**
     * Remove album images by list of albumImageId
     *
     * @param albumImageIds list of albumImageId
     */
    public void removeAlbumImages(List<String> albumImageIds);

    /**
     * Remove all album images by targetId
     *
     * @param targetId id of associated object
     */
    public void removeAllAlbumImages(String targetId);

    /**
     * Add like to album image
     *
     * @param albumImageId id of image
     */
    public void like(String albumImageId);
}

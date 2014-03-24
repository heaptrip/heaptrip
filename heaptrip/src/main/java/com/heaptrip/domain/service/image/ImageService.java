package com.heaptrip.domain.service.image;

import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.image.ImageEnum;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Service to working with images
 */
// TODO konovalov: check tests
public interface ImageService {

    public String SERVICE_NAME = "imageService";

    /**
     * Add image to associated object
     *
     * @param targetId  _id of associated object (account id, trip id, table item id, trip route id, etc.)
     * @param ownerId   owner id
     * @param imageType image type
     * @param fileName  file name
     * @param is        input stream
     * @return image
     * @throws java.io.IOException
     */
    public Image addImage(String targetId, String ownerId, ImageEnum imageType, String fileName,
                          InputStream is) throws IOException;

    /**
     * Add image
     *
     * @param imageType image type
     * @param fileName  file name
     * @param is        input stream
     * @return image
     * @throws java.io.IOException
     */
    public Image addImage(ImageEnum imageType, String fileName, InputStream is) throws IOException;

    /**
     * Get image by id
     *
     * @param imageId image id
     * @return image
     */
    public Image getImageById(String imageId);

    /**
     * Get images by id of associated object
     *
     * @param targetId _id of associated object
     * @return list of images
     */
    public List<Image> getImagesByTargetId(String targetId);

    /**
     * Get images by id of associated object and limit size
     *
     * @param targetId _id of associated object (account id, trip id, table item id, trip route id, etc.)
     * @param skip     the number of records to skip
     * @param limit    the maximum number of records
     * @return list of images
     */
    public List<Image> getImagesByTargetId(String targetId, int skip, int limit);

    /**
     * Get count of images by id of associated object
     *
     * @param targetId _id of associated object (account id, trip id, table item id, trip route id, etc.)
     * @return count of image
     */
    public long getCountByTargetId(String targetId);

    /**
     * Update image
     *
     * @param image image
     */
    // TODO konovalov: rename to updateImageNameAndText
    public void updateImage(Image image);

    /**
     * Remove image by image id
     *
     * @param imageId image id
     */
    public void removeImageById(String imageId);

    /**
     * Remove images by list of id
     *
     * @param imageIds list of image id
     */
    public void removeImagesByIds(List<String> imageIds);

    /**
     * Remove all images by targetId
     *
     * @param targetId _id of associated object (account id, trip id, table item id, trip route id, etc.)
     */
    public void removeImagesByTargetId(String targetId);

    /**
     * Add like to image
     *
     * @param imageId id of image
     */
    public void like(String imageId);
}

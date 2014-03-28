package com.heaptrip.domain.service.image;

import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.service.image.criteria.ImageCriteria;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Service to working with images
 */
public interface ImageService {

    /**
     * Add image to associated object. Designed to add all types of the images.
     *
     * @param targetId  _id of associated object (account id, trip id, table item id, trip route id, etc.)
     * @param imageType image type
     * @param fileName  file name
     * @param is        input stream
     * @return image
     * @throws java.io.IOException
     */
    public Image addImage(String targetId, ImageEnum imageType, String fileName, InputStream is) throws IOException;

    /**
     * Update image name and text
     *
     * @param imageId image id
     * @param name    new image name
     * @param text    new image description
     */
    public void updateNameAndText(String imageId, String name, String text);

    /**
     * Add like to image
     *
     * @param imageId id of image
     */
    public void like(String imageId);

    /**
     * Get image by id
     *
     * @param imageId image id
     * @return image
     */
    public Image getImageById(String imageId);

    /**
     * Get images by criteria
     *
     * @param imageCriteria criteria for search images
     * @return list of images
     */
    public List<Image> getImagesByCriteria(ImageCriteria imageCriteria);

    /**
     * Get count of images by criteria
     *
     * @param imageCriteria criteria for search images
     * @return count of images
     */
    public long getCountByCriteria(ImageCriteria imageCriteria);

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
     * Remove all images by targetId and image type
     *
     * @param targetId  _id of associated object (account id, trip id, table item id, trip route id, etc.)
     * @param imageType image type
     */
    public void removeImagesByTargetId(String targetId, ImageEnum imageType);
}

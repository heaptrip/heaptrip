package com.heaptrip.domain.service.image;

import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.image.ImageEnum;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Service to working with images
 */
public interface ImageService {

    /**
     * Add image to associated object. Designed to add all types of the images except ImageEnum.CONTENT_IMAGE
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
     * Add image without targetId. Designed to work with following types of image: ImageEnum.CONTENT_IMAGE
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
     * Update image name and text
     *
     * @param imageId image id
     * @param name    new image name
     * @param text    new image description
     */
    public void updateImageNameAndText(String imageId, String name, String text);

    /**
     * Add like to image
     *
     * @param imageId id of image
     */
    public void like(String imageId);

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
}

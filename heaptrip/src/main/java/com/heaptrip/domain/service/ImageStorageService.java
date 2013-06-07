package com.heaptrip.domain.service;

import java.io.InputStream;

import com.heaptrip.domain.entity.ImageEnum;

/**
 * 
 * Image storage service
 * 
 */
public interface ImageStorageService {

	/**
	 * Save image
	 * 
	 * @param fileName
	 *            file name
	 * @param imageType
	 *            typr of image
	 * @param is
	 *            input stream
	 * @return image id
	 */
	public String saveImage(String fileName, ImageEnum imageType, InputStream is);

	/**
	 * Get image
	 * 
	 * @param imageId
	 *            image id
	 * @return input stream
	 */
	public InputStream getImage(String imageId);

	/**
	 * Remove image
	 * 
	 * @param imageId
	 *            image id
	 */
	public void removeImage(String imageId);
}

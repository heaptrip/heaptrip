package com.heaptrip.domain.service.album;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.heaptrip.domain.entity.album.AlbumImage;

/**
 * 
 * Service for working with images in an album
 * 
 */
public interface AlbumService {

	public String SERVICE_NAME = "albumService";

	/**
	 * Add image to associated object
	 * 
	 * @param targetId
	 *            _id of associated object (table item id or trip id, trip route
	 *            id, etc.)
	 * @param ownerId
	 *            owner id
	 * @param type
	 *            image type
	 * @param fileName
	 *            file name
	 * @param is
	 *            input stream
	 * @return album image
	 * @throws IOException
	 */
	public AlbumImage addAlbumImage(String targetId, String ownerId, AlbumImageEnum type, String fileName,
			InputStream is) throws IOException;

	/**
	 * Remove album image by albumImageId
	 * 
	 * @param albumImageId
	 */
	public void removeAlbumImage(String albumImageId);

	/**
	 * Remove album images by list of albumImageId
	 * 
	 * @param albumImageId
	 */
	public void removeAlbumImages(List<String> albumImageIds);

	/**
	 * Remove album images by targetId
	 * 
	 * @param albumImageId
	 */
	public void removeAlbumImagesByTargetId(String targetId);

	/**
	 * Get images by id of associated object
	 * 
	 * @param targetId
	 *            _id of associated object
	 * @return list of images
	 */
	public List<AlbumImage> getAlbumImagesByTargetId(String targetId);

	/**
	 * Get images by id of associated object and limit size
	 * 
	 * @param targetId
	 *            _id of associated object
	 * @return list of images
	 */
	public List<AlbumImage> getAlbumImagesByTargetId(String targetId, int limit);

	/**
	 * Get images by id
	 * 
	 * @param albumImageId
	 *            album image id
	 * @return image
	 */
	public AlbumImage getAlbumImageById(String albumImageId);

	/**
	 * Update album image
	 * 
	 * @param albumImage
	 */
	public void updateAlbumImage(AlbumImage albumImage);

	/**
	 * Add like to album image
	 * 
	 * @param albumImageId
	 *            id of image
	 */
	public void like(String albumImageId);
}

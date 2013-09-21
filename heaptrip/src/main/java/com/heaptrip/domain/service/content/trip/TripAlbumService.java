package com.heaptrip.domain.service.content.trip;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.heaptrip.domain.entity.album.AlbumImage;
import com.heaptrip.domain.service.album.AlbumService;

/**
 * 
 * Service for working with images in trips
 * 
 */
public interface TripAlbumService extends AlbumService {

	/**
	 * Add image to owner album
	 * 
	 * @param tripId
	 *            trip id
	 * @param ownerId
	 *            owner id
	 * @param fileName
	 *            file name
	 * @param is
	 *            input stream
	 * @return album image
	 * @throws IOException
	 */
	public AlbumImage addOwnerAlbumImage(String tripId, String ownerId, String fileName, InputStream is)
			throws IOException;

	/**
	 * Add image to table item album
	 * 
	 * @param tableId
	 *            table item id
	 * @param memberId
	 *            id of trip member
	 * @param fileName
	 *            file name
	 * @param is
	 *            input stream
	 * @return album image
	 * @throws IOException
	 */
	public AlbumImage addTableAlbumImage(String tableId, String memberId, String fileName, InputStream is)
			throws IOException;

	/**
	 * Get images from owner album
	 * 
	 * @param tripId
	 *            trip id
	 * @return list of images
	 */
	public List<AlbumImage> getOwnerAlbumImages(String tripId);

	/**
	 * Get images from owner album
	 * 
	 * @param tripId
	 *            trip id
	 * @param limit
	 *            limit of images
	 * @return list of images
	 */
	public List<AlbumImage> getOwnerAlbumImages(String tripId, int limit);

	/**
	 * Get images from table item album
	 * 
	 * @param tableId
	 *            table item id
	 * @return list of images
	 */
	public List<AlbumImage> getTableAlbumImages(String tableId);

	/**
	 * Get images from table item album
	 * 
	 * @param tableId
	 *            table item id
	 * @param limit
	 *            limit of images
	 * @return list of images
	 */
	public List<AlbumImage> getTableAlbumImages(String tableId, int limit);
}

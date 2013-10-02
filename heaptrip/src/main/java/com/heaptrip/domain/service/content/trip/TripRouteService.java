package com.heaptrip.domain.service.content.trip;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.heaptrip.domain.entity.album.AlbumImage;
import com.heaptrip.domain.service.album.AlbumService;

/**
 * 
 * Service to work with route of the trip
 * 
 */
public interface TripRouteService extends AlbumService {

	/**
	 * Add image to route
	 * 
	 * @param routeId
	 *            id of route
	 * @param ownerId
	 *            owner id
	 * @param fileName
	 *            file name
	 * @param is
	 *            input stream
	 * @return image
	 * @throws IOException
	 */
	public AlbumImage addRouteImage(String routeId, String ownerId, String fileName, InputStream is) throws IOException;

	/**
	 * Get routes images by id of route
	 * 
	 * @param routeId
	 *            id of route
	 * @return list of images
	 */
	public List<AlbumImage> getRouteImages(String routeId);

	/**
	 * Get routes images by id of route
	 * 
	 * @param routeId
	 *            id of route
	 * @param limit
	 *            max namber of images
	 * @return list of images
	 */
	public List<AlbumImage> getRouteImages(String routeId, int limit);

}

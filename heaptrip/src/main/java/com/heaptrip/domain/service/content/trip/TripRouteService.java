package com.heaptrip.domain.service.content.trip;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.album.AlbumImage;
import com.heaptrip.domain.entity.content.trip.Route;
import com.heaptrip.domain.service.album.AlbumService;

/**
 * 
 * Service to work with route of the trip
 * 
 */
public interface TripRouteService extends AlbumService {

	/**
	 * Get route of the trip by trip id
	 * 
	 * @param tripId
	 *            id of trip
	 * @param locale
	 *            locale
	 * @return route
	 */
	public Route getRoute(String tripId, Locale locale);

	/**
	 * Update route of the trip
	 * 
	 * @param tripId
	 *            id of trip
	 * @param route
	 *            route
	 * @param locale
	 *            locale
	 */
	public void updateRoute(String tripId, Route route, Locale locale);

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

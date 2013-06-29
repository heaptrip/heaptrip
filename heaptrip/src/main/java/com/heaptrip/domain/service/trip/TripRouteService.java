package com.heaptrip.domain.service.trip;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.album.AlbumImage;
import com.heaptrip.domain.entity.trip.Route;
import com.heaptrip.domain.service.album.AlbumService;

public interface TripRouteService extends AlbumService {

	public Route updateRoute(String tripId, Route route, Locale locale);

	public Route getRoute(String tripId, Locale locale);

	public AlbumImage addRouteImage(String routeId, String ownerId, String fileName, InputStream is);

	public List<AlbumImage> getRouteImages(String routeId);

	public List<AlbumImage> getRouteImages(String routeId, int limit);

}

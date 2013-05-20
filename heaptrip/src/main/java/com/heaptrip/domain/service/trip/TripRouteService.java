package com.heaptrip.domain.service.trip;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.trip.RoutePhoto;

public interface TripRouteService {

	public void saveRouteDescription(String tripId, String description, Locale locale);

	public String getRouteDescription(String tripId, Locale locale);

	public List<RoutePhoto> getRoutePhotos(String tripId);

	public void addTripRoutePhoto(String tripId, RoutePhoto routePhoto);

	public void updateTripRoutePhoto(String tripId, RoutePhoto routePhoto);

	public void removeTripRoutePhoto(String tripId, RoutePhoto routePhoto);

}

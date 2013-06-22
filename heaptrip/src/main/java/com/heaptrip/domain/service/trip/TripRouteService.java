package com.heaptrip.domain.service.trip;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.image.Image;

public interface TripRouteService {

	public void saveRouteDescription(String tripId, String description, Locale locale);

	public String getRouteDescription(String tripId, Locale locale);

	public List<Image> getRouteImages(String tripId);

	public void addTripRouteImage(String tripId, Image routeImage);

	// what is?
	public void updateTripRouteImage(String tripId, Image routeImage);

	public void removeTripRouteImage(String tripId, Image routeImage);

}

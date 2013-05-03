package com.heaptrip.domain.repository.trip;

import java.util.List;

import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.trip.TripCriteria;

public interface TripRepository {

	public static final String SERVICE_NAME = "tripRepository";

	public void save(Trip trip);

	public Trip findById(String tripId);

	public void removeTrip(String tripId);

	public void setTripDeleted(String tripId, String ownerId);

	public List<Trip> findTripByCriteria(TripCriteria criteria);
}

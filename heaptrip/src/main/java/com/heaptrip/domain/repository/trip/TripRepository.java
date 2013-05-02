package com.heaptrip.domain.repository.trip;

import com.heaptrip.domain.entity.trip.Trip;

public interface TripRepository {

	public static final String SERVICE_NAME = "tripRepository";

	public void save(Trip trip);

	public void hardRemoveTrip(String tripId);
}

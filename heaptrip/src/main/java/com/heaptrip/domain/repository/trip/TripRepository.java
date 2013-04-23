package com.heaptrip.domain.repository.trip;

import java.util.List;

import com.heaptrip.domain.entity.trip.Trip;

public interface TripRepository {
	
	public List<Trip> find();

}

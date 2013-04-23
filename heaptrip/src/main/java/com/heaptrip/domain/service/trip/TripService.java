package com.heaptrip.domain.service.trip;

import java.util.List;

import com.heaptrip.domain.entity.trip.Trip;

public interface TripService {

	public List<Trip> findByCriteria(TripCriteria tripCriteria);

}

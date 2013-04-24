package com.heaptrip.domain.service.trip;

import java.util.List;

import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.SearchPeriod;

public interface TripService {

	public List<Trip> getTripsByCriteria(TripCriteria tripCriteria);

	public Long getTripsCountByCriteria(TripCriteria tripCriteria);

	public TableItem getNearTableItem(Trip trip);

	public TableItem getNearTableItemByPeriod(Trip trip, SearchPeriod period);

}

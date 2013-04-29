package com.heaptrip.web.converter;

import java.util.List;

import com.heaptrip.domain.service.trip.TripCriteria;
import com.heaptrip.web.model.travel.TripModel;

public interface TripModelService {

	List<TripModel> getTripsModelByCriteria(TripCriteria tripCriteria);

}

package com.heaptrip.web.converter;

import java.util.List;

import com.heaptrip.domain.service.trip.FeedTripCriteria;
import com.heaptrip.web.model.travel.TripInfoModel;
import com.heaptrip.web.model.travel.TripModel;

public interface TripModelService {

	List<TripModel> getTripsModelByCriteria(FeedTripCriteria feedTripCriteria);

	TripInfoModel getTripInfoById(String tripId);

}

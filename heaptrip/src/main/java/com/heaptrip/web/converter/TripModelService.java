package com.heaptrip.web.converter;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.trip.criteria.TripFeedCriteria;
import com.heaptrip.web.model.travel.TripInfoModel;
import com.heaptrip.web.model.travel.TripModel;

public interface TripModelService {

	List<TripModel> getTripsModelByCriteria(TripFeedCriteria feedTripCriteria);

	TripInfoModel getTripInfoById(String tripId);

	Trip saveTripInfo(TripInfoModel tripInfoModel, Locale locale);

	void updateTripInfo(TripInfoModel tripInfoModel, Locale locale);

}

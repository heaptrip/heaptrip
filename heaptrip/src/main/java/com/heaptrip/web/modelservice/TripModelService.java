package com.heaptrip.web.modelservice;

import java.util.List;
import java.util.Locale;

import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.service.content.trip.criteria.TripFeedCriteria;
import com.heaptrip.web.model.travel.TripInfoModel;
import com.heaptrip.web.model.travel.TripModel;
import com.heaptrip.web.model.travel.TripRouteModel;

public interface TripModelService {

	List<TripModel> getTripsModelByCriteria(TripFeedCriteria feedTripCriteria);

	TripInfoModel getTripInfoById(String tripId, Locale locale, boolean isOnlyThisLocale);
	
	TripRouteModel getTripRouteById(String tripId, Locale locale, boolean isOnlyThisLocale);

	Trip saveTripInfo(TripInfoModel tripInfoModel);

	void updateTripInfo(TripInfoModel tripInfoModel);

}

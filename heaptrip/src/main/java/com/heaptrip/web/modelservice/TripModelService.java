package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.service.content.trip.criteria.TripFeedCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripForeignAccountCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripMyAccountCriteria;
import com.heaptrip.web.model.travel.TripInfoModel;
import com.heaptrip.web.model.travel.TripModel;

import java.util.List;
import java.util.Locale;

public interface TripModelService {

    List<TripModel> getTripsModelByFeedCriteria(TripFeedCriteria feedTripCriteria);

    List<TripModel> getTripsModelByMyAccountCriteria(TripMyAccountCriteria myAccountCriteria);

    List<TripModel> getTripsModelByForeignAccountCriteria(TripForeignAccountCriteria foreignAccountCriteria);

    TripInfoModel getTripInfoById(String tripId, Locale locale, boolean isOnlyThisLocale);

    Trip saveTripInfo(TripInfoModel tripInfoModel);

    void updateTripInfo(TripInfoModel tripInfoModel);

    TripModel convertTrip(Trip trip);
}

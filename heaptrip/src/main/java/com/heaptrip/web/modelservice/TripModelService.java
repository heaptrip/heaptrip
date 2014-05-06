package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.service.content.trip.criteria.TripFeedCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripForeignAccountCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripMyAccountCriteria;
import com.heaptrip.domain.service.criteria.IDCriteria;
import com.heaptrip.web.model.travel.ScheduleParticipantModel;
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

    /**
     * Convert trip to trip model
     *
     * @param trip        trip
     * @param isFullModel if the value is true, then all the data about the content
     *                    will be converted into the model (including the availability of the rating,
     *                    the possibility to add to favorites, etc.) Otherwise, the model will
     *                    include only short information to display in the feeds.
     * @return trip model
     */
    TripModel convertTrip(Trip trip, boolean isFullModel);

    List<ScheduleParticipantModel> getTripScheduleParticipants(IDCriteria criteria);

}

package com.heaptrip.web.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.trip.TripService;
import com.heaptrip.domain.service.trip.criteria.TripFeedCriteria;
import com.heaptrip.web.model.travel.TripInfoModel;
import com.heaptrip.web.model.travel.TripModel;

@Service
public class TripModelServiceImpl extends ContentModelServiceImpl implements TripModelService {

	@Autowired
	private TripService tripService;

	@Override
	public List<TripModel> getTripsModelByCriteria(TripFeedCriteria feedTripCriteria) {
		feedTripCriteria.setLocale(getCurrentLocale());
		List<Trip> trips = tripService.getTripsByFeedTripCriteria(feedTripCriteria);
		return convertTripToTripModel(trips);
	}

	@Override
	public TripInfoModel getTripInfoById(String tripId) {
		return convertTripToTripInfoModel(tripService.getTripInfo(tripId, getCurrentLocale()));
	}

	private void setTripToTripModel(TripModel tripModel, Trip trip) {

		if (trip != null) {

			setContentToContentModel(tripModel, trip);

			tripModel.setRating(trip.getRating());
			tripModel.setComments(trip.getComments());

			TableItem tableItem = tripService.getNearTableItem(trip);
			if (tableItem != null) {
				tripModel.setBegin(convertDate(tableItem.getBegin()));
				tripModel.setEnd(convertDate(tableItem.getEnd()));
			}

			if (trip.getSummary() != null)
				tripModel.setSummary(trip.getSummary().getValue(getCurrentLocale()));

		}

	}

	private TripInfoModel convertTripToTripInfoModel(Trip trip) {
		TripInfoModel tripInfoModel = new TripInfoModel();
		setTripToTripModel(tripInfoModel, trip);
		if (trip.getDescription() != null)
			tripInfoModel.setDescription(trip.getDescription().getValue(getCurrentLocale()));
		return tripInfoModel;

	}

	private TripModel convertTripToTripModel(Trip trip) {
		TripModel result = new TripModel();
		setTripToTripModel(result, trip);
		return result;

	}

	private List<TripModel> convertTripToTripModel(List<Trip> trips) {
		List<TripModel> tripModels = new ArrayList<TripModel>();
		if (trips != null && !trips.isEmpty()) {
			for (Trip trip : trips) {
				tripModels.add(convertTripToTripModel(trip));
			}
		}
		return tripModels;
	}

}

package com.heaptrip.web.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.adm.RequestScopeService;
import com.heaptrip.domain.service.trip.TripCriteria;
import com.heaptrip.domain.service.trip.TripService;
import com.heaptrip.web.model.travel.TripModel;

@Service
public class TripModelServiceImpl implements TripModelService {

	@Autowired
	private TripService tripService;

	@Autowired
	private RequestScopeService scopeService;

	@Override
	public List<TripModel> getTripsModelByCriteria(TripCriteria tripCriteria) {

		return convertTripToModel(tripService.getTripsByCriteria(tripCriteria));
	}

	private TripModel convertTripToModel(Trip trip) {

		TripModel tripModel = new TripModel();
		tripModel.setName(trip.getName().getValue(scopeService.getCurrentLocale()));

		return tripModel;
	}

	private List<TripModel> convertTripToModel(List<Trip> trips) {
		List<TripModel> tripModels = new ArrayList<TripModel>();
		if (trips != null && !trips.isEmpty()) {
			for (Trip trip : trips) {
				tripModels.add(convertTripToModel(trip));
			}
		}
		return tripModels;
	}

}

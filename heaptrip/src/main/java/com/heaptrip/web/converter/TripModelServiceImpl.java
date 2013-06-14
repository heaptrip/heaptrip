package com.heaptrip.web.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.trip.TableItem;
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

	@Autowired
	private FilterModelService filterModelService;

	@Autowired
	private ContentModelService contentModelService;

	@Override
	public List<TripModel> getTripsModelByCriteria(TripCriteria tripCriteria) {
		tripCriteria.setLocale(scopeService.getCurrentLocale());
		tripCriteria.setCategoryIds(filterModelService.getCategoriesForCurrentUser(tripCriteria.getCategoryIds()));

		List<Trip> trips = tripService.getTripsByCriteria(tripCriteria);
		return convertTripToModel(trips);
	}

	private TripModel convertTripToModel(Trip trip) {
		TripModel tripModel = null;
		if (trip != null) {
			tripModel = new TripModel();
			tripModel.setId(trip.getId());
			tripModel.setCreated(trip.getCreated());
			tripModel.setRating(trip.getRating());
			tripModel.setComments(trip.getComments());
			tripModel.setImage(trip.getImage().getId());
			tripModel.setViews(trip.getViews());

			TableItem tableItem = tripService.getNearTableItem(trip);
			if (tableItem != null) {
				tripModel.setBegin(tableItem.getBegin());
				tripModel.setEnd(tableItem.getEnd());
			}
			
			if (trip.getSummary() != null)
				tripModel.setSummary(trip.getSummary().getValue(scopeService.getCurrentLocale()));
			if (trip.getName() != null)
				tripModel.setName(trip.getName().getValue(scopeService.getCurrentLocale()));
			if (trip.getDescription() != null)
				tripModel.setDescription(trip.getDescription().getValue(scopeService.getCurrentLocale()));

			tripModel.setOwner(contentModelService.convertContentOwnerToModel(trip.getOwner()));
			tripModel.setCategories(contentModelService.convertCategoriesToModel(trip.getCategories()));
			tripModel.setRegions(contentModelService.convertRegionsToModel(trip.getRegions()));
		}
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

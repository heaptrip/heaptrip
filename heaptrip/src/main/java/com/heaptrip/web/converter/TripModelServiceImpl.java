package com.heaptrip.web.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.trip.TripService;
import com.heaptrip.domain.service.trip.criteria.TripFeedCriteria;
import com.heaptrip.web.model.content.StatusModel;
import com.heaptrip.web.model.travel.ScheduleModel;
import com.heaptrip.web.model.travel.TripInfoModel;
import com.heaptrip.web.model.travel.TripModel;

@Service
public class TripModelServiceImpl extends ContentModelServiceImpl implements
		TripModelService {

	@Autowired
	private TripService tripService;

	@Override
	public List<TripModel> getTripsModelByCriteria(
			TripFeedCriteria tripFeedCriteria) {
		tripFeedCriteria.setLocale(getCurrentLocale());
		List<Trip> trips = tripService
				.getTripsByTripFeedCriteria(tripFeedCriteria);
		return convertTripToTripModel(trips);
	}

	@Override
	public TripInfoModel getTripInfoById(String tripId) {
		return convertTripToTripInfoModel(tripService.getTripInfo(tripId,
				getCurrentLocale()));
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
				tripModel.setSummary(trip.getSummary().getValue(
						getCurrentLocale()));

		}

	}

	private TripInfoModel convertTripToTripInfoModel(Trip trip) {
		TripInfoModel tripInfoModel = new TripInfoModel();
		setTripToTripModel(tripInfoModel, trip);
		if (trip.getDescription() != null)
			tripInfoModel.setDescription(trip.getDescription().getValue(
					getCurrentLocale()));
		if (trip.getTable() != null) {
			List<ScheduleModel> scheduleList = new ArrayList<ScheduleModel>();
			for (TableItem item : trip.getTable()) {
				scheduleList.add(convertTableItemToScheduleModel(item));
			}
			tripInfoModel.setSchedule(scheduleList
					.toArray(new ScheduleModel[scheduleList.size()]));
		}
		return tripInfoModel;

	}

	private ScheduleModel convertTableItemToScheduleModel(TableItem item) {
		ScheduleModel schedule = new ScheduleModel();
		schedule.setBegin(convertDate(item.getBegin()));
		schedule.setEnd(convertDate(item.getEnd()));
		schedule.setMembers(item.getMembers());
		schedule.setMin(item.getMin());
		schedule.setMax(item.getMax());
		StatusModel status = new StatusModel();
		status.setValue(item.getStatus().getValue().name());
		status.setText(item.getStatus().getText());
		schedule.setStatus(status);
		schedule.setPrice(convertPrice(item.getPrice()));
		return schedule;

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

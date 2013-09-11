package com.heaptrip.web.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.category.SimpleCategory;
import com.heaptrip.domain.entity.content.MultiLangText;
import com.heaptrip.domain.entity.region.SimpleRegion;
import com.heaptrip.domain.entity.trip.TableItem;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.service.trip.TripService;
import com.heaptrip.domain.service.trip.criteria.TripFeedCriteria;
import com.heaptrip.web.model.content.CategoryModel;
import com.heaptrip.web.model.content.RegionModel;
import com.heaptrip.web.model.content.StatusModel;
import com.heaptrip.web.model.travel.ScheduleModel;
import com.heaptrip.web.model.travel.TripInfoModel;
import com.heaptrip.web.model.travel.TripModel;

@Service
public class TripModelServiceImpl extends ContentModelServiceImpl implements TripModelService {

	@Autowired
	private TripService tripService;

	@Override
	public List<TripModel> getTripsModelByCriteria(TripFeedCriteria tripFeedCriteria) {
		tripFeedCriteria.setLocale(getCurrentLocale());
		List<Trip> trips = tripService.getTripsByTripFeedCriteria(tripFeedCriteria);
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
		if (trip.getTable() != null) {
			List<ScheduleModel> scheduleList = new ArrayList<ScheduleModel>();
			for (TableItem item : trip.getTable()) {
				scheduleList.add(convertTableItemToScheduleModel(item));
			}
			tripInfoModel.setSchedule(scheduleList.toArray(new ScheduleModel[scheduleList.size()]));
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

	private Trip convertTripModelToTrip(TripInfoModel tripInfoModel, Locale locale) {
		Trip trip = new Trip();
		trip.setOwner(getContentOwner());
		trip.setMainLang(locale.getDisplayLanguage());
		trip.setName(new MultiLangText(tripInfoModel.getName(), locale));
		trip.setDescription(new MultiLangText(tripInfoModel.getDescription(), locale));
		trip.setSummary(new MultiLangText(tripInfoModel.getSummary(), locale));
		trip.setCategoryIds(convertCategoriesModelsToIdsArray(tripInfoModel.getCategories(), locale));
		trip.setCategories(convertCategoriesModelsToCategories(tripInfoModel.getCategories(), locale));
		trip.setRegionIds(convertRegionModelsToIdsArray(tripInfoModel.getRegions(), locale));
		trip.setRegions(convertRegionModelsToRegions(tripInfoModel.getRegions(), locale));
		trip.setTable(convertScheduleModelsToTableItems(tripInfoModel.getSchedule()));
		return trip;
	}

	private TableItem[] convertScheduleModelsToTableItems(ScheduleModel[] models) {
		TableItem[] result = null;
		if (models != null) {
			List<TableItem> tableItem = new ArrayList<TableItem>();
			for (ScheduleModel model : models) {
				tableItem.add(convertScheduleModelToTableItem(model));
			}
			result = tableItem.toArray(new TableItem[tableItem.size()]);
		}
		return result;
	}

	private TableItem convertScheduleModelToTableItem(ScheduleModel model) {
		TableItem tableItem = new TableItem();

		tableItem.setBegin(model.getBegin().getValue());
		tableItem.setEnd(model.getEnd().getValue());
		// TODO: add ID to ScheduleModel
		// tableItem.setId();
		tableItem.setMax(model.getMax());
		tableItem.setMin(model.getMin());
		tableItem.setMembers(model.getMembers());
		// TODO: add ID to ScheduleModel
		// tableItem.setPrice();
		tableItem.setPrice(null);

		return tableItem;
	}

	@Override
	public Trip saveTripInfo(TripInfoModel tripInfoModel, Locale locale) {
		return tripService.saveTrip(convertTripModelToTrip(tripInfoModel, locale), locale);
	}

	@Override
	public void updateTripInfo(TripInfoModel tripInfoModel, Locale locale) {
		tripService.updateTripInfo(convertTripModelToTrip(tripInfoModel, locale), locale);
	}

}

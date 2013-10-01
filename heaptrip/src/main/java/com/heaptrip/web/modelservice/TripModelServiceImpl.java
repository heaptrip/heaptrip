package com.heaptrip.web.modelservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.content.trip.TableItem;
import com.heaptrip.domain.entity.content.trip.TableStatus;
import com.heaptrip.domain.entity.content.trip.TableStatusEnum;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.service.content.trip.TripFeedService;
import com.heaptrip.domain.service.content.trip.TripService;
import com.heaptrip.domain.service.content.trip.criteria.TripFeedCriteria;
import com.heaptrip.web.model.content.StatusModel;
import com.heaptrip.web.model.travel.RouteModel;
import com.heaptrip.web.model.travel.ScheduleModel;
import com.heaptrip.web.model.travel.TripInfoModel;
import com.heaptrip.web.model.travel.TripModel;
import com.heaptrip.web.model.travel.TripRouteModel;

@Service
public class TripModelServiceImpl extends ContentModelServiceImpl implements TripModelService {

	@Autowired
	private TripService tripService;

	@Autowired
	private TripFeedService tripFeedService;

	@Override
	public List<TripModel> getTripsModelByCriteria(TripFeedCriteria tripFeedCriteria) {
		tripFeedCriteria.setLocale(getCurrentLocale());
		List<Trip> trips = tripFeedService.getContentsByFeedCriteria(tripFeedCriteria);
		return convertTripToTripModel(trips);
	}

	@Override
	public TripInfoModel getTripInfoById(String tripId, Locale locale, boolean isOnlyThisLocale) {
		return convertTripToTripInfoModel(tripService.getTripInfo(tripId, locale), locale, isOnlyThisLocale);
	}

	@Override
	public TripRouteModel getTripRouteById(String tripId, Locale locale, boolean isOnlyThisLocale) {
		return convertTripToTripRouteModel(tripService.getTripInfo(tripId, locale), locale, isOnlyThisLocale);
	}

	@Override
	public Trip saveTripInfo(TripInfoModel tripInfoModel) {
		return tripService.save(convertTripInfoModelToTrip(tripInfoModel, new Locale(tripInfoModel.getLocale())),
				new Locale(tripInfoModel.getLocale()));
	}

	@Override
	public void updateTripInfo(TripInfoModel tripInfoModel) {
		tripService.updateTripInfo(convertTripInfoModelToTrip(tripInfoModel, new Locale(tripInfoModel.getLocale())),
				new Locale(tripInfoModel.getLocale()));
	}

	private TripModel appendTripToTripModel(TripModel tripModel, Trip trip, Locale locale, boolean isOnlyThisLocale) {
		if (trip != null) {
			setContentToContentModel(tripModel, trip, locale, isOnlyThisLocale);

			tripModel.setComments(trip.getComments());

			if (trip.getTable() != null && trip.getTable().length > 0) {
				TableItem tableItem = tripService.getNearestTableItem(trip);
				if (tableItem != null) {
					tripModel.setBegin(convertDate(tableItem.getBegin()));
					tripModel.setEnd(convertDate(tableItem.getEnd()));
				}
			}

			if (trip.getSummary() != null)
				tripModel.setSummary(getMultiLangTextValue(trip.getSummary(), locale, isOnlyThisLocale));
		}

		return tripModel;
	}

	private TripInfoModel convertTripToTripInfoModel(Trip trip, Locale locale, boolean isOnlyThisLocale) {
		TripInfoModel tripInfoModel = new TripInfoModel();
		appendTripToTripModel(tripInfoModel, trip, locale, isOnlyThisLocale);
		if (trip.getDescription() != null)
			tripInfoModel.setDescription(getMultiLangTextValue(trip.getDescription(), locale, isOnlyThisLocale));
		tripInfoModel.setSchedule(convertTableItemsToScheduleModels(trip.getTable()));
		return tripInfoModel;
	}

	private TripRouteModel convertTripToTripRouteModel(Trip trip, Locale locale, boolean isOnlyThisLocale) {
		TripRouteModel tripRouteModel = new TripRouteModel();
		appendTripToTripModel(tripRouteModel, trip, locale, isOnlyThisLocale);
		RouteModel routeModel = new RouteModel();
		routeModel.setText(getMultiLangTextValue(trip.getDescription(), locale, isOnlyThisLocale));
		tripRouteModel.setRoute(routeModel);
		return tripRouteModel;
	}

	private ScheduleModel convertTableItemToScheduleModel(TableItem item) {
		ScheduleModel schedule = new ScheduleModel();
		schedule.setId(item.getId());
		schedule.setBegin(convertDate(item.getBegin()));
		schedule.setEnd(convertDate(item.getEnd()));
		schedule.setMembers(item.getMembers());
		schedule.setMin(item.getMin());
		schedule.setMax(item.getMax());
		schedule.setStatus(convertStatus(item.getStatus()));
		schedule.setPrice(convertPrice(item.getPrice()));
		return schedule;
	}

	private TripModel convertTripToTripModel(Trip trip) {
		TripModel tripModel = new TripModel();
		return appendTripToTripModel(tripModel, trip, getCurrentLocale(), false);
	}

	private Trip convertTripInfoModelToTrip(TripInfoModel tripInfoModel, Locale locale) {
		Trip trip = new Trip();
		trip.setLangs(new String[] { locale.getLanguage() });
		trip.setId(tripInfoModel.getId());
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

	private TableItem convertScheduleModelToTableItem(ScheduleModel model) {
		TableItem tableItem = new TableItem();
		tableItem.setBegin(model.getBegin().getValue());
		tableItem.setEnd(model.getEnd().getValue());
		tableItem.setId(model.getId());
		tableItem.setMax(model.getMax());
		tableItem.setMin(model.getMin());
		tableItem.setMembers(model.getMembers());
		tableItem.setPrice(convertPriceModel(model.getPrice()));
		tableItem.setStatus(convertStatusModel(model.getStatus()));
		return tableItem;
	}

	private TableStatus convertStatusModel(StatusModel statusModel) {
		TableStatus status = null;
		if (statusModel != null) {
			status = new TableStatus();
			status.setText(status.getText());
			TableStatusEnum statusEnum = null;
			for (TableStatusEnum tableStatusEnum : TableStatusEnum.values()) {
				if (tableStatusEnum.name().equals(statusModel.getValue()))
					statusEnum = tableStatusEnum;
			}
			status.setValue(statusEnum);
		}
		return status;
	}

	private StatusModel convertStatus(TableStatus status) {
		StatusModel statusModel = null;
		if (status != null) {
			statusModel = new StatusModel();
			statusModel.setValue(status.getValue().name());
			statusModel.setText(status.getText());
		}
		return statusModel;
	}

	private ScheduleModel[] convertTableItemsToScheduleModels(TableItem[] tableItems) {
		ScheduleModel[] result = null;
		if (tableItems != null) {
			List<ScheduleModel> scheduleList = new ArrayList<ScheduleModel>();
			for (TableItem item : tableItems) {
				scheduleList.add(convertTableItemToScheduleModel(item));
			}
			result = scheduleList.toArray(new ScheduleModel[scheduleList.size()]);
		}
		return result;
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

	private List<TripModel> convertTripToTripModel(List<Trip> trips) {
		List<TripModel> tripModels = new ArrayList<TripModel>();
		if (trips != null && !trips.isEmpty())
			for (Trip trip : trips) {
				tripModels.add(convertTripToTripModel(trip));
			}
		return tripModels;
	}

}

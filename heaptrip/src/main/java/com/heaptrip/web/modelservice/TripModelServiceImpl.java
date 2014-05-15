package com.heaptrip.web.modelservice;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.account.Profile;
import com.heaptrip.domain.entity.account.user.Knowledge;
import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.content.trip.*;
import com.heaptrip.domain.service.content.trip.TripFeedService;
import com.heaptrip.domain.service.content.trip.TripService;
import com.heaptrip.domain.service.content.trip.TripUserService;
import com.heaptrip.domain.service.content.trip.criteria.TripFeedCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripForeignAccountCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripMemberCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripMyAccountCriteria;
import com.heaptrip.util.converter.Converter;
import com.heaptrip.util.converter.ListConverter;
import com.heaptrip.web.model.content.StatusModel;
import com.heaptrip.web.model.profile.KnowledgeModel;
import com.heaptrip.web.model.travel.criteria.TripParticipantsCriteria;
import com.heaptrip.web.model.travel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class TripModelServiceImpl extends ContentModelServiceImpl implements TripModelService {

    @Autowired
    private TripService tripService;

    @Autowired
    private TripFeedService tripFeedService;

    @Autowired
    private TripUserService tripUserService;

    @Autowired
    private ProfileModelService profileModelService;


    @Override
    public TripModel convertTrip(Trip trip, boolean isFullModel) {
        TripInfoModel tripInfoModel = new TripInfoModel();
        appendTripToTripModel(tripInfoModel, trip, getCurrentLocale(), false, isFullModel);
        tripInfoModel.setSchedule(convertTableItemsToScheduleModels(trip.getTable()));
        tripInfoModel.setRoute(convertTripRouteToModel(trip.getRoute(), getCurrentLocale(), false));
        return tripInfoModel;
    }

    @Override
    public List<TripModel> getTripsModelByFeedCriteria(TripFeedCriteria tripFeedCriteria) {
        tripFeedCriteria.setLocale(getCurrentLocale());
        List<Trip> trips = tripFeedService.getContentsByFeedCriteria(tripFeedCriteria);
        return convertTripToTripModel(trips);
    }

    @Override
    public List<TripModel> getTripsModelByMyAccountCriteria(TripMyAccountCriteria myAccountCriteria) {
        myAccountCriteria.setLocale(getCurrentLocale());
        List<Trip> trips = tripFeedService.getContentsByMyAccountCriteria(myAccountCriteria);
        return convertTripToTripModel(trips);
    }

    @Override
    public List<TripModel> getTripsModelByForeignAccountCriteria(TripForeignAccountCriteria foreignAccountCriteria) {
        foreignAccountCriteria.setLocale(getCurrentLocale());
        List<Trip> trips = tripFeedService.getContentsByForeignAccountCriteria(foreignAccountCriteria);
        return convertTripToTripModel(trips);
    }

    @Override
    public TripInfoModel getTripInfoById(String tripId, Locale locale, boolean isOnlyThisLocale) {
        return convertTripToTripInfoModel(tripService.getTripInfo(tripId, locale), locale, isOnlyThisLocale);
    }

    @Override
    public Trip saveTripInfo(TripInfoModel tripInfoModel) {
        Trip trip = convertTripInfoModelToTrip(tripInfoModel, new Locale(tripInfoModel.getLocale()));
        trip = tripService.save(trip, new Locale(tripInfoModel.getLocale()));
        ContentStatus contentStatus = convertContentStatusModelToContentStatus(tripInfoModel.getStatus());
        contentService.setStatus(trip.getId(), contentStatus);
        return trip;
    }

    @Override
    public void updateTripInfo(TripInfoModel tripInfoModel) {
        Trip trip = convertTripInfoModelToTrip(tripInfoModel, new Locale(tripInfoModel.getLocale()));
        tripService.updateTripInfo(trip, new Locale(tripInfoModel.getLocale()));
        ContentStatus contentStatus = convertContentStatusModelToContentStatus(tripInfoModel.getStatus());
        contentService.setStatus(trip.getId(), contentStatus);
    }

    @Override
    public List<ScheduleParticipantModel> getTripScheduleParticipants(TripParticipantsCriteria criteria) {
        List<ScheduleParticipantModel> scheduleParticipants = new ArrayList<>();
        String tripId = criteria.getTripId();
        if (tripId != null && !tripId.isEmpty()) {
            List<TableItem> tableItems = tripService.getTableItems(tripId);
            if (tableItems != null && !tableItems.isEmpty()) {
                for (TableItem tableItem : tableItems) {

                    ScheduleParticipantModel scheduleParticipantModel = new ScheduleParticipantModel();
                    scheduleParticipantModel.setSchedule(convertTableItemToScheduleModel(tableItem));
                    scheduleParticipants.add(scheduleParticipantModel);

                    TripMemberCriteria memberCriteria = new TripMemberCriteria();
                    memberCriteria.setTableId(tableItem.getId());
                    memberCriteria.setTripId(tripId);
                    // TODO : voronenko  memberCriteria.setLimit & memberCriteria.setSkip for list
                    // memberCriteria.setLimit( criteria.getParticipantsCriteria().get(0).getLimit());
                    // memberCriteria.setSkip(criteria.getParticipantsCriteria().get(0).getSkip());
                    List<TripMember> tripMembers = tripUserService.getMembersByCriteria(memberCriteria);
                    scheduleParticipantModel.setParticipants(convertTripMembersToParticipantModels(tripMembers));
                }
            }
        }
        return scheduleParticipants;
    }

    private ParticipantModel convertTripMemberToParticipantModel(TripMember tripMember) {
        if (tripMember instanceof TripUser) {
            TripUser tripUser = (TripUser) tripMember;
            ParticipantUserModel model = new ParticipantUserModel();
            model.setAccount(profileModelService.getAccountInformation(tripUser.getUserId()));
            if (tripUser.getIsOrganizer() != null)
                model.setOrganizer(tripUser.getIsOrganizer());
            model.setStatus(tripUser.getStatus().name());
            return model;
        } else if (tripMember instanceof TripInvite) {
            ParticipantInviteModel model = new ParticipantInviteModel();
            // TODO : voronenko not yet implemented
            return model;
        } else {
            throw new RuntimeException("tripMember not instanceof TripUser OR TripInvite");
        }

    }

    private List<ParticipantModel> convertTripMembersToParticipantModels(List<TripMember> tripMember) {
        return ListConverter.convertList(tripMember, new Converter<TripMember, ParticipantModel>() {
            public ParticipantModel convert(TripMember tripMember) {
                return convertTripMemberToParticipantModel(tripMember);
            }
        });
    }


    private TripModel appendTripToTripModel(TripModel tripModel, Trip trip, Locale locale, boolean isOnlyThisLocale, boolean isFullModel) {
        if (trip != null) {
            setContentToContentModel(tripModel, trip, locale, isOnlyThisLocale, isFullModel);

            if (trip.getTable() != null && trip.getTable().length > 0) {
                TableItem tableItem = tripService.getNearestTableItem(trip);
                if (tableItem != null) {
                    tripModel.setBegin(convertDate(tableItem.getBegin()));
                    tripModel.setEnd(convertDate(tableItem.getEnd()));
                }
            }
        }

        return tripModel;
    }

    private TripInfoModel convertTripToTripInfoModel(Trip trip, Locale locale, boolean isOnlyThisLocale) {
        TripInfoModel tripInfoModel = new TripInfoModel();
        appendTripToTripModel(tripInfoModel, trip, locale, isOnlyThisLocale, true);
        tripInfoModel.setSchedule(convertTableItemsToScheduleModels(trip.getTable()));
        tripInfoModel.setRoute(convertTripRouteToModel(trip.getRoute(), locale, isOnlyThisLocale));
        return tripInfoModel;
    }

    private RouteModel convertTripRouteToModel(Route route, Locale locale, boolean isOnlyThisLocale) {
        RouteModel routeModel = new RouteModel();
        if (route != null) {
            routeModel.setText(getMultiLangTextValue(route.getText(), locale, isOnlyThisLocale));
        }
        return routeModel;
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

    private Trip convertTripInfoModelToTrip(TripInfoModel tripInfoModel, Locale locale) {
        Trip trip = new Trip();
        trip.setLangs(new String[]{locale.getLanguage()});
        trip.setId(tripInfoModel.getId());
        trip.setOwnerId(tripInfoModel.getOwner().getId());
        trip.setStatus(convertContentStatusModelToContentStatus(tripInfoModel.getStatus()));
        trip.setMainLang(locale.getDisplayLanguage());
        trip.setName(new MultiLangText(tripInfoModel.getName(), locale));
        trip.setDescription(new MultiLangText(tripInfoModel.getDescription(), locale));
        trip.setSummary(new MultiLangText(tripInfoModel.getSummary(), locale));
        trip.setCategories(convertCategoriesModelsToCategories(tripInfoModel.getCategories(), locale));
        trip.setRoute(convertRouteModelToRoute(tripInfoModel.getRoute(), locale));
        trip.setRegions(convertRegionModelsToRegions(tripInfoModel.getRegions(), locale));
        trip.setTable(convertScheduleModelsToTableItems(tripInfoModel.getSchedule()));
        return trip;
    }

    private Route convertRouteModelToRoute(RouteModel routeModel, Locale locale) {
        Route route = null;
        if (routeModel != null && routeModel.getText() != null) {
            route = new Route();
            if (routeModel.getText() != null) {
                route.setText(new MultiLangText(routeModel.getText(), locale));
            }
        }
        return route;
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
            status.setValue(TableStatusEnum.valueOf(statusModel.getValue()));
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
            List<ScheduleModel> scheduleList = new ArrayList<>();
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
            List<TableItem> tableItem = new ArrayList<>();
            for (ScheduleModel model : models) {
                tableItem.add(convertScheduleModelToTableItem(model));
            }
            result = tableItem.toArray(new TableItem[tableItem.size()]);
        }
        return result;
    }

    private TripModel convertTripToTripModel(Trip trip, boolean isFullModel) {
        TripModel tripModel = new TripModel();
        return appendTripToTripModel(tripModel, trip, getCurrentLocale(), false, isFullModel);
    }

    private List<TripModel> convertTripToTripModel(List<Trip> trips) {
        List<TripModel> tripModels = new ArrayList<>();
        if (trips != null && !trips.isEmpty())
            for (Trip trip : trips) {
                tripModels.add(convertTripToTripModel(trip, false));
            }
        return tripModels;
    }

}

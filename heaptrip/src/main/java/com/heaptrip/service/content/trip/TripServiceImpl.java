package com.heaptrip.service.content.trip;

import com.heaptrip.domain.entity.MultiLangText;
import com.heaptrip.domain.entity.content.ContentStatus;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.entity.content.Views;
import com.heaptrip.domain.entity.content.trip.*;
import com.heaptrip.domain.entity.rating.ContentRating;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.trip.TripException;
import com.heaptrip.domain.repository.content.trip.TripMemberRepository;
import com.heaptrip.domain.repository.content.trip.TripRepository;
import com.heaptrip.domain.service.content.ContentSearchService;
import com.heaptrip.domain.service.content.trip.TripService;
import com.heaptrip.domain.service.content.trip.criteria.SearchPeriod;
import com.heaptrip.domain.service.content.trip.criteria.TripMemberCriteria;
import com.heaptrip.domain.service.system.ErrorService;
import com.heaptrip.service.content.ContentServiceImpl;
import com.heaptrip.util.language.LanguageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

@Service
public class TripServiceImpl extends ContentServiceImpl implements TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripMemberRepository tripMemberRepository;

    @Autowired
    private ErrorService errorService;

    @Autowired
    private ContentSearchService solrContentService;

    //@Autowired
    //private TripUserService tripUserService;

    /*
    private void setOrganizers(Trip trip) {
        if (trip.getTable() != null) {
            for (TableItem item : trip.getTable()) {
                if (item.getStatus().getValue().equals(TableStatusEnum.OK)
                        && (item.getMembers() == null || item.getMembers().equals(0L))) {
                    TripUser tripUser = tripUserService.sendInvite(trip.getId(), item.getId(), trip.getOwnerId());
                    tripUserService.acceptTripMember(tripUser.getId());
                    tripUserService.setTripMemberOrganizer(tripUser.getId(), true);
                }
            }
        }

    }
    */

    @Override
    public Trip save(Trip trip, Locale locale) {
        Assert.notNull(trip, "trip must not be null");
        Assert.notNull(locale, "locale must not be null");
        Assert.notNull(trip.getOwnerId(), "owner.id must not be null");
        Assert.notEmpty(trip.getName(), "name must not be empty");
        Assert.notEmpty(trip.getSummary(), "summary must not be empty");
        Assert.notEmpty(trip.getDescription(), "description must not be empty");

        // update categories and categoryIds
        updateCategories(trip);

        // update regions and regionIds
        updateRegions(trip);

        if (trip.getTable() != null) {
            for (TableItem item : trip.getTable()) {
                if (item.getId() == null) {
                    item.setId(UUID.randomUUID().toString());
                }
                item.setStatus(new TableStatus(TableStatusEnum.OK));
            }
        }

        // set route
        Route route = new Route();
        route.setId(UUID.randomUUID().toString());
        route.setText(new MultiLangText());
        trip.setRoute(route);

        // set lang
        String lang = LanguageUtils.getLanguageByLocale(locale);
        trip.setLangs(new String[]{lang});
        trip.setMainLang(lang);
        trip.getName().setMainLanguage(lang);
        trip.getSummary().setMainLanguage(lang);
        trip.getDescription().setMainLanguage(lang);

        trip.setStatus(new ContentStatus(ContentStatusEnum.DRAFT));
        trip.setCreated(new Date());
        trip.setDeleted(null);
        trip.setRating(ContentRating.getDefaultValue());
        trip.setComments(0L);

        Views views = new Views();
        views.setCount(0);
        trip.setViews(views);

        if (trip.getPostIds() == null)
            trip.setPostIds(new String[0]);

        // save to db
        tripRepository.save(trip);

        // save to solr
        solrContentService.saveContent(trip.getId());

        // set trip organizers
        //setOrganizers(trip);

        return trip;
    }

    @Override
    public void remove(String tripId) {
        Assert.notNull(tripId, "tripId must not be null");
        // check trip members
        TripMemberCriteria memberCriteria = new TripMemberCriteria();
        memberCriteria.setTripId(tripId);
        long members = tripMemberRepository.countByCriteria(memberCriteria);
        if (members > 0) {
            throw errorService.createException(TripException.class, ErrorEnum.REMOVE_TRIP_FAILURE);
        }
        // remove from solr
        solrContentService.removeContent(tripId);
        super.remove(tripId);
    }

    @Override
    public void hardRemove(String tripId) {
        Assert.notNull(tripId, "tripId must not be null");
        super.hardRemove(tripId);
        // remove from solr
        solrContentService.removeContent(tripId);
    }

    @Override
    public TableItem getNearestTableItem(Trip trip) {
        Assert.notNull(trip, "trip must not be null");
        Assert.notEmpty(trip.getTable(), "trip.table must not be empty");
        Arrays.sort(trip.getTable(), new TableItemDateBeginComparator());
        return trip.getTable()[0];
    }

    @Override
    public TableItem getNearestTableItemByPeriod(Trip trip, SearchPeriod period) {
        Assert.notNull(trip, "trip must not be null");
        Assert.notEmpty(trip.getTable(), "trip.table must not be empty");
        Arrays.sort(trip.getTable(), new TableItemDateBeginComparator());
        for (TableItem item : trip.getTable()) {
            if (period.getDateBegin() != null && item.getBegin() != null
                    && item.getBegin().before(period.getDateBegin())) {
                continue;
            }
            return item;
        }
        return null;
    }

    @Override
    public TableItem getLatestTableItem(String tripId) {
        Assert.notNull(tripId, "tripId must not be null");
        TableItem[] items = tripRepository.getTableItemsWithDateBeginAndDateEnd(tripId);
        if (items != null) {
            Arrays.sort(items, new TableItemDateEndComparator());
            return items[items.length - 1];
        }
        return null;
    }

    @Override
    public Trip getTripInfo(String tripId, Locale locale) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(locale, "locale must not be null");
        return tripRepository.getInfo(tripId, locale);
    }

    @Override
    public void updateTripInfo(Trip trip, Locale locale) {
        Assert.notNull(trip, "trip must not be null");
        Assert.notNull(locale, "locale must not be null");
        Assert.notNull(trip.getId(), "trip.id must not be null");
        Assert.notNull(trip.getMainLang(), "mainLang must not be empty");
        Assert.notEmpty(trip.getName(), "name must not be empty");
        Assert.notEmpty(trip.getSummary(), "summary must not be empty");
        Assert.notEmpty(trip.getDescription(), "description must not be empty");
        Assert.notEmpty(trip.getLangs(), "langs must not be empty");

        // update categories and categoryIds
        updateCategories(trip);

        // update regions and regionIds
        updateRegions(trip);

        if (trip.getTable() != null) {
            for (TableItem item : trip.getTable()) {
                if (item.getId() == null) {
                    item.setId(UUID.randomUUID().toString());
                }
                if (item.getStatus() == null) {
                    item.setStatus(new TableStatus(TableStatusEnum.OK));
                }
            }
        }

        // set lang
        String lang = LanguageUtils.getLanguageByLocale(locale);
        String mainLang = tripRepository.getMainLanguage(trip.getId()).getMainLang();
        trip.setMainLang(mainLang);

        if (mainLang.equals(lang)) {
            trip.getName().setMainLanguage(mainLang);
            trip.getSummary().setMainLanguage(mainLang);
            trip.getDescription().setMainLanguage(mainLang);
            if (trip.getRoute() != null && trip.getRoute().getText() != null) {
                trip.getRoute().getText().setMainLanguage(mainLang);
            }
        }

        if (trip.getPostIds() == null)
            trip.setPostIds(new String[0]);

        // update to db
        tripRepository.updateInfo(trip, locale);

        // save to solr
        solrContentService.saveContent(trip.getId());

        // set trip organizers
        //setOrganizers(trip);
    }

    @Override
    public void removeTripLocale(String tripId, Locale locale) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(locale, "locale must not be null");
        String removeLang = LanguageUtils.getLanguageByLocale(locale);
        Trip trip = tripRepository.getMainLanguage(tripId);
        if (trip == null) {
            throw new IllegalArgumentException(String.format("trip with id=%s is not found", tripId));
        }
        String mainLang = trip.getMainLang();
        if (mainLang == null) {
            throw new IllegalStateException(String.format("trip with id=%s does not have a main language", tripId));
        }
        if (mainLang.equals(removeLang)) {
            throw errorService.createException(TripException.class, ErrorEnum.REMOVE_TRIP_LANGUAGE_FAILURE);
        }
        tripRepository.removeLanguage(tripId, locale);

        // save to solr
        solrContentService.saveContent(tripId);
    }

    @Override
    public void abortTableItem(String tripId, String tableId, String cause) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(tableId, "tableId must not be null");
        TableStatus status = new TableStatus();
        status.setValue(TableStatusEnum.ABORTED);
        status.setText(cause);
        tripRepository.setTableStatus(tripId, tableId, status);
    }

    @Override
    public void cancelTableItem(String tripId, String tableId, String cause) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(tableId, "tableId must not be null");
        TableStatus status = new TableStatus();
        status.setValue(TableStatusEnum.CANCELED);
        status.setText(cause);
        tripRepository.setTableStatus(tripId, tableId, status);
    }

    @Override
    public List<TableItem> getTableItems(String tripId) {
        Assert.notNull(tripId, "tripId must not be null");
        TableItem[] items = tripRepository.getTableItemsWithDateBeginAndDateEnd(tripId);
        Arrays.sort(items, new TableItemDateBeginComparator());
        return Arrays.asList(items);
    }

    @Override
    public void addPost(String tripId, String postId) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(postId, "postId must not be null");
        tripRepository.addPostId(tripId, postId);
    }

    @Override
    public String[] getPostIds(String tripId) {
        Assert.notNull(tripId, "tripId must not be null");
        return tripRepository.getPostIds(tripId);
    }

    @Override
    public void removePost(String tripId, String postId) {
        Assert.notNull(tripId, "tripId must not be null");
        Assert.notNull(postId, "postId must not be null");
        tripRepository.removePostId(tripId, postId);
    }
}

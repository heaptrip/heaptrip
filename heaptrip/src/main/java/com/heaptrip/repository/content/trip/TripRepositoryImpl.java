package com.heaptrip.repository.content.trip;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.entity.content.trip.TableItem;
import com.heaptrip.domain.entity.content.trip.TableStatus;
import com.heaptrip.domain.entity.content.trip.Trip;
import com.heaptrip.domain.repository.content.FavoriteContentRepository;
import com.heaptrip.domain.repository.content.trip.TripMemberRepository;
import com.heaptrip.domain.repository.content.trip.TripRepository;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import com.heaptrip.domain.service.content.trip.criteria.TripFeedCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripForeignAccountCriteria;
import com.heaptrip.domain.service.content.trip.criteria.TripMyAccountCriteria;
import com.heaptrip.repository.content.FeedRepositoryImpl;
import com.heaptrip.repository.helper.QueryHelper;
import com.heaptrip.repository.helper.QueryHelperFactory;
import com.heaptrip.util.language.LanguageUtils;
import com.mongodb.WriteResult;
import org.apache.commons.lang.ArrayUtils;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class TripRepositoryImpl extends FeedRepositoryImpl<Trip> implements TripRepository {

    private static final Logger logger = LoggerFactory.getLogger(TripRepositoryImpl.class);

    @Autowired
    private TripMemberRepository tripMemberRepository;

    @Autowired
    private FavoriteContentRepository favoriteContentRepository;

    @Autowired
    private QueryHelperFactory queryHelperFactory;

    @Override
    protected String getCollectionName() {
        return CollectionEnum.CONTENTS.getName();
    }

    @Override
    protected Class<Trip> getCollectionClass() {
        return Trip.class;
    }

    @Override
    public List<Trip> findByFeedCriteria(TripFeedCriteria criteria) {
        QueryHelper<TripFeedCriteria> queryHelper = queryHelperFactory.getHelperByCriteria(TripFeedCriteria.class);
        return findByCriteria(criteria, queryHelper);
    }

    @Override
    public List<Trip> findByMyAccountCriteria(TripMyAccountCriteria criteria) {
        List<String> tripIds = null;
        if (criteria.getRelation().equals(RelationEnum.MEMBER)) {
            tripIds = tripMemberRepository.findTripIdsByUserId(criteria.getUserId());
        } else if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
            tripIds = favoriteContentRepository
                    .findIdsByContentTypeAndAccountId(ContentEnum.TRIP, criteria.getUserId());
        }
        QueryHelper<TripMyAccountCriteria> queryHelper = queryHelperFactory
                .getHelperByCriteria(TripMyAccountCriteria.class);
        return findByCriteria(criteria, queryHelper, tripIds);
    }

    @Override
    public List<Trip> findByForeignAccountCriteria(TripForeignAccountCriteria criteria) {
        List<String> tripIds = null;
        if (criteria.getRelation().equals(RelationEnum.MEMBER)) {
            tripIds = tripMemberRepository.findTripIdsByUserId(criteria.getAccountId());
        } else if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
            tripIds = favoriteContentRepository.findIdsByContentTypeAndAccountId(ContentEnum.TRIP,
                    criteria.getAccountId());
        }
        QueryHelper<TripForeignAccountCriteria> queryHelper = queryHelperFactory
                .getHelperByCriteria(TripForeignAccountCriteria.class);
        return findByCriteria(criteria, queryHelper, tripIds);
    }

    @Override
    public long getCountByFeedCriteria(TripFeedCriteria criteria) {
        QueryHelper<TripFeedCriteria> queryHelper = queryHelperFactory.getHelperByCriteria(TripFeedCriteria.class);
        return getCountByCriteria(criteria, queryHelper);
    }

    @Override
    public long getCountByMyAccountCriteria(TripMyAccountCriteria criteria) {
        List<String> tripIds = null;
        if (criteria.getRelation().equals(RelationEnum.MEMBER)) {
            tripIds = tripMemberRepository.findTripIdsByUserId(criteria.getUserId());
        } else if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
            tripIds = favoriteContentRepository
                    .findIdsByContentTypeAndAccountId(ContentEnum.TRIP, criteria.getUserId());
        }
        QueryHelper<TripMyAccountCriteria> queryHelper = queryHelperFactory
                .getHelperByCriteria(TripMyAccountCriteria.class);
        return getCountByCriteria(criteria, queryHelper, tripIds);
    }

    @Override
    public long getCountByForeignAccountCriteria(TripForeignAccountCriteria criteria) {
        List<String> tripIds = null;
        if (criteria.getRelation().equals(RelationEnum.MEMBER)) {
            tripIds = tripMemberRepository.findTripIdsByUserId(criteria.getAccountId());
        } else if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
            tripIds = favoriteContentRepository.findIdsByContentTypeAndAccountId(ContentEnum.TRIP,
                    criteria.getAccountId());
        }
        QueryHelper<TripForeignAccountCriteria> queryHelper = queryHelperFactory
                .getHelperByCriteria(TripForeignAccountCriteria.class);
        return getCountByCriteria(criteria, queryHelper, tripIds);
    }

    @Override
    public Trip getInfo(String tripId, Locale locale) {
        MongoCollection coll = getCollection();
        String query = "{_id: #}";
        String lang = LanguageUtils.getLanguageByLocale(locale);
        String projection = String
                .format("{_class: 1, owner: 1, 'categories._id': 1, 'categories.name.%s': 1, 'regions._id': 1, 'regions.name.%s': 1,"
                        + " status: 1, 'name.%s': 1, 'name.main': 1, 'summary.%s': 1, 'summary.main': 1, 'description.%s': 1, 'description.main': 1,"
                        + " 'table._id': 1, 'table.begin': 1, 'table.end': 1, 'table.min': 1, 'table.max': 1, 'table.status': 1, 'table.users': 1,"
                        + " 'table.price': 1, image: 1, created: 1, owners:1, 'views.count': 1, mainLang: 1, rating: 1, comments: 1, langs: 1,"
                        + " 'route._id': 1, 'route.text.main': 1, 'route.text.%s': 1, 'route.map': 1, postIds: 1}",
                        lang, lang, lang, lang, lang, lang);
        if (logger.isDebugEnabled()) {
            String msg = String.format("get trip info\n->query: %s\n->parameters: %s\n->projection: %s", query, tripId,
                    projection);
            logger.debug(msg);
        }
        return coll.findOne(query, tripId).projection(projection).as(Trip.class);
    }

    @Override
    public void updateInfo(Trip trip, Locale locale) {
        String query = "{_id: #}";

        String updateQuery = null;
        List<Object> parameters = new ArrayList<>();

        String lang = LanguageUtils.getLanguageByLocale(locale);
        String mainLang = trip.getMainLang();

        if (mainLang.equals(lang)) {
            // update main language
            updateQuery = String
                    .format("{$addToSet: {langs: #}, $set: {categories: #, categoryIds: #, regions: #, regionIds: #, 'name.main': #, 'name.%s': #, "
                            + "'summary.main': #, 'summary.%s': #, 'description.main': #, 'description.%s': #, image: #, table: #,"
                            + "'route.text.main': #, 'route.text.%s': #, 'route.map': #, postIds: #}}", lang, lang,
                            lang, lang);

            parameters.add(lang);
            parameters.add(trip.getCategories());
            parameters.add(trip.getCategoryIds());
            parameters.add(trip.getRegions());
            parameters.add(trip.getRegionIds());
            parameters.add(trip.getName().getValue(locale));
            parameters.add(trip.getName().getValue(locale));
            parameters.add(trip.getSummary().getValue(locale));
            parameters.add(trip.getSummary().getValue(locale));
            parameters.add(trip.getDescription().getValue(locale));
            parameters.add(trip.getDescription().getValue(locale));
            parameters.add(trip.getImage());
            parameters.add(trip.getTable());
            parameters.add(trip.getRoute().getText().getValue(locale));
            parameters.add(trip.getRoute().getText().getValue(locale));
            parameters.add(trip.getRoute().getMap());
            parameters.add(trip.getPostIds());
        } else {
            updateQuery = String
                    .format("{$addToSet: {langs: #}, $set: {categories: #, categoryIds: #, regions: #, regionIds: #, 'name.%s': #, 'summary.%s': #,"
                            + " 'description.%s': #, image: #, table: #, 'route.text.%s': #, 'route.map': #, postIds: #}}",
                            lang, lang, lang, lang);

            parameters.add(lang);
            parameters.add(trip.getCategories());
            parameters.add(trip.getCategoryIds());
            parameters.add(trip.getRegions());
            parameters.add(trip.getRegionIds());
            parameters.add(trip.getName().getValue(locale));
            parameters.add(trip.getSummary().getValue(locale));
            parameters.add(trip.getDescription().getValue(locale));
            parameters.add(trip.getImage());
            parameters.add(trip.getTable());
            parameters.add(trip.getRoute().getText().getValue(locale));
            parameters.add(trip.getRoute().getMap());
            parameters.add(trip.getPostIds());
        }

        if (logger.isDebugEnabled()) {
            String msg = String.format(
                    "update trip info\n->query: %s\n->parameters: %s\n->updateQuery: %s\n->updateParameters: %s",
                    query, trip.getId(), updateQuery, ArrayUtils.toString(parameters));
            logger.debug(msg);
        }

        MongoCollection coll = getCollection();
        WriteResult wr = coll.update(query, trip.getId()).with(updateQuery, parameters.toArray());
        logger.debug("WriteResult for update trip info: {}", wr);
    }

    @Override
    public void incTableMembers(String tripId, String tableId, int value) {
        String query = "{_id: #, 'table._id': #}";
        List<Object> parameters = new ArrayList<>();
        parameters.add(tripId);
        parameters.add(tableId);
        String updateQuery = "{$inc: {'table.$.members': #}}";
        if (logger.isDebugEnabled()) {
            String msg = String.format(
                    "inc table members\n->query: %s\n->parameters: %s\n->updateQuery: %s\n->updateParameters: %s",
                    query, ArrayUtils.toString(parameters), updateQuery, value);
            logger.debug(msg);
        }
        MongoCollection coll = getCollection();
        WriteResult wr = coll.update(query, parameters.toArray()).with(updateQuery, value);
        logger.debug("WriteResult for inc table members: {}", wr);
    }

    @Override
    public void setTableStatus(String tripId, String tableId, TableStatus status) {
        MongoCollection coll = getCollection();
        String query = "{_id: #, 'table._id': #}";
        List<Object> parameters = new ArrayList<>();
        parameters.add(tripId);
        parameters.add(tableId);
        String updateQuery = "{$set: {'table.$.status': #}}";
        if (logger.isDebugEnabled()) {
            String msg = String
                    .format("update table item status\n->query: %s\n->parameters: %s\n->updateQuery: %s\n->updateParameters: %s",
                            query, ArrayUtils.toString(parameters), updateQuery, status);
            logger.debug(msg);
        }
        WriteResult wr = coll.update(query, parameters.toArray()).with(updateQuery, status);
        logger.debug("WriteResult for set table status: {}", wr);
    }

    @Override
    public Trip getMainLanguage(String tripId) {
        MongoCollection coll = getCollection();
        return coll.findOne("{_id: #}", tripId).projection("{_class: 1, mainLang: 1}").as(getCollectionClass());
    }

    @Override
    public void removeLanguage(String tripId, Locale locale) {
        String lang = LanguageUtils.getLanguageByLocale(locale);

        MongoCollection coll = getCollection();
        String query = "{_id: #}";
        String updateQuery = String
                .format("{$pull: {langs: #}, $unset: {'name.%s': 1, 'summary.%s': 1, 'description.%s': 1, 'route.text.%s': 1}}",
                        lang, lang, lang, lang);

        if (logger.isDebugEnabled()) {
            String msg = String.format(
                    "remove language\n->query: %s\n->parameters: [%s]\n->updateQuery: %s\n->updateParameters: [%s]",
                    query, tripId, updateQuery, lang);
            logger.debug(msg);
        }
        WriteResult wr = coll.update(query, tripId).with(updateQuery, lang);
        logger.debug("WriteResult for set table status: {}", wr);
    }

    @Override
    public TableItem[] getTableItemsWithDateBeginAndDateEnd(String tripId) {
        MongoCollection coll = getCollection();
        Trip trip = coll.findOne("{_id: #}", tripId)
                .projection("{_class: 1, 'table._id': 1, 'table.begin': 1, 'table.end': 1}").as(getCollectionClass());
        return (trip == null) ? null : trip.getTable();
    }

    @Override
    public void addPostId(String tripId, String postId) {
        MongoCollection coll = getCollection();
        WriteResult wr = coll.update("{_id: #}", tripId).with("{$push: {postIds: #}}", postId);
        logger.debug("WriteResult for add post id: {}", wr);
    }

    @Override
    public void removePostId(String tripId, String postId) {
        MongoCollection coll = getCollection();
        WriteResult wr = coll.update("{_id: #}", tripId).with("{$pull: {postIds: #}}", postId);
        logger.debug("WriteResult for remove post id: {}", wr);
    }

    @Override
    public String[] getPostIds(String tripId) {
        MongoCollection coll = getCollection();
        Trip trip = coll.findOne("{_id: #}", tripId).projection("{_class: 1, postIds: 1}").as(getCollectionClass());
        return (trip == null) ? null : trip.getPostIds();
    }
}
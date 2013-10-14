package com.heaptrip.repository.content.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.event.Event;
import com.heaptrip.domain.repository.content.FavoriteContentRepository;
import com.heaptrip.domain.repository.content.event.EventRepository;
import com.heaptrip.domain.service.content.criteria.FeedCriteria;
import com.heaptrip.domain.service.content.criteria.ForeignAccountCriteria;
import com.heaptrip.domain.service.content.criteria.MyAccountCriteria;
import com.heaptrip.domain.service.content.criteria.RelationEnum;
import com.heaptrip.domain.service.content.event.criteria.EventFeedCriteria;
import com.heaptrip.domain.service.content.event.criteria.EventForeignAccountCriteria;
import com.heaptrip.domain.service.content.event.criteria.EventMyAccountCriteria;
import com.heaptrip.repository.content.FeedRepositoryImpl;
import com.heaptrip.repository.helper.QueryHelper;
import com.heaptrip.repository.helper.QueryHelperFactory;
import com.heaptrip.util.language.LanguageUtils;
import com.mongodb.WriteResult;

@Service
public class EventRepositoryImpl extends FeedRepositoryImpl<Event> implements EventRepository {

	private static final Logger logger = LoggerFactory.getLogger(EventRepositoryImpl.class);

	@Autowired
	private QueryHelperFactory queryHelperFactory;

	@Autowired
	private FavoriteContentRepository favoriteContentRepository;

	@Override
	public List<Event> findByFeedCriteria(EventFeedCriteria criteria) {
		QueryHelper<EventFeedCriteria> queryHelper = queryHelperFactory.getHelperByCriteria(EventFeedCriteria.class);
		return findByCriteria(criteria, queryHelper);
	}

	@Override
	public List<Event> findByMyAccountCriteria(EventMyAccountCriteria criteria) {
		List<String> eventIds = null;
		if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
			eventIds = favoriteContentRepository.findIdsByContentTypeAndAccountId(criteria.getContentType(),
					criteria.getUserId());
		}
		QueryHelper<MyAccountCriteria> queryHelper = queryHelperFactory.getHelperByCriteria(MyAccountCriteria.class);
		return findByCriteria(criteria, queryHelper, eventIds);
	}

	@Override
	public List<Event> findByForeignAccountCriteria(EventForeignAccountCriteria criteria) {
		List<String> eventIds = null;
		if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
			eventIds = favoriteContentRepository.findIdsByContentTypeAndAccountId(criteria.getContentType(),
					criteria.getAccountId());
		}
		QueryHelper<ForeignAccountCriteria> queryHelper = queryHelperFactory
				.getHelperByCriteria(ForeignAccountCriteria.class);
		return findByCriteria(criteria, queryHelper, eventIds);
	}

	@Override
	public long getCountByFeedCriteria(EventFeedCriteria criteria) {
		QueryHelper<FeedCriteria> queryHelper = queryHelperFactory.getHelperByCriteria(FeedCriteria.class);
		return getCountByCriteria(criteria, queryHelper);
	}

	@Override
	public long getCountByMyAccountCriteria(EventMyAccountCriteria criteria) {
		List<String> eventIds = null;
		if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
			eventIds = favoriteContentRepository.findIdsByContentTypeAndAccountId(criteria.getContentType(),
					criteria.getUserId());
		}
		QueryHelper<MyAccountCriteria> queryHelper = queryHelperFactory.getHelperByCriteria(MyAccountCriteria.class);
		return getCountByCriteria(criteria, queryHelper, eventIds);
	}

	@Override
	public long getCountByForeignAccountCriteria(EventForeignAccountCriteria criteria) {
		List<String> eventIds = null;
		if (criteria.getRelation().equals(RelationEnum.FAVORITES)) {
			eventIds = favoriteContentRepository.findIdsByContentTypeAndAccountId(criteria.getContentType(),
					criteria.getAccountId());
		}
		QueryHelper<ForeignAccountCriteria> queryHelper = queryHelperFactory
				.getHelperByCriteria(ForeignAccountCriteria.class);
		return getCountByCriteria(criteria, queryHelper, eventIds);
	}

	@Override
	protected Class<Event> getCollectionClass() {
		return Event.class;
	}

	@Override
	protected String getCollectionName() {
		return CollectionEnum.CONTENTS.getName();
	}

	@Override
	public Event findByIdAndLocale(String contentId, Locale locale) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String lang = LanguageUtils.getLanguageByLocale(locale);
		String projection = String
				.format("{_class: 1, owner: 1, 'categories._id': 1, 'categories.name.%s': 1, 'regions._id': 1, 'regions.name.%s': 1,"
						+ " status: 1, 'name.%s': 1, 'name.main': 1, 'summary.%s': 1, 'summary.main': 1, 'description.%s': 1, 'description.main': 1,"
						+ " image: 1, created: 1, owners:1, 'views.count': 1, mainLang: 1, rating: 1, comments: 1, langs: 1,"
						+ " 'types._id': 1, 'types.name.%s': 1, members: 1, price: 1, map: 1, showMap: 1}", lang, lang,
						lang, lang, lang, lang);
		if (logger.isDebugEnabled()) {
			String msg = String.format("find event\n->query: %s\n->parameters: %s\n->projection: %s", query, contentId,
					projection);
			logger.debug(msg);
		}
		return coll.findOne(query, contentId).projection(projection).as(getCollectionClass());
	}

	@Override
	public void update(Event event, Locale locale) {
		String query = "{_id: #}";

		String updateQuery = null;
		List<Object> parameters = new ArrayList<>();

		String lang = LanguageUtils.getLanguageByLocale(locale);
		String mainLang = event.getMainLang();

		if (mainLang.equals(lang)) {
			// update main language
			updateQuery = String
					.format("{$addToSet: {langs: #}, $set: {categories: #, categoryIds: #, regions: #, regionIds: #, 'name.main': #, 'name.%s': #, "
							+ "'summary.main': #, 'summary.%s': #, 'description.main': #, 'description.%s': #, image: #, types: #, price: #, map: #, showMap: #}}",
							lang, lang, lang, lang);

			parameters.add(lang);
			parameters.add(event.getCategories());
			parameters.add(event.getCategoryIds());
			parameters.add(event.getRegions());
			parameters.add(event.getRegionIds());
			parameters.add(event.getName().getValue(locale));
			parameters.add(event.getName().getValue(locale));
			parameters.add(event.getSummary().getValue(locale));
			parameters.add(event.getSummary().getValue(locale));
			parameters.add(event.getDescription().getValue(locale));
			parameters.add(event.getDescription().getValue(locale));
			parameters.add(event.getImage());
			parameters.add(event.getTypes());
			parameters.add(event.getPrice());
			parameters.add(event.getMap());
			parameters.add(event.isShowMap());

		} else {
			updateQuery = String
					.format("{$addToSet: {langs: #}, $set: {categories: #, categoryIds: #, regions: #, regionIds: #, 'name.%s': #, 'summary.%s': #,"
							+ " 'description.%s': #, image: #, types: #, price: #, map: #, showMap: #}}", lang, lang,
							lang, lang);

			parameters.add(lang);
			parameters.add(event.getCategories());
			parameters.add(event.getCategoryIds());
			parameters.add(event.getRegions());
			parameters.add(event.getRegionIds());
			parameters.add(event.getName().getValue(locale));
			parameters.add(event.getSummary().getValue(locale));
			parameters.add(event.getDescription().getValue(locale));
			parameters.add(event.getImage());
			parameters.add(event.getTypes());
			parameters.add(event.getPrice());
			parameters.add(event.getMap());
			parameters.add(event.isShowMap());

		}

		if (logger.isDebugEnabled()) {
			String msg = String.format(
					"update event\n->query: %s\n->parameters: %s\n->updateQuery: %s\n->updateParameters: %s", query,
					event.getId(), updateQuery, ArrayUtils.toString(parameters));
			logger.debug(msg);
		}

		MongoCollection coll = getCollection();
		WriteResult wr = coll.update(query, event.getId()).with(updateQuery, parameters.toArray());
		logger.debug("WriteResult for update event: {}", wr);
	}

	@Override
	public void removeLanguage(String contentId, Locale locale) {
		String lang = LanguageUtils.getLanguageByLocale(locale);

		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = String
				.format("{$pull: {langs: #}, $unset: {'name.%s': 1, 'summary.%s': 1, 'description.%s': 1, 'route.text.%s': 1}}",
						lang, lang, lang, lang);

		if (logger.isDebugEnabled()) {
			String msg = String.format(
					"remove language\n->query: %s\n->parameters: [%s]\n->updateQuery: %s\n->updateParameters: [%s]",
					query, contentId, updateQuery, lang);
			logger.debug(msg);
		}
		WriteResult wr = coll.update(query, contentId).with(updateQuery, lang);
		logger.debug("WriteResult for remove language from event: {}", wr);
	}

	@Override
	public void incMembers(String contentId, int value) {
		String query = "{_id: #}";
		String updateQuery = "{$inc: {'members': #}}";
		if (logger.isDebugEnabled()) {
			String msg = String.format(
					"inc event members\n->query: %s\n->parameters: %s\n->updateQuery: %s\n->updateParameters: %s",
					query, contentId, updateQuery, value);
			logger.debug(msg);
		}
		MongoCollection coll = getCollection();
		WriteResult wr = coll.update(query, contentId).with(updateQuery, value);
		logger.debug("WriteResult for inc event members: {}", wr);
	}

	@Override
	public void resetMembers(String contentId) {
		MongoCollection coll = getCollection();
		WriteResult wr = coll.update("{_id: #}", contentId).with("{$set: {members: 0}}");
		logger.debug("WriteResult for reset event members: {}", wr);
	}

}

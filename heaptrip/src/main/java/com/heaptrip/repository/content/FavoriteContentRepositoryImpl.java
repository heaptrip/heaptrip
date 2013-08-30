package com.heaptrip.repository.content;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentEnum;
import com.heaptrip.domain.repository.content.FavoriteContentRepository;
import com.heaptrip.repository.BaseRepositoryImpl;
import com.heaptrip.repository.content.helper.QueryHelperFactory;
import com.heaptrip.util.collection.IteratorConverter;
import com.mongodb.WriteResult;

@Service
public class FavoriteContentRepositoryImpl extends BaseRepositoryImpl implements FavoriteContentRepository {

	private static final Logger logger = LoggerFactory.getLogger(FavoriteContentRepositoryImpl.class);

	@Override
	protected String getCollectionName() {
		return CollectionEnum.CONTENTS.getName();
	}

	@Override
	public void addFavorite(String contentId, String accountId) {
		MongoCollection coll = getCollection();
		WriteResult wr = coll
				.update("{_id: #, 'favorites.ids': {$not: {$in: #}}}", contentId, Arrays.asList(accountId)).with(
						"{$push: {'favorites.ids': #}, $inc: {'favorites.count': 1}}", accountId);
		logger.debug("WriteResult for add favorite: {}", wr);
	}

	@Override
	public void removeFavorite(String contentId, String accountId) {
		MongoCollection coll = getCollection();
		WriteResult wr = coll.update("{_id: #, 'favorites.ids': #}", contentId, Arrays.asList(accountId)).with(
				"{$pull: {'favorites.ids': #}, $inc: {'favorites.count': -1}}", accountId);
		logger.debug("WriteResult for remove favorite: {}", wr);
	}

	@Override
	public List<Content> findByAccountId(String accountId, Locale locale) {
		String fields = QueryHelperFactory.getInstance(QueryHelperFactory.FEED_HELPER).getProjection(locale);
		MongoCollection coll = getCollection();
		Iterable<Content> iter = coll.find("{'favorites.ids': #}", accountId).projection(fields)
				.hint("{'favorites.ids': 1}").as(Content.class);
		return IteratorConverter.copyIterator(iter.iterator());
	}

	@Override
	public List<Content> findByContentTypeAndAccountId(ContentEnum contentType, String accountId, Locale locale) {
		String fields = QueryHelperFactory.getInstance(QueryHelperFactory.FEED_HELPER).getProjection(locale);
		MongoCollection coll = getCollection();
		Iterable<Content> iter = coll.find("{_class: #, 'favorites.ids': #}", contentType.getClazz(), accountId)
				.projection(fields).hint("{_class: 1, 'favorites.ids': 1}").as(Content.class);
		return IteratorConverter.copyIterator(iter.iterator());
	}

	@Override
	public boolean exists(String contentId, String accountId) {
		MongoCollection coll = getCollection();
		Content content = coll.findOne("{_id: #, 'favorites.ids': #}", contentId, accountId).as(Content.class);
		return (content == null) ? false : true;
	}

	@Override
	public List<String> findIdsByContentTypeAndAccountId(ContentEnum contentType, String accountId) {
		MongoCollection coll = getCollection();
		Iterator<Content> iter = coll.find("{_class: #, 'favorites.ids': #}", contentType.getClazz(), accountId)
				.projection("{_class: 1}").hint("{_class: 1, 'favorites.ids': 1}").as(Content.class).iterator();

		List<String> result = new ArrayList<>();
		if (iter != null) {
			while (iter.hasNext()) {
				Content content = iter.next();
				result.add(content.getId());
			}
		}
		return result;
	}
}

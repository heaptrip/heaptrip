package com.heaptrip.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.ContentEnum;
import com.heaptrip.domain.entity.FavoriteContent;
import com.heaptrip.domain.repository.FavoriteContentRepository;
import com.heaptrip.domain.repository.MongoContext;
import com.heaptrip.util.collection.IteratorConverter;
import com.mongodb.WriteResult;

@Service
public class FavoriteContentRepositoryImpl implements FavoriteContentRepository {

	private static final Logger logger = LoggerFactory.getLogger(FavoriteContentRepositoryImpl.class);

	@Autowired
	private MongoContext mongoContext;

	@Override
	public void save(FavoriteContent favoriteContent) {
		MongoCollection coll = mongoContext.getCollection(FavoriteContent.COLLECTION_NAME);
		coll.save(favoriteContent);
	}

	@Override
	public List<FavoriteContent> findByTypeAndUserId(ContentEnum contentType, String userId) {
		MongoCollection coll = mongoContext.getCollection(FavoriteContent.COLLECTION_NAME);
		String query = "{userId: #, type: #}";
		String hint = "{userId: 1, type: 1}";
		if (logger.isDebugEnabled()) {
			String msg = String.format("find favorites\n->query: %s\n->parameters: [%s,%s]\n->hint: %s", query, userId,
					contentType, hint);
			logger.debug(msg);
		}
		Iterator<FavoriteContent> iter = coll.find(query, userId, contentType).hint(hint).as(FavoriteContent.class)
				.iterator();
		return IteratorConverter.copyIterator(iter);
	}

	@Override
	public List<FavoriteContent> findByUserId(String userId) {
		MongoCollection coll = mongoContext.getCollection(FavoriteContent.COLLECTION_NAME);
		String query = "{userId: #}";
		String hint = "{userId: 1, type: 1}";
		if (logger.isDebugEnabled()) {
			String msg = String.format("find favorites\n->query: %s\n->parameters: [%s]\n->hint: %s", query, userId,
					hint);
			logger.debug(msg);
		}
		Iterator<FavoriteContent> iter = coll.find(query, userId).hint(hint).as(FavoriteContent.class).iterator();
		return IteratorConverter.copyIterator(iter);
	}

	@Override
	public FavoriteContent findOneByContentIdAndUserId(String contentId, String userId) {
		MongoCollection coll = mongoContext.getCollection(FavoriteContent.COLLECTION_NAME);
		String query = "{userId: #, contentId: #}";
		if (logger.isDebugEnabled()) {
			String msg = String.format("find favorite\n->query: %s\n->parameters: [%s,%s]", query, userId, contentId);
			logger.debug(msg);
		}
		// XXX check index
		return coll.findOne(query, userId, contentId).as(FavoriteContent.class);
	}

	@Override
	public void removeByContentIdAndUserId(String contentId, String userId) {
		MongoCollection coll = mongoContext.getCollection(FavoriteContent.COLLECTION_NAME);
		// XXX check index
		WriteResult wr = coll.remove("{userId: #, contentId: #}", userId, contentId);
		logger.debug("WriteResult for remove favorite: {}", wr);
	}

	@Override
	public List<String> findContentIdsByTypeAndUserId(ContentEnum contentType, String userId) {
		MongoCollection coll = mongoContext.getCollection(FavoriteContent.COLLECTION_NAME);
		String query = "{userId: #, type: #}";
		String hint = "{userId: 1, type: 1}";
		String fields = "{contentId: 1}";
		if (logger.isDebugEnabled()) {
			String msg = String.format(
					"find favorites\n->query: %s\n->parameters: [%s,%s]\n->projection: %s\n->hint: %s", query, userId,
					contentType, fields, hint);
			logger.debug(msg);
		}
		Iterator<FavoriteContent> iter = coll.find(query, userId, contentType).projection(fields).hint(hint)
				.as(FavoriteContent.class).iterator();
		List<String> result = new ArrayList<>();
		if (iter != null) {
			while (iter.hasNext()) {
				FavoriteContent fc = iter.next();
				result.add(fc.getContentId());
			}
		}
		return result;
	}

}

package com.heaptrip.repository;

import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.Content;
import com.heaptrip.domain.entity.ContentStatusEnum;
import com.heaptrip.domain.repository.ContentRepository;
import com.heaptrip.domain.repository.MongoContext;
import com.mongodb.WriteResult;

@Service
public class ContentRepositoryImpl implements ContentRepository {
	private static final Logger logger = LoggerFactory.getLogger(ContentRepositoryImpl.class);

	@Autowired
	private MongoContext mongoContext;

	@Override
	public Content findById(String contentId) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		return coll.findOne("{ _id: #}", contentId).as(Content.class);
	}

	@Override
	public void setStatus(String tripId, ContentStatusEnum status, String[] allowed) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		WriteResult wr = coll.update("{_id: #}", tripId).with("{$set: {'status.value': #, allowed: #}}", status,
				allowed);
		logger.debug("WriteResult for set status: {}", wr);
	}

	@Override
	public void incViews(String tripId) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		WriteResult wr = coll.update("{_id: #}", tripId).with("{$inc: {views: 1}}");
		logger.debug("WriteResult for inc views: {}", wr);
	}
}

package com.heaptrip.repository.content;

import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.content.Content;
import com.heaptrip.domain.entity.content.ContentStatusEnum;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.mongodb.WriteResult;

@Service
public class ContentRepositoryImpl extends CrudRepositoryImpl<Content> implements ContentRepository {

	private static final Logger logger = LoggerFactory.getLogger(ContentRepositoryImpl.class);

	@Override
	public void setStatus(String tripId, ContentStatusEnum status, String[] allowed) {
		MongoCollection coll = getCollection();
		WriteResult wr = coll.update("{_id: #}", tripId).with("{$set: {'status.value': #, allowed: #}}", status,
				allowed);
		logger.debug("WriteResult for set status: {}", wr);
	}

	@Override
	public void incViews(String tripId) {
		MongoCollection coll = getCollection();
		WriteResult wr = coll.update("{_id: #}", tripId).with("{$inc: {views: 1}}");
		logger.debug("WriteResult for inc views: {}", wr);
	}

	@Override
	protected String getCollectionName() {
		return Content.COLLECTION_NAME;
	}

	@Override
	protected Class<Content> getCollectionClass() {
		return Content.class;
	}
}

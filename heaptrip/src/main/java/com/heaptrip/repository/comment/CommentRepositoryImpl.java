package com.heaptrip.repository.comment;

import java.util.List;

import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.comment.Comment;
import com.heaptrip.domain.repository.comment.CommentRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.util.collection.IteratorConverter;
import com.mongodb.WriteResult;

@Service
public class CommentRepositoryImpl extends CrudRepositoryImpl<Comment> implements CommentRepository {

	private static final Logger logger = LoggerFactory.getLogger(CommentRepositoryImpl.class);

	@Override
	public List<Comment> findByTargetIdOrderByFullSlugAsc(String targetId) {
		MongoCollection coll = getCollection();
		String hint = "{target: 1, fullSlug: 1}";
		Iterable<Comment> iter = coll.find("{target: #}", targetId).hint(hint).sort("{fullSlug: 1}").as(Comment.class);
		return IteratorConverter.copyIterator(iter.iterator());
	}

	@Override
	public void incCommentsNumber(String collectionName, String numberFieldName, String id, int value) {
		MongoCollection coll = mongoContext.getCollection(collectionName);
		WriteResult wr = coll.update("{_id: #}", id).with("{$inc: {" + numberFieldName + ": #}}", value);
		logger.debug("WriteResult for inc views: {}", wr);
	}

	@Override
	public void removeByTargetId(String targetId) {
		MongoCollection coll = getCollection();
		coll.remove("{target: #}", targetId);
	}

	@Override
	protected String getCollectionName() {
		return Comment.COLLECTION_NAME;
	}

	@Override
	protected Class<Comment> getCollectionClass() {
		return Comment.class;
	}
}

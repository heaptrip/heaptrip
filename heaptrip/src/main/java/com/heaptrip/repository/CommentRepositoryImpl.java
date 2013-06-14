package com.heaptrip.repository;

import java.util.List;

import org.jongo.MongoCollection;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.Comment;
import com.heaptrip.domain.repository.CommentRepository;
import com.heaptrip.util.collection.IteratorConverter;

@Service
public class CommentRepositoryImpl extends CrudRepositoryImpl<Comment> implements CommentRepository {

	@Override
	public List<Comment> findByTargetIdOrderByFullSlugAsc(String targetId) {
		MongoCollection coll = getCollection();
		String hint = "{target: 1, fullSlug: 1}";
		Iterable<Comment> iter = coll.find("{target: #}", targetId).hint(hint).sort("{fullSlug: 1}").as(Comment.class);
		return IteratorConverter.copyIterator(iter.iterator());
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

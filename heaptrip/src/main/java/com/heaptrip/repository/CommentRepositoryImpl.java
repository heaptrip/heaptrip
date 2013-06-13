package com.heaptrip.repository;

import java.util.List;

import org.jongo.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.Comment;
import com.heaptrip.domain.repository.CommentRepository;
import com.heaptrip.domain.repository.MongoContext;
import com.heaptrip.util.collection.IteratorConverter;

@Service
public class CommentRepositoryImpl implements CommentRepository {

	@Autowired
	private MongoContext mongoContext;

	@Override
	public Comment save(Comment comment) {
		MongoCollection coll = mongoContext.getCollection(Comment.COLLECTION_NAME);
		coll.save(comment);
		return comment;
	}

	@Override
	public Comment findById(String commentId) {
		MongoCollection coll = mongoContext.getCollection(Comment.COLLECTION_NAME);
		return coll.findOne("{_id: #}", commentId).as(Comment.class);
	}

	@Override
	public List<Comment> findByTargetIdOrderByFullSlugAsc(String targetId) {
		MongoCollection coll = mongoContext.getCollection(Comment.COLLECTION_NAME);
		String hint = "{target: 1, fullSlug: 1}";
		Iterable<Comment> iter = coll.find("{target: #}", targetId).hint(hint).sort("{fullSlug: 1}").as(Comment.class);
		return IteratorConverter.copyIterator(iter.iterator());
	}

	@Override
	public void removeByTargetId(String targetId) {
		MongoCollection coll = mongoContext.getCollection(Comment.COLLECTION_NAME);
		coll.remove("{target: #}", targetId);
	}
}

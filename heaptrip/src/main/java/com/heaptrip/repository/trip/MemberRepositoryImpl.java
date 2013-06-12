package com.heaptrip.repository.trip;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.Content;
import com.heaptrip.domain.entity.trip.TableMember;
import com.heaptrip.domain.entity.trip.TableUserStatusEnum;
import com.heaptrip.domain.repository.MongoContext;
import com.heaptrip.domain.repository.trip.MemberRepository;
import com.heaptrip.util.collection.IteratorConverter;
import com.mongodb.WriteResult;

@Service
public class MemberRepositoryImpl implements MemberRepository {

	private static final Logger logger = LoggerFactory.getLogger(MemberRepositoryImpl.class);

	@Autowired
	private MongoContext mongoContext;

	@Override
	public void save(TableMember member) {
		MongoCollection coll = mongoContext.getCollection(TableMember.COLLECTION_NAME);
		WriteResult wr = coll.save(member);
		logger.debug("WriteResult for save member: {}", wr);
	}

	@Override
	public void setStatus(String memberId, TableUserStatusEnum status) {
		MongoCollection coll = mongoContext.getCollection(TableMember.COLLECTION_NAME);
		WriteResult wr = coll.update("{_id: #}", memberId).with("{$set: {status: #}}", status);
		logger.debug("WriteResult for set status: {}", wr);
	}

	@Override
	public void setOrganizer(String tableUserId, Boolean isOrganizer) {
		MongoCollection coll = mongoContext.getCollection(TableMember.COLLECTION_NAME);
		WriteResult wr = coll.update("{_id: #}", tableUserId).with("{$set: {isOrganizer: #}}", isOrganizer);
		logger.debug("WriteResult for set organizer: {}", wr);
	}

	@Override
	public List<TableMember> findByTripIdAndTableId(String tripId, String tableId) {
		MongoCollection coll = mongoContext.getCollection(TableMember.COLLECTION_NAME);
		String query = "{tripId: #, tableId: #}";
		String hint = "{tripId: 1, tableId: 1}";
		if (logger.isDebugEnabled()) {
			String msg = String.format("find table members\n->query: %s\n->parameters: [%s,%s]\n->hint: %s", query,
					tripId, tableId, hint);
			logger.debug(msg);
		}
		Iterator<TableMember> iter = coll.find(query, tripId, tableId).hint(hint).as(TableMember.class).iterator();
		return IteratorConverter.copyIterator(iter);
	}

	@Override
	public List<TableMember> findByTripIdAndTableId(String tripId, String tableId, int limit) {
		MongoCollection coll = mongoContext.getCollection(TableMember.COLLECTION_NAME);
		String query = "{tripId: #, tableId: #}";
		String hint = "{tripId: 1, tableId: 1}";
		if (logger.isDebugEnabled()) {
			String msg = String.format(
					"find table members\n->query: %s\n->parameters: [%s,%s]\n->limit: %d\n->hint: %s", query, tripId,
					tableId, limit, hint);
			logger.debug(msg);
		}
		Iterator<TableMember> iter = coll.find(query, tripId, tableId).limit(limit).hint(hint).as(TableMember.class)
				.iterator();
		return IteratorConverter.copyIterator(iter);
	}

	@Override
	public long getCountByTripId(String tripId) {
		MongoCollection coll = mongoContext.getCollection(TableMember.COLLECTION_NAME);
		return coll.count("{tripId: #}", tripId);
	}

	@Override
	public void removeById(String memberId) {
		MongoCollection coll = mongoContext.getCollection(TableMember.COLLECTION_NAME);
		WriteResult wr = coll.remove("{_id: #}", memberId);
		logger.debug("WriteResult for remove table member: {}", wr);
	}

	@Override
	public void removeByTripId(String tripId) {
		MongoCollection coll = mongoContext.getCollection(TableMember.COLLECTION_NAME);
		WriteResult wr = coll.remove("{tripId: #}", tripId);
		logger.debug("WriteResult for remove table member by tripId: {}", wr);
	}

	@Override
	public TableMember findById(String memberId) {
		MongoCollection coll = mongoContext.getCollection(TableMember.COLLECTION_NAME);
		return coll.findOne("{ _id : #}", memberId).as(TableMember.class);
	}

	@Override
	public List<String> findTripIdsByUserId(String userId) {
		MongoCollection coll = mongoContext.getCollection(TableMember.COLLECTION_NAME);
		String query = "{_class: 'com.heaptrip.domain.entity.trip.TableUser', userId: #}";
		String projection = "{_class: 1, tripId: 1}";
		String hint = "{_class: 1, userId: 1}";
		if (logger.isDebugEnabled()) {
			String msg = String.format(
					"find table members\n->query: %s\n->parameters: %s\n->projection: %s\n->hint: %s", query, userId,
					projection, hint);
			logger.debug(msg);
		}
		Iterator<TableMember> iter = coll.find(query, userId).projection(projection).hint(hint).as(TableMember.class)
				.iterator();
		List<String> result = new ArrayList<>();
		if (iter != null) {
			while (iter.hasNext()) {
				TableMember member = iter.next();
				result.add(member.getTripId());
			}
		}
		return result;
	}

	@Override
	public void addAllowed(String ownerId, String userId) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		String query = "{_class: 'com.heaptrip.domain.entity.trip.Trip', 'owner._id': #}";
		String updateQuery = "{$addToSet :{allowed: #}}";
		if (logger.isDebugEnabled()) {
			String msg = String.format(
					"add allowed\n->query: %s\n->parameters: %s\n->updateQuery: %s\n->updateParameters: %s", query,
					ownerId, updateQuery, userId);
			logger.debug(msg);
		}
		// XXX check index
		WriteResult wr = coll.update(query, ownerId).multi().with(updateQuery, userId);
		logger.debug("WriteResult for add allowed: {}", wr);
	}

	@Override
	public void removeAllowed(String ownerId, String userId) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		String query = "{_class: 'com.heaptrip.domain.entity.trip.Trip', 'owner._id': #}";
		String updateQuery = "{$pull :{allowed: #}}";
		if (logger.isDebugEnabled()) {
			String msg = String.format(
					"remove allowed\n->query: %s\n->parameters: %s\n->updateQuery: %s\n->updateParameters: %s", query,
					ownerId, updateQuery, userId);
			logger.debug(msg);
		}
		// XXX check index
		WriteResult wr = coll.update(query, ownerId).multi().with(updateQuery, userId);
		logger.debug("WriteResult for remove allowed: {}", wr);
	}
}

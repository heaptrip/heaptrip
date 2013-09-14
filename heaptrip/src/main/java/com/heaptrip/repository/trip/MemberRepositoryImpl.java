package com.heaptrip.repository.trip;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.trip.TableMember;
import com.heaptrip.domain.entity.trip.TableUser;
import com.heaptrip.domain.entity.trip.TableUserStatusEnum;
import com.heaptrip.domain.repository.trip.MemberRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.util.collection.IteratorConverter;
import com.mongodb.WriteResult;

@Service
public class MemberRepositoryImpl extends CrudRepositoryImpl<TableMember> implements MemberRepository {

	private static final Logger logger = LoggerFactory.getLogger(MemberRepositoryImpl.class);

	@Override
	protected String getCollectionName() {
		return TableMember.COLLECTION_NAME;
	}

	@Override
	protected Class<TableMember> getCollectionClass() {
		return TableMember.class;
	}

	@Override
	public void setStatus(String memberId, TableUserStatusEnum status) {
		MongoCollection coll = getCollection();
		WriteResult wr = coll.update("{_id: #}", memberId).with("{$set: {status: #}}", status);
		logger.debug("WriteResult for set status: {}", wr);
	}

	@Override
	public void setOrganizer(String tableUserId, Boolean isOrganizer) {
		MongoCollection coll = getCollection();
		WriteResult wr = coll.update("{_id: #}", tableUserId).with("{$set: {isOrganizer: #}}", isOrganizer);
		logger.debug("WriteResult for set organizer: {}", wr);
	}

	@Override
	public List<TableMember> findByTripIdAndTableId(String tripId, String tableId) {
		MongoCollection coll = getCollection();
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
		MongoCollection coll = getCollection();
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
	public List<TableUser> findTableUsersByUserId(String tripId, String userId) {
		MongoCollection coll = getCollection();
		String query = "{userId: #, tripId: #}";
		String hint = "{userId: 1, tripId: 1}";
		if (logger.isDebugEnabled()) {
			String msg = String.format("find table users\n->query: %s\n->parameters: [%s,%s]\n->hint: %s", query,
					userId, tripId, hint);
			logger.debug(msg);
		}
		Iterator<TableUser> iter = coll.find(query, userId, tripId).hint(hint).as(TableUser.class).iterator();
		return IteratorConverter.copyIterator(iter);
	}

	@Override
	public boolean existsByTripIdAndUserId(String tripId, String userId) {
		MongoCollection coll = getCollection();
		long count = coll.count("{userId: #, tripId: #, status: #}", userId, tripId, TableUserStatusEnum.OK);
		return (count > 0) ? true : false;
	}

	@Override
	public long getCountByTripId(String tripId) {
		MongoCollection coll = getCollection();
		return coll.count("{tripId: #}", tripId);
	}

	@Override
	public void removeByTripId(String tripId) {
		MongoCollection coll = getCollection();
		WriteResult wr = coll.remove("{tripId: #}", tripId);
		logger.debug("WriteResult for remove table member by tripId: {}", wr);
	}

	@Override
	public List<String> findTripIdsByUserId(String userId) {
		MongoCollection coll = getCollection();
		String query = "{userId: #}";
		String projection = "{_class: 1, tripId: 1}";
		String hint = "{userId: 1, tripId: 1}";
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
		MongoCollection coll = mongoContext.getCollection(CollectionEnum.CONTENTS.getName());
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
		MongoCollection coll = mongoContext.getCollection(CollectionEnum.CONTENTS.getName());
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

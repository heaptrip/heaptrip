package com.heaptrip.repository.content.trip;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.CollectionEnum;
import com.heaptrip.domain.entity.content.trip.TableUserStatusEnum;
import com.heaptrip.domain.entity.content.trip.TripMember;
import com.heaptrip.domain.entity.content.trip.TripUser;
import com.heaptrip.domain.repository.content.trip.TripMemberRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.heaptrip.util.collection.IteratorConverter;
import com.mongodb.WriteResult;

@Service
public class TripMemberRepositoryImpl extends CrudRepositoryImpl<TripMember> implements TripMemberRepository {

	private static final Logger logger = LoggerFactory.getLogger(TripMemberRepositoryImpl.class);

	@Override
	protected String getCollectionName() {
		return CollectionEnum.MEMBERS.getName();
	}

	@Override
	protected Class<TripMember> getCollectionClass() {
		return TripMember.class;
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
	public List<TripMember> findByTripIdAndTableId(String tripId, String tableId) {
		MongoCollection coll = getCollection();
		String query = "{contentId: #, tableId: #}";
		String hint = "{contentId: 1, tableId: 1}";
		if (logger.isDebugEnabled()) {
			String msg = String.format("find trip members\n->query: %s\n->parameters: [%s,%s]\n->hint: %s", query,
					tripId, tableId, hint);
			logger.debug(msg);
		}
		Iterator<TripMember> iter = coll.find(query, tripId, tableId).hint(hint).as(getCollectionClass()).iterator();
		return IteratorConverter.copyIterator(iter);
	}

	@Override
	public List<TripMember> findByTripIdAndTableId(String tripId, String tableId, int limit) {
		MongoCollection coll = getCollection();
		String query = "{contentId: #, tableId: #}";
		String hint = "{contentId: 1, tableId: 1}";
		if (logger.isDebugEnabled()) {
			String msg = String.format(
					"find trip members\n->query: %s\n->parameters: [%s,%s]\n->limit: %d\n->hint: %s", query, tripId,
					tableId, limit, hint);
			logger.debug(msg);
		}
		Iterator<TripMember> iter = coll.find(query, tripId, tableId).limit(limit).hint(hint).as(getCollectionClass())
				.iterator();
		return IteratorConverter.copyIterator(iter);
	}

	@Override
	public List<TripUser> findByUserId(String tripId, String userId) {
		MongoCollection coll = getCollection();
		String query = "{userId: #, contentId: #}";
		String hint = "{userId: 1, contentId: 1}";
		if (logger.isDebugEnabled()) {
			String msg = String.format("find trip users\n->query: %s\n->parameters: [%s,%s]\n->hint: %s", query,
					userId, tripId, hint);
			logger.debug(msg);
		}
		Iterator<TripUser> iter = coll.find(query, userId, tripId).hint(hint).as(TripUser.class).iterator();
		return IteratorConverter.copyIterator(iter);
	}

	@Override
	public boolean existsByTripIdAndUserId(String tripId, String userId) {
		MongoCollection coll = getCollection();
		long count = coll.count("{userId: #, contentId: #, status: #}", userId, tripId, TableUserStatusEnum.OK);
		return (count > 0) ? true : false;
	}

	@Override
	public long getCountByTripId(String tripId) {
		MongoCollection coll = getCollection();
		return coll.count("{contentId: #}", tripId);
	}

	@Override
	public void removeByTripId(String tripId) {
		MongoCollection coll = getCollection();
		WriteResult wr = coll.remove("{contentId: #}", tripId);
		logger.debug("WriteResult for remove trip member by tripId: {}", wr);
	}

	@Override
	public List<String> findTripIdsByUserId(String userId) {
		MongoCollection coll = getCollection();
		String query = "{userId: #}";
		String projection = "{_class: 1, contentId: 1}";
		String hint = "{userId: 1, contentId: 1}";
		if (logger.isDebugEnabled()) {
			String msg = String.format(
					"find trip members\n->query: %s\n->parameters: %s\n->projection: %s\n->hint: %s", query, userId,
					projection, hint);
			logger.debug(msg);
		}
		Iterator<TripMember> iter = coll.find(query, userId).projection(projection).hint(hint).as(getCollectionClass())
				.iterator();
		List<String> result = new ArrayList<>();
		if (iter != null) {
			while (iter.hasNext()) {
				TripMember member = iter.next();
				result.add(member.getContentId());
			}
		}
		return result;
	}
}
package com.heaptrip.repository.trip;

import java.util.Calendar;

import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.Content;
import com.heaptrip.domain.entity.trip.Trip;
import com.heaptrip.domain.repository.MongoContext;
import com.heaptrip.domain.repository.trip.TripRepository;
import com.mongodb.WriteResult;

@Service(TripRepository.SERVICE_NAME)
public class TripRepositoryImpl implements TripRepository {

	private static final Logger logger = LoggerFactory.getLogger(TripRepositoryImpl.class);

	@Autowired
	private MongoContext mongoContext;

	@Override
	public void save(Trip trip) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		WriteResult wr = coll.save(trip);
		logger.debug("WriteResult for save trip: {}", wr);
	}

	@Override
	public void removeTrip(String tripId) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		WriteResult wr = coll.remove("{_id: #}", tripId);
		logger.debug("WriteResult for remove trip: {}", wr);
	}

	@Override
	public void setTripDeleted(String tripId, String ownerId) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		WriteResult wr = coll.update("{_id: #, owner._id : #}", tripId, ownerId).with("{$set: {deleted: #}}",
				Calendar.getInstance().getTime());
		logger.debug("WriteResult for set trip deleted: {}", wr);
	}

	@Override
	public Trip findById(String tripId) {
		MongoCollection coll = mongoContext.getCollection(Content.COLLECTION_NAME);
		return coll.findOne("{ _id : #}", tripId).as(Trip.class);
	}
}

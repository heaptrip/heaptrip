package com.heaptrip.repository.account.user;

import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.repository.account.user.UserRelationsRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.mongodb.WriteResult;

@Repository
public class UserRelationsRepositoryImpl extends CrudRepositoryImpl<User> implements UserRelationsRepository {

	private static final Logger logger = LoggerFactory.getLogger(UserRelationsRepositoryImpl.class);
	
	@Override
	protected String getCollectionName() {
		return Account.COLLECTION_NAME;
	}

	@Override
	protected Class<User> getCollectionClass() {
		return User.class;
	}

	@Override
	public void addFriend(String userId, String friendId) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$addToSet :{'friend' : #}}";
		WriteResult wr = coll.update(query, friendId).with(updateQuery, userId);
		logger.debug("WriteResult for add friend: {}", wr);
	}
	
	@Override
	public void deleteFriend(String userId, String friendId) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$pull :{'friend' : #}}";
		WriteResult wr = coll.update(query, friendId).with(updateQuery, userId);
		logger.debug("WriteResult for delete friend: {}", wr);
	}

	@Override
	public void addPublisher(String userId, String publisherId) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$addToSet :{'publisher' : #}}";
		WriteResult wr = coll.update(query, publisherId).with(updateQuery, userId);
		logger.debug("WriteResult for add publisher: {}", wr);		
	}

	@Override
	public void deletePublisher(String userId, String publisherId) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$pull :{'publisher' : #}}";
		WriteResult wr = coll.update(query, publisherId).with(updateQuery, userId);
		logger.debug("WriteResult for delete publisher: {}", wr);
	}

	@Override
	public void addOwner(String userId, String communityId) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$addToSet :{'owner' : #}}";
		WriteResult wr = coll.update(query, userId).with(updateQuery, communityId);
		logger.debug("WriteResult for add owner: {}", wr);		
	}

	@Override
	public void deleteOwner(String userId, String communityId) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$pull :{'owner' : #}}";
		WriteResult wr = coll.update(query, userId).with(updateQuery, communityId);
		logger.debug("WriteResult for delete owner: {}", wr);
	}

	@Override
	public void addEmployee(String userId, String communityId) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$addToSet :{'employee' : #}}";
		WriteResult wr = coll.update(query, userId).with(updateQuery, communityId);
		logger.debug("WriteResult for add employee: {}", wr);		
	}

	@Override
	public void deleteEmployee(String userId, String communityId) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$pull :{'employee' : #}}";
		WriteResult wr = coll.update(query, userId).with(updateQuery, communityId);
		logger.debug("WriteResult for delete employee: {}", wr);
	}

	@Override
	public void addMember(String userId, String clubId) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$addToSet :{'member' : #}}";
		WriteResult wr = coll.update(query, userId).with(updateQuery, clubId);
		logger.debug("WriteResult for add member: {}", wr);		
	}

	@Override
	public void deleteMember(String userId, String clubId) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$pull :{'member' : #}}";
		WriteResult wr = coll.update(query, userId).with(updateQuery, clubId);
		logger.debug("WriteResult for delete member: {}", wr);
	}
}

package com.heaptrip.repository.user;

import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.user.User;
import com.heaptrip.domain.repository.user.AuthenticationRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.mongodb.WriteResult;

@Service
public class AuthenticationRepositoryImpl extends CrudRepositoryImpl<User> implements AuthenticationRepository {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationRepositoryImpl.class);
	
	@Override
	protected String getCollectionName() {
		return User.COLLECTION_NAME;
	}

	@Override
	protected Class<User> getCollectionClass() {
		return User.class;
	}

	@Override
	public User findUserBySocNetUID(String socNetName, String uid) {
		MongoCollection coll = getCollection();
		String query = "{net._id: #, net.uid: #}";
		return coll.findOne(query, socNetName, uid).as(User.class);
	}

	@Override
	public User findByEmailAndPassword(String email, String password) {
		MongoCollection coll = getCollection();
		String query = "{email: #, password: #}";
		return coll.findOne(query, email, password).as(User.class);
	}

	@Override
	public void confirmRegistration(String userId) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$set: {'status': #}}";
		WriteResult wr = coll.update(query, userId).with(updateQuery, AccountStatusEnum.ACTIVE);
		logger.debug("WriteResult for update user: {}", wr);
	}

	@Override
	public void changePassword(String userId, String newPassword) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$set: {'password': #}}";
		WriteResult wr = coll.update(query, userId).with(updateQuery, newPassword);
		logger.debug("WriteResult for update user: {}", wr);
	}

	@Override
	public void changeEmail(String userId, String newEmail) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$set: {'email': #}}";
		WriteResult wr = coll.update(query, userId).with(updateQuery, newEmail);
		logger.debug("WriteResult for update user: {}", wr);
	}

	@Override
	public User findByEmail(String email) {
		MongoCollection coll = getCollection();
		String query = "{email: #}";
		return coll.findOne(query, email).as(User.class);
	}
}

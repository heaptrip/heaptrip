package com.heaptrip.repository.account.user;

import org.apache.commons.lang.StringUtils;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.user.SocialNetwork;
import com.heaptrip.domain.entity.account.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.account.user.UserRegistration;
import com.heaptrip.domain.repository.account.user.UserRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.mongodb.WriteResult;

@Repository
public class UserRepositoryImpl extends CrudRepositoryImpl<User> implements UserRepository {

	private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

	@Override
	public User findByEmailAndPassword(String email, String password) {
		MongoCollection coll = getCollection();
		String query = "{email: #, password: #}";
		return coll.findOne(query, email, password).as(User.class);
	}

	@Override
	public User findUserBySocNetUID(String socNetName, String uid) {
		MongoCollection coll = getCollection();
		String query = "{net._id: #, net.uid: #}";
		return coll.findOne(query, socNetName, uid).as(User.class);
	}

	@Override
	public Boolean isEmptyPassword(String userId) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";

		UserRegistration user = coll.findOne(query, userId).as(UserRegistration.class);

		if (user == null) {
			throw new IllegalArgumentException(String.format("user with id=%s is not found", userId));
		} else {
			return (StringUtils.isBlank(user.getPassword()));
		}
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
	public void profileImageFrom(String userId, SocialNetworkEnum socialNetwork) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$set: {'extImageStore': #}}";
		WriteResult wr = coll.update(query, userId).with(updateQuery, socialNetwork);
		logger.debug("WriteResult for update user: {}", wr);
	}

	@Override
	public void unlinkSocialNetwork(String userId, SocialNetworkEnum socialNetwork) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$pull :{net: {'_id':#}}}";
		WriteResult wr = coll.update(query, userId).with(updateQuery, socialNetwork.toString());
		logger.debug("WriteResult for unlink SocialNetwork: {}", wr);
	}

	@Override
	public void linkSocialNetwork(String userId, SocialNetwork socialNetwork) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$addToSet: {'net': #}}";
		WriteResult wr = coll.update(query, userId).with(updateQuery, socialNetwork);
		logger.debug("WriteResult for update user: {}", wr);
	}

	@Override
	protected String getCollectionName() {
		return Account.COLLECTION_NAME;
	}

	@Override
	protected Class<User> getCollectionClass() {
		return User.class;
	}

}

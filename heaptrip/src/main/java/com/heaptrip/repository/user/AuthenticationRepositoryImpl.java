package com.heaptrip.repository.user;

import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.user.User;
import com.heaptrip.domain.entity.user.UserStatusEnum;
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
		String query = "{net._id: #, net.uid: #, status: #}";
		return coll.findOne(query, socNetName, uid, UserStatusEnum.ACTIVE).as(User.class);
	}

	@Override
	public User findByEmailAndPassword(String email, String password) {
		MongoCollection coll = getCollection();
		String query = "{email: #, password: #, status: #}";
		return coll.findOne(query, email, password, UserStatusEnum.ACTIVE).as(User.class);
	}

	@Override
	public Boolean confirmRegistration(String email) {
		MongoCollection coll = getCollection();
		String query = "{email: #, status: #}";
		
		User user = coll.findOne(query, email, UserStatusEnum.NOTCONFIRMED).as(User.class);
		
		if (user == null) {
			String msg = String.format("user not find by email: %s", email);
			logger.debug(msg);			
			// TODO dikma: заменить на бизнес исключение
			return false;
		} else {
			query = "{_id: #}";
			String updateQuery = "{$set: {'status': #}}";
			WriteResult wr = coll.update(query, user.getId()).with(updateQuery, UserStatusEnum.ACTIVE);
			logger.debug("WriteResult for update user: {}", wr);
			return true;
		}
	}

	@Override
	public void resetPassword(String email) {
		MongoCollection coll = getCollection();
		String query = "{email: #, status: #}";
		
		User user = coll.findOne(query, email, UserStatusEnum.ACTIVE).as(User.class);
		
		if (user == null) {
			String msg = String.format("user not find by email: %s", email);
			logger.debug(msg);
		} else {
			// TODO dikma: выслать письмо с урлом для смены пароля
		}
	}

	@Override
	public void sendNewPassword(String email, String value) {
		MongoCollection coll = getCollection();
		String query = "{email: #, status: #}";
		
		User user = coll.findOne(query, email, UserStatusEnum.ACTIVE).as(User.class);
		
		if (user == null) {
			String msg = String.format("user not find by email: %s", email);
			logger.debug(msg);
		} else if (user.getId().equals(value)) {
			// TODO dikma: выслать письмо с новым паролем
		} else {
			String msg = String.format("can`t change password, hash value is wrong: %s", value);
			logger.debug(msg);
		}
	}
}

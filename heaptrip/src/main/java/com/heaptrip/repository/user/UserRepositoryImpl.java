package com.heaptrip.repository.user;

import org.apache.commons.lang.StringUtils;
import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.user.UserSetting;
import com.heaptrip.domain.entity.user.SocialNetwork;
import com.heaptrip.domain.entity.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.user.User;
import com.heaptrip.domain.entity.user.UserProfile;
import com.heaptrip.domain.entity.user.UserRegistration;
import com.heaptrip.domain.repository.user.AuthenticationRepository;
import com.heaptrip.domain.repository.user.UserRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.mongodb.WriteResult;

@Service
public class UserRepositoryImpl extends CrudRepositoryImpl<User> implements UserRepository {

	private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
	
	@Autowired
	private AuthenticationRepository authenticationRepository;
	
	@Override
	protected String getCollectionName() {
		return User.COLLECTION_NAME;
	}

	@Override
	protected Class<User> getCollectionClass() {
		return User.class;
	}

	@Override
	public void saveSetting(String userId, UserSetting setting) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$set: {'setting._id': #, 'setting.adsFromClub': #, 'setting.adsFromCompany': #, 'setting.adsFromAgency': #}}";
		
		WriteResult wr = coll.update(query, userId).
							with(updateQuery, setting.getId(), setting.getAdsFromClub(), setting.getAdsFromCompany(), setting.getAdsFromAgency());
		logger.debug("WriteResult for update user: {}", wr);
	}

	@Override
	public void deleteUser(String userId) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$set: {'status': #}}";
		WriteResult wr = coll.update(query, userId).with(updateQuery, AccountStatusEnum.DELETED);
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
//		String updateQuery = "{$addToSet: {net: #}, $set: {'_id': #, 'uid': #}}}";
		String updateQuery = "{$addToSet: {'net': #}}";
		WriteResult wr = coll.update(query, userId).with(updateQuery, socialNetwork);
		logger.debug("WriteResult for update user: {}", wr);
		
	}

	@Override
	public void saveProfile(String userId, UserProfile profile) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$set: {'profile': #}}";
		WriteResult wr = coll.update(query, userId).with(updateQuery, profile);
		logger.debug("WriteResult for update user: {}", wr);
	}
}

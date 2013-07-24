package com.heaptrip.repository.user;

import org.jongo.MongoCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.user.SocialNetwork;
import com.heaptrip.domain.entity.user.UserRegistration;
import com.heaptrip.domain.entity.user.UserSetting;
import com.heaptrip.domain.repository.user.AuthenticationRepository;
import com.heaptrip.domain.repository.user.UserSettingRepository;
import com.heaptrip.repository.CrudRepositoryImpl;
import com.mongodb.WriteResult;

@Service
public class UserSettingRepositoryImpl extends CrudRepositoryImpl<UserSetting> implements UserSettingRepository {

	private static final Logger logger = LoggerFactory.getLogger(UserSettingRepositoryImpl.class);
	
	@Autowired
	private AuthenticationRepository authenticationRepository;
	
	@Override
	protected String getCollectionName() {
		return UserSetting.COLLECTION_NAME;
	}

	@Override
	protected Class<UserSetting> getCollectionClass() {
		return UserSetting.class;
	}

	@Override
	public void saveSetting(UserSetting userSetting) {
		
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$set: {'adsFromClub': #, 'adsFromCompany': #, 'adsFromAgency': #}}";
		
		WriteResult wr = coll.update(query, userSetting.getId()).
							with(updateQuery, userSetting.getAdsFromClub(), userSetting.getAdsFromCompany(), userSetting.getAdsFromAgency());
		logger.debug("WriteResult for update user: {}", wr);
	}

	@Override
	public void deleteUserProfile(String userId) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$set: {'status': #}}";
		
		WriteResult wr = coll.update(query, userId).with(updateQuery, AccountStatusEnum.DELETED);
		logger.debug("WriteResult for update user: {}", wr);
	}

	@Override
	public void profileImageFrom(String userId, String socialNetworkName) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";		
		String updateQuery = "{$set: {'extImageStore': #}}";
		
		WriteResult wr = coll.update(query, userId).with(updateQuery, socialNetworkName);
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
			return (user.getPassword().isEmpty());
		}
	}

	@Override
	public void unlinkSocialNetwork(String userId, SocialNetwork unlinkNet) {
		MongoCollection coll = getCollection();
		String query = "{_id: #}";
		String updateQuery = "{$pull :{net: #}}";

		WriteResult wr = coll.update(query, userId).with(updateQuery, unlinkNet);
		logger.debug("WriteResult for unlink SocialNetwork: {}", wr);
	}
}

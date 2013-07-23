package com.heaptrip.repository.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.heaptrip.domain.entity.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.user.UserSetting;
import com.heaptrip.domain.repository.user.UserSettingRepository;
import com.heaptrip.repository.CrudRepositoryImpl;

public class UserSettingRepositoryImpl extends CrudRepositoryImpl<UserSetting> implements UserSettingRepository {

	private static final Logger logger = LoggerFactory.getLogger(UserSettingRepositoryImpl.class);
	
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUserProfile(String userId) {
		// TODO Auto-generated method stub
	}

	@Override
	public void profileImageFrom(String userId, String socialNetworkName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlinkSocialNetwork(String userId, String socialNetworkName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean isEmptyPassword(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

}

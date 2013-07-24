package com.heaptrip.domain.repository.user;

import com.heaptrip.domain.entity.user.SocialNetwork;
import com.heaptrip.domain.entity.user.UserSetting;
import com.heaptrip.domain.repository.CrudRepository;

public interface UserSettingRepository extends CrudRepository<UserSetting> {
	
	void saveSetting(UserSetting userSetting);
	
	void deleteUserProfile(String userId);
	
	void profileImageFrom(String userId, String socialNetworkName);
	
	void unlinkSocialNetwork(String userId, SocialNetwork unlinkNet);
	
	Boolean isEmptyPassword(String userId);
}

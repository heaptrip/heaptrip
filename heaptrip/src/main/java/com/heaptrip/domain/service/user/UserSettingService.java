package com.heaptrip.domain.service.user;

import com.heaptrip.domain.entity.user.UserSetting;

public interface UserSettingService {

	void saveSetting(UserSetting userSetting);
	
	void deleteUserProfile(String userId);
	
	void profileImageFrom(String userId, String socialNetworkName);
	
	void unlinkSocialNetwork(String userId, String socialNetworkName);
}

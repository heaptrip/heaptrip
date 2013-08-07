package com.heaptrip.domain.service.user;

import com.heaptrip.domain.entity.user.UserSetting;
import com.heaptrip.domain.entity.user.SocialNetwork;
import com.heaptrip.domain.entity.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.user.UserProfile;

public interface UserService {

	void saveSetting(String userId, UserSetting setting);
	
	void saveProfile(String userId, UserProfile profile);
	
	void deleteUser(String userId);
	
	void profileImageFrom(String userId, SocialNetworkEnum socialNetwork);
	
	void unlinkSocialNetwork(String userId, SocialNetworkEnum socialNetwork);
	
	void linkSocialNetwork(String userId, SocialNetwork socialNetwork);
}

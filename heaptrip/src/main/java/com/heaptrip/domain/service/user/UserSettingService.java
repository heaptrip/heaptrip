package com.heaptrip.domain.service.user;

import com.heaptrip.domain.entity.user.Setting;
import com.heaptrip.domain.entity.user.SocialNetwork;
import com.heaptrip.domain.entity.user.SocialNetworkEnum;

public interface UserSettingService {

	void saveSetting(String userId, Setting setting);
	
	void deleteUser(String userId);
	
	void profileImageFrom(String userId, SocialNetworkEnum socialNetwork);
	
	void unlinkSocialNetwork(String userId, SocialNetworkEnum socialNetwork);
	
	void linkSocialNetwork(String userId, SocialNetwork socialNetwork);
}

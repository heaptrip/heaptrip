package com.heaptrip.domain.repository.user;

import com.heaptrip.domain.entity.user.UserSetting;
import com.heaptrip.domain.entity.user.SocialNetwork;
import com.heaptrip.domain.entity.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.user.User;
import com.heaptrip.domain.entity.user.UserProfile;
import com.heaptrip.domain.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User> {
	
	void saveSetting(String userId, UserSetting setting);
	
	void saveProfile(String userId, UserProfile profile);
	
	void deleteUser(String userId);
	
	void profileImageFrom(String userId, SocialNetworkEnum socialNetwork);
	
	void unlinkSocialNetwork(String userId, SocialNetworkEnum socialNetwork);
	
	void linkSocialNetwork(String userId, SocialNetwork socialNetwork);
	
	Boolean isEmptyPassword(String userId);
}

package com.heaptrip.domain.repository.user;

import com.heaptrip.domain.entity.user.UserProfile;
import com.heaptrip.domain.repository.CrudRepository;

public interface UserProfileRepository extends CrudRepository<UserProfile> {

	void saveUserProfile(UserProfile userProfile);
	
	UserProfile getUserProfile(String userId);
}

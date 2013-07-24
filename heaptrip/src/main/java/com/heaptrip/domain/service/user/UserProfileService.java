package com.heaptrip.domain.service.user;

import com.heaptrip.domain.entity.user.UserProfile;

public interface UserProfileService {

	void saveUserProfile(UserProfile userProfile);
	
	UserProfile getUserProfile(String userId);
}

package com.heaptrip.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.user.UserProfile;
import com.heaptrip.domain.repository.user.UserProfileRepository;
import com.heaptrip.domain.service.user.UserProfileService;

@Service
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Override
	public void saveUserProfile(UserProfile userProfile) {
		Assert.notNull(userProfile, "userProfile must not be null");
		userProfileRepository.saveUserProfile(userProfile);
	}

	@Override
	public UserProfile getUserProfile(String userId) {
		Assert.notNull(userId, "userId must not be null");
		
		UserProfile user = userProfileRepository.findOne(userId);
		
		if (user == null) {
			throw new IllegalArgumentException(String.format("user with id=%s is not found", userId));
		} else {
			return user;
		}
	}
}

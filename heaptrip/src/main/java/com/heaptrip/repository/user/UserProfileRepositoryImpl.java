package com.heaptrip.repository.user;

import org.springframework.stereotype.Service;

import com.heaptrip.domain.entity.user.UserProfile;
import com.heaptrip.domain.repository.user.UserProfileRepository;
import com.heaptrip.repository.CrudRepositoryImpl;

@Service
public class UserProfileRepositoryImpl extends CrudRepositoryImpl<UserProfile> implements UserProfileRepository {

	@Override
	protected String getCollectionName() {
		return UserProfile.COLLECTION_NAME;
	}

	@Override
	protected Class<UserProfile> getCollectionClass() {
		return UserProfile.class;
	}

	@Override
	public void saveUserProfile(UserProfile userProfile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserProfile getUserProfile(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

}

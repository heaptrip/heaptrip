package com.heaptrip.web.modelservice;

import com.heaptrip.web.model.profile.AccountModelInfo;

public interface ProfileModelService {

	AccountModelInfo getProfileInformation(String uid); 
	
}

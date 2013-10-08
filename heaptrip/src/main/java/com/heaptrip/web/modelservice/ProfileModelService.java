package com.heaptrip.web.modelservice;

import com.heaptrip.web.model.profile.AccountModel;

public interface ProfileModelService {

	AccountModel getProfileInformation(String uid); 
	
}

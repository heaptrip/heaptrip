package com.heaptrip.domain.service.adm;

import com.heaptrip.domain.entity.adm.User;

public interface UserService {

	User getUserByAuthenticationInfo(String username, String password);
	
	User getCurrentUser();

}

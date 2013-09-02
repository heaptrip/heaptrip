package com.heaptrip.domain.service.account.community;

import com.heaptrip.domain.entity.account.community.Community;
import com.heaptrip.domain.service.account.AccountService;

public interface CommunityService extends AccountService {
	
	/**
	 * Community registration
	 * 
	 * @param account
	 * @return community
	 */
	Community registration(Community community);
}

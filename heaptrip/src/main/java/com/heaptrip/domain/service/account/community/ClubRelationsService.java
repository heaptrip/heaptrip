package com.heaptrip.domain.service.account.community;

public interface ClubRelationsService extends CommunityRelationsService {

	/**
	 * send member request
	 * 
	 * @param userId
	 * @param communityId
	 */
	void sendMemberRequest(String userId, String communityId);
	
	/**
	 * delete member
	 *
	 * @param userId
	 * @param communityId
	 */
	void deleteMember(String userId, String communityId);
}

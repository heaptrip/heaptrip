package com.heaptrip.domain.service.account.user;

public interface UserRelationsService {

	/**
	 * send friendship request
	 * 
	 * @param userId
	 * @param frendId
	 */
	void sendFriendshipRequest(String userId, String frendId);
	
	/**
	 * delete frend
	 * 
	 * @param userId
	 * @param frendId
	 */
	void deleteFrend(String userId, String frendId);
	
	/**
	 * add signed
	 * 
	 * @param userId
	 * @param signedId
	 */
	void addSigned(String userId, String signedId);
	
	/**
	 * delete signed
	 * 
	 * @param userId
	 * @param signedId
	 */
	void deleteSigned(String userId, String signedId);
}

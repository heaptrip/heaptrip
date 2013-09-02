package com.heaptrip.domain.service.account.user;

public interface UserRelationsService {

	/**
	 * send friendship request
	 * 
	 * @param userId
	 * @param frendId
	 */
	void sendFriendshipRequest(String userId, String friendId);
	
	/**
	 * delete friend
	 * 
	 * @param userId
	 * @param friendId
	 */
	void deleteFriend(String userId, String friendId);
	
	/**
	 * add publisher
	 * 
	 * @param userId
	 * @param publisherId
	 */
	void addPublisher(String userId, String publisherId);
	
	/**
	 * delete publisher
	 * 
	 * @param userId
	 * @param publisherId
	 */
	void deletePublisher(String userId, String publisherId);
}

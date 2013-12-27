package com.heaptrip.domain.service.account.relation;

public interface RelationService {

	/**
	 * send friendship request
	 * 
	 * @param userId
	 * @param friendId
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
	
	/**
	 * send owner request
	 * 
	 * @param userId
	 * @param communityId
	 */
	void sendOwnerRequest(String userId, String communityId);
	
	/**
	 * delete owner
	 * 
	 * @param userId
	 * @param communityId
	 */
	void deleteOwner(String userId, String communityId);
	
	/**
	 * send employee request
	 * 
	 * @param userId
	 * @param communityId
	 */
	void sendEmployeeRequest(String userId, String communityId);
	
	/**
	 * delete employee
	 * 
	 * @param userId
	 * @param communityId
	 */
	void deleteEmployee(String userId, String communityId);
	
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

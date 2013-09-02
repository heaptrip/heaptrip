package com.heaptrip.domain.service.account.community;

public interface CommunityRelationsService {

	/**
	 * send owner request
	 * 
	 * @param communityId
	 * @param userId
	 */
	void sendOwnerRequest(String userId, String communityId);
	
	/**
	 * delete owner
	 * 
	 * @param communityId
	 * @param userId
	 */
	void deleteOwner(String userId, String communityId);
	
	/**
	 * send employee request
	 * 
	 * @param communityId
	 * @param userId
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
	 * add publisher
	 * 
	 * @param userId
	 * @param communityId
	 */
	void addPublisher(String userId, String communityId);
	
	/**
	 * delete publisher
	 * 
	 * @param userId
	 * @param communityId
	 */
	void deletePublisher(String userId, String communityId);
}

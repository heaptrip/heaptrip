package com.heaptrip.domain.repository.account.user;

import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.repository.CrudRepository;

public interface UserRelationsRepository extends CrudRepository<User> {
	
	void addOwner(String userId, String communityId);
	
	void deleteOwner(String userId, String communityId);
	
	void addEmployee(String userId, String communityId);
	
	void deleteEmployee(String userId, String communityId);
	
	void addMember(String userId, String clubId);
	
	void deleteMember(String userId, String clubId);
	
	void addFriend(String userId, String friendId);
	
	void deleteFriend(String userId, String friendId);
	
	void addPublisher(String userId, String publisherId);

	void deletePublisher(String userId, String publisherId);
}

package com.heaptrip.domain.repository.user;

import com.heaptrip.domain.entity.user.User;
import com.heaptrip.domain.repository.CrudRepository;

public interface AuthenticationRepository extends CrudRepository<User> {
	
	User findByEmailAndPassword(String email, String password);
	
	User findByEmail(String email);
	
	User findUserBySocNetUID(String socNetName, String uid);
	
	void confirmRegistration(String userId);
	
	void changePassword(String userId, String newPassword);
	
	void changeEmail(String userId, String newEmail);
}

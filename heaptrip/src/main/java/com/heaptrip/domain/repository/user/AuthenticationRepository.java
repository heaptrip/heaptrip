package com.heaptrip.domain.repository.user;

import com.heaptrip.domain.entity.user.User;
import com.heaptrip.domain.repository.CrudRepository;

public interface AuthenticationRepository extends CrudRepository<User> {
	
	User findByEmailAndPassword(String email, String password);
	
	User findByEmail(String email);
	
	User findUserBySocNetUID(String socNetName, String uid);
	
	Boolean confirmRegistration(String email);
	
	void resetPassword(String email);
	
	void sendNewPassword(String email, String value);
	
	Boolean changePassword(String userId, String oldPassword, String newPassword);
	
	Boolean changeEmail(String userId, String oldEmail, String newEmail);
}

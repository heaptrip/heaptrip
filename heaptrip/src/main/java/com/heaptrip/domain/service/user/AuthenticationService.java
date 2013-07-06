package com.heaptrip.domain.service.user;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import com.heaptrip.domain.entity.user.User;
import com.heaptrip.domain.entity.user.UserRegistration;

/**
 * 
 * Service registration and authentication
 * 
 */
public interface AuthenticationService {

	/**
	 * Get user by email
	 * 
	 * @param email
	 * @param password
	 * @return user
	 */
	User getUserByEmail(String email, String password);
	
	/**
	 * Get user by social network user id
	 * 
	 * @param socNetName
	 * @param uid
	 * @return user
	 */
	User getUserBySocNetUID(String socNetName, String uid);
	
	/**
	 * User registration
	 * 
	 * @param registrationInfo
	 * @return user
	 */
	User registration(UserRegistration userRegistration, InputStream isImage) throws IOException, NoSuchAlgorithmException;
	
	/**
	 * Hard remove user. It is recommended to use the after tests to clear data
	 * 
	 * @param userId
	 */
	void hardRemoveUser(String userId);
	
	/**
	 * Confirmation of registration
	 * 
	 * @param email
	 * @return boolean - true if the user was found and confirmed
	 */
	Boolean confirmRegistration(String email);
	
	/**
	 * Reset password
	 * 
	 * @param email
	 */
	void resetPassword(String email);
	
	/**
	 * Send new password
	 * 
	 * @param email
	 * @param value = sended hash value
	 */
	void sendNewPassword(String email, String value);
}

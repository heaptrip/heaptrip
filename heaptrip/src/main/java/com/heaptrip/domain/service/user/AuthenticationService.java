package com.heaptrip.domain.service.user;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.mail.MessagingException;

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
	User getUserBySocNetUID(String socNetName, String uid, InputStream isImage) throws IOException, NoSuchAlgorithmException;
	
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
	 */
	void confirmRegistration(String email);
	
	/**
	 * Reset password
	 * 
	 * @param email
	 */
	void resetPassword(String email, Locale locale) throws MessagingException;
	
	/**
	 * Send new password
	 * 
	 * @param email
	 * @param value = sended hash value
	 */
	void sendNewPassword(String email, String value, Locale locale) throws MessagingException;
	
	/**
	 * Change password
	 * 
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 */
	void changePassword(String userId, String oldPassword, String newPassword);
	
	/**
	 * Change email
	 * 
	 * @param userId
	 * @param oldEmail
	 * @param newEmail
	 */
	void changeEmail(String userId, String oldEmail, String newEmail);
}

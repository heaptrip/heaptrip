package com.heaptrip.domain.service.account.user;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.mail.MessagingException;

import com.heaptrip.domain.entity.account.user.User;

public interface AuthenticationService {
	
	/**
	 * Get user by email
	 *
	 * @param email
	 * @param password
	 * @return user
	 */
	User getUserByEmailAndPassword(String email, String password);
	
	/**
	 * Get user by social network user id
	 * 
	 * @param socNetName
	 * @param uid
	 * @return user
	 */
	User getUserBySocNetUID(String socNetName, String uid, InputStream isImage) throws IOException, NoSuchAlgorithmException;
	
	/**
	 * Reset password
	 * 
	 * @param email
	 */
	void resetPassword(String email, Locale locale);
	
	/**
	 * Send new password
	 * 
	 * @param accountId
	 * @param value = sended hash value
	 */
	void sendNewPassword(String accountId, String value, Locale locale) throws MessagingException;
}

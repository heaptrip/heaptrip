package com.heaptrip.domain.service.account.user;

import com.heaptrip.domain.entity.account.user.SocialNetwork;
import com.heaptrip.domain.entity.account.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.account.user.UserRegistration;
import com.heaptrip.domain.service.account.AccountService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public interface UserService extends AccountService {


	/**
	 * User registration
	 * 
	 * @param userRegistration
	 * @return user
	 */
	User registration(UserRegistration userRegistration, InputStream isImage, Locale locale) throws IOException,
			NoSuchAlgorithmException, MessagingException;

	/**
	 * Change password
	 * 
	 * @param userId
	 * @param currentPassword
	 * @param newPassword
	 */
	void changePassword(String userId, String currentPassword, String newPassword);

	/**
	 * Get image from social network
	 * 
	 * @param userId
	 * @param socialNetwork
	 */
	void profileImageFrom(String userId, SocialNetworkEnum socialNetwork);

	/**
	 * Unlink social network
	 * 
	 * @param userId
	 * @param socialNetwork
	 */
	void unlinkSocialNetwork(String userId, SocialNetworkEnum socialNetwork);

	/**
	 * Link social network
	 * 
	 * @param userId
	 * @param socialNetwork
	 */
	void linkSocialNetwork(String userId, SocialNetwork socialNetwork);
}

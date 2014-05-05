package com.heaptrip.domain.service.account.user;

import com.heaptrip.domain.entity.account.user.SocialNetwork;
import com.heaptrip.domain.entity.account.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.service.account.AccountService;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public interface UserService extends AccountService {


	/**
	 * User registration
	 * 
	 * @param user
     * @param password
     * @param isImage
     * @param locale
	 * @return user
	 */
	User registration(User user, String password, InputStream isImage, Locale locale) throws IOException,
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

    boolean checkPassword(String password, User user);

    byte[] generatePassword(String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException;

    byte[] generateSalt() throws NoSuchAlgorithmException;

    /**
     * From a base 64 representation, returns the corresponding byte[]
     * @param data String The base64 representation
     * @return byte[]
     * @throws IOException
     */
    byte[] base64ToByte(String data) throws IOException;

    /**
     * From a byte[] returns a base 64 representation
     * @param data byte[]
     * @return String
     */
    String byteToBase64(byte[] data);
}

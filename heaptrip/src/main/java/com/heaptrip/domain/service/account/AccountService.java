package com.heaptrip.domain.service.account;

import com.heaptrip.domain.entity.account.Profile;
import com.heaptrip.domain.entity.account.Setting;

public interface AccountService {

	/**
	 * Hard remove account. It is recommended to use the after tests to clear data
	 * 
	 * @param userId
	 */
	void hardRemove(String accountId);
	
	/**
	 * Confirmation of registration
	 * 
	 * @param accountId
	 * @param value
	 */
	void confirmRegistration(String accountId, String value);
	
	/**
	 * Change email
	 * 
	 * @param accountId
	 * @param currentEmail
	 * @param newEmail
	 */
	void changeEmail(String accountId, String currentEmail, String newEmail);
	
	/**
	 * Save setting
	 * 
	 * @param accountId
	 * @param setting
	 */
	void saveSetting(String accountId, Setting setting);
	
	/**
	 * Save profile
	 * 
	 * @param accountId
	 * @param profile
	 */
	void saveProfile(String accountId, Profile profile);
	
	/**
	 * Soft delete account
	 * 
	 * @param accountId
	 */
	void delete(String accountId);
}

package com.heaptrip.domain.service.account;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.Profile;
import com.heaptrip.domain.entity.account.Setting;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.rating.AccountRating;
import org.springframework.stereotype.Service;


public interface AccountService {

    /**
     * Get user by id
     *
     * @param accountId
     */
    public Account getAccountById(String accountId);

    /**
	 * Hard remove account. It is recommended to use the after tests to clear
	 * data
	 * 
	 * @param accountId
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

	/**
	 * Get account rating by account id
	 * 
	 * @param accountId
	 *            account id
	 * @return account rating
	 */
	public AccountRating getAccountRating(String accountId);

	/**
	 * Update account rating value by account id
	 * 
	 * @param accountId
	 *            account id
	 * @param ratingValue
	 *            new value for account rating
	 * 
	 */
	public void updateAccountRatingValue(String accountId, double ratingValue);
}

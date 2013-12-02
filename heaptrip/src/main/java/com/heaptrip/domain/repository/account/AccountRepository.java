package com.heaptrip.domain.repository.account;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.Profile;
import com.heaptrip.domain.entity.account.Setting;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account> {

    Account findAccountById(String accId);

	void changeStatus(String accountId, AccountStatusEnum accountStatus);
	
	void changeEmail(String accountId, String newEmail);
	
	void saveSetting(String accountId, Setting setting);
	
	void saveProfile(String accountId, Profile profile);
	
	Account findByEmail(String email);
	
	public AccountRating getRating(String accountId);

	public void updateRating(String accountId, double ratingValue);
}

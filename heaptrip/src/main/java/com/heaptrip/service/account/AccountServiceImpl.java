package com.heaptrip.service.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.Profile;
import com.heaptrip.domain.entity.account.Setting;
import com.heaptrip.domain.repository.account.AccountRepository;
import com.heaptrip.domain.service.account.AccountService;

public abstract class AccountServiceImpl implements AccountService {

	protected static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	public static String EMAIL_REGEX = "^[-!#$%&'*+/0-9=?A-Z^_a-z{|}~](\\.?[-!#$%&'*+/0-9=?A-Z^_a-z{|}~])*@[a-zA-Z](-?[a-zA-Z0-9])*(\\.[a-zA-Z](-?[a-zA-Z0-9])*)+$";
	
	@Autowired
	protected AccountRepository accountRepository;
	
	@Override
	public void hardRemove(String accountId) {
		Assert.notNull(accountId, "accountId must not be null");
		accountRepository.remove(accountId);		
	}

	@Override
	public void confirmRegistration(String accountId, String value) {
		Assert.notNull(accountId, "accountId must not be null");
		Assert.notNull(value, "value must not be null");
		
		Account account = accountRepository.findOne(accountId);
		
		if (account == null) {
			String msg = String.format("account not find by id %s", accountId);
			logger.debug(msg);			
			// TODO dikma: заменить на бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!account.getStatus().equals(AccountStatusEnum.NOTCONFIRMED)) {
			String msg = String.format("account status must be: %s", AccountStatusEnum.NOTCONFIRMED);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (account.getId().hashCode() == Integer.valueOf(value).intValue()) {
			accountRepository.changeStatus(account.getId(), AccountStatusEnum.ACTIVE);
		} else {
			String msg = String.format("value not correct, account not been confirmed id: %s", accountId);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		}
	}

	@Override
	public void changeEmail(String accountId, String currentEmail, String newEmail) {
		Assert.notNull(accountId, "accountId must not be null");
		Assert.notNull(newEmail, "email must not be null");
		Assert.isTrue(newEmail.matches(EMAIL_REGEX), "email is not correct");
		
		Account account = accountRepository.findOne(accountId);
		
		if (account == null) {
			String msg = String.format("account not find by id %s", accountId);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!account.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("account status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!account.getEmail().equals(currentEmail)) {
			String msg = String.format("current email not equals");
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else {
			accountRepository.changeEmail(accountId, newEmail);
		}		
	}

	@Override
	public void saveSetting(String accountId, Setting setting) {
		Assert.notNull(accountId, "accountId must not be null");
		Assert.notNull(setting, "setting must not be null");
		
		Account account = accountRepository.findOne(accountId);
		
		if (account == null) {
			String msg = String.format("account not find by id: %s", accountId);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!account.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("account status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else {
			accountRepository.saveSetting(accountId, setting);
		}		
	}

	@Override
	public void saveProfile(String accountId, Profile profile) {
		Assert.notNull(accountId, "accountId must not be null");
		Assert.notNull(profile, "profile must not be null");
		
		Account account = accountRepository.findOne(accountId);
		
		if (account == null) {
			String msg = String.format("account not find by id: %s", accountId);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!account.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("account status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else {
			accountRepository.saveProfile(accountId, profile);
		}		
	}
}

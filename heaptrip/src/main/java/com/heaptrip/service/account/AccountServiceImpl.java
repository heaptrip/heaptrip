package com.heaptrip.service.account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.Profile;
import com.heaptrip.domain.entity.account.Setting;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.AccountRepository;
import com.heaptrip.domain.service.account.AccountSearchService;
import com.heaptrip.domain.service.account.AccountService;
import com.heaptrip.domain.service.system.ErrorService;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

	protected static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	public static String EMAIL_REGEX = "^[-!#$%&'*+/0-9=?A-Z^_a-z{|}~](\\.?[-!#$%&'*+/0-9=?A-Z^_a-z{|}~])*@[a-zA-Z](-?[a-zA-Z0-9])*(\\.[a-zA-Z](-?[a-zA-Z0-9])*)+$";

	@Autowired
	protected AccountRepository accountRepository;
	
	@Autowired
	private ErrorService errorService;
	
	//@Autowired
	//private AccountSearchService accountSearchService;

    @Override
    public Account getAccountById(String accountId){
        Assert.notNull(accountId, "accountId must not be null");
        return accountRepository.findAccountById(accountId);
    }

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
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_ACCOUNT_NOT_FOUND);
		} else if (!account.getStatus().equals(AccountStatusEnum.NOTCONFIRMED)) {
			String msg = String.format("account status must be: %s", AccountStatusEnum.NOTCONFIRMED);
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_ACCOUNT_ALREADY_CONFIRM);
		} else if (account.getId().hashCode() == Integer.valueOf(value).intValue()) {
            // TODO dikma: не очень круто генерить хеш по идентификатору, да и присылаемое значение может быть не числом (получим NumberFormatException) ;)
            accountRepository.changeStatus(account.getId(), AccountStatusEnum.ACTIVE);
		} else {
			String msg = String.format("value not correct, account not been confirmed id: %s", accountId);
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_ACCOUNT_WRONG_CONFIRM_VALUE);
		}
	}

	@Override
	public void changeEmail(String accountId, String currentEmail, String newEmail) {
		Assert.notNull(accountId, "accountId must not be null");
		Assert.notNull(newEmail, "email must not be null");

		if (!newEmail.matches(EMAIL_REGEX)) {
			String msg = String.format("email is not correct");
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_EMAIL_IS_NOT_CORRECT);
		}
		
		Account account = accountRepository.findOne(accountId);

		if (account == null) {
			String msg = String.format("account not find by id %s", accountId);
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_ACCOUNT_NOT_FOUND);
		} else if (!account.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("account status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_ACCOUNT_NOT_ACTIVE);
		} else if (!account.getEmail().equals(currentEmail)) {
			String msg = String.format("current email not equals");
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_ACCOUNT_CURRENT_EMAIL_NOT_EQUALS);
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
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_ACCOUNT_NOT_FOUND);
		} else if (!account.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("account status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_ACCOUNT_NOT_ACTIVE);
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
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_ACCOUNT_NOT_FOUND);
		} else if (!account.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("account status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_ACCOUNT_NOT_ACTIVE);
		} else {
			accountRepository.saveProfile(accountId, profile);
			// TODO dikma: подключить поиск когда...
//			account = accountRepository.findOne(accountId);
//			accountSearchService.saveAccount(account);
		}
	}
	
	@Override
	public AccountRating getAccountRating(String accountId) {
		Assert.notNull(accountId, "accountId must not be null");
		return accountRepository.getRating(accountId);
	}

	@Override
	public void updateAccountRatingValue(String accountId, double ratingValue) {
		Assert.notNull(accountId, "accountId must not be null");
		Assert.notNull(ratingValue, "ratingValue must not be null");
		accountRepository.updateRating(accountId, ratingValue);
	}

    @Override
    public void delete(String accountId){

    }
}

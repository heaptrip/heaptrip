package com.heaptrip.service.account;

import com.heaptrip.domain.entity.account.*;
import com.heaptrip.domain.entity.account.community.Community;
import com.heaptrip.domain.entity.account.relation.RelationTypeEnum;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.AccountRepository;
import com.heaptrip.domain.repository.account.relation.RelationRepository;
import com.heaptrip.domain.service.account.AccountService;
import com.heaptrip.domain.service.account.AccountStoreService;
import com.heaptrip.domain.service.image.ImageService;
import com.heaptrip.domain.service.system.ErrorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    protected static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    public static String EMAIL_REGEX = "^[-!#$%&'*+/0-9=?A-Z^_a-z{|}~](\\.?[-!#$%&'*+/0-9=?A-Z^_a-z{|}~])*@[a-zA-Z](-?[a-zA-Z0-9])*(\\.[a-zA-Z](-?[a-zA-Z0-9])*)+$";

    static int NAME_MIN_LENGTH = 3;
    static int NAME_MAX_LENGTH = 100;

    @Autowired
    protected AccountRepository accountRepository;

    @Autowired
    private ErrorService errorService;

    @Autowired
    private AccountStoreService accountStoreService;

    @Autowired
    protected ImageService imageService;

    @Autowired
    private RelationRepository relationRepository;

    @Override
    public Account getAccountById(String accountId) {
        Assert.notNull(accountId, "accountId must not be null");
        return accountRepository.findById(accountId);
    }

    @Override
    public void hardRemove(String accountId) {
        Assert.notNull(accountId, "accountId must not be null");
        Account account = accountRepository.findOne(accountId);
        if (account != null) {
            if (account.getImage() != null) {
                imageService.removeImageById(account.getImage().getId());
            }
            accountRepository.remove(accountId);
        }
    }

    @Override
    public Future<Void> confirmRegistration(String accountId, String value) {
        Future<Void> future;

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
        } else if (account.getSendValue().equals(value)) {
            accountRepository.changeStatus(account.getId(), AccountStatusEnum.ACTIVE);

            if (!account.getAccountType().toString().equals(AccountEnum.USER.toString()) && account instanceof Community && (((Community) account).getOwnerAccountId() != null)) {
                relationRepository.add(account.getId(), ((Community) account).getOwnerAccountId(), RelationTypeEnum.OWNER);
            }

            future = accountStoreService.save(account.getId());
        } else {
            String msg = String.format("value not correct, account not been confirmed id: %s", accountId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_ACCOUNT_WRONG_CONFIRM_VALUE);
        }

        return future;
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
    public Future<Void> saveProfile(String accountId, String name, Profile profile) {
        Future<Void> future;

        Assert.notNull(accountId, "accountId must not be null");
        nameIsCorrectly(name);
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
            accountRepository.saveProfile(accountId, name, profile);
            future = accountStoreService.update(accountId);
        }

        return future;
    }

    @Override
    public AccountRating getAccountRating(String accountId) {
        Assert.notNull(accountId, "accountId must not be null");
        return accountRepository.getRating(accountId);
    }

    @Override
    public void setAccountRating(String accountId, AccountRating accountRating) {
        Assert.notNull(accountId, "accountId must not be null");
        Assert.notNull(accountRating, "accountRating must not be null");
        Assert.notNull(accountRating.getK(), "accountRating.K must not be null");
        accountRepository.setRating(accountId, accountRating);
        accountStoreService.updateRating(accountId, accountRating.getValue());
    }

    @Override
    public void updateAccountRatingValue(String accountId, double ratingValue) {
        Assert.notNull(accountId, "accountId must not be null");
        Assert.notNull(ratingValue, "ratingValue must not be null");
        accountRepository.updateRating(accountId, ratingValue);
        accountStoreService.updateRating(accountId, ratingValue);
    }

    @Override
    public void delete(String accountId) {

    }

    protected void nameIsCorrectly(String name) {
        Assert.notNull(name, "name must not be null");
        Assert.isTrue(name.length() >= NAME_MIN_LENGTH,
                "length name must be at least 3 characters and maximum length of 100");
        Assert.isTrue(name.length() <= NAME_MAX_LENGTH,
                "length name must be at least 3 characters and maximum length of 100");
    }
}
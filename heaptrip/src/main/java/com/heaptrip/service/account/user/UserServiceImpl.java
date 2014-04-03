package com.heaptrip.service.account.user;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.user.SocialNetwork;
import com.heaptrip.domain.entity.account.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.account.user.UserRegistration;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.entity.mail.MailEnum;
import com.heaptrip.domain.entity.mail.MailTemplate;
import com.heaptrip.domain.entity.mail.MailTemplateStorage;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.AccountRepository;
import com.heaptrip.domain.repository.account.user.UserRepository;
import com.heaptrip.domain.service.account.AccountStoreService;
import com.heaptrip.domain.service.account.user.UserService;
import com.heaptrip.domain.service.system.ErrorService;
import com.heaptrip.domain.service.system.MailService;
import com.heaptrip.domain.service.system.RequestScopeService;
import com.heaptrip.service.account.AccountServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

@Service
public class UserServiceImpl extends AccountServiceImpl implements UserService {

    static String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]))";
    static int PASSWORD_MIN_LENGTH = 8;
    static int PASSWORD_MAX_LENGTH = 32;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MailTemplateStorage mailTemplateStorage;

    @Autowired
    @Qualifier("requestScopeService")
    private RequestScopeService requestScopeService;

    @Autowired
    private MailService mailService;

    @Autowired
    private ErrorService errorService;

    @Autowired
    private AccountStoreService accountStoreService;

    @Override
    public void delete(String accountId) {
        Assert.notNull(accountId, "accountId must not be null");
        // TODO dikma после реализации сообществ, добавть проверку их наличия, если есть, удалить профиль нельзя если пользователь единственныйвладелец

        Account account = accountRepository.findOne(accountId);

        if (account == null) {
            String msg = String.format("account not find by id: %s", accountId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
        } else if (!account.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("account status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
        } else {
            accountRepository.changeStatus(accountId, AccountStatusEnum.DELETED);
            accountStoreService.remove(accountId);
        }
    }

    @Override
    public User registration(UserRegistration userRegistration, InputStream isImage, Locale locale) throws IOException,
            NoSuchAlgorithmException, MessagingException {
        Assert.notNull(userRegistration, "userRegistration must not be null");
        Assert.notNull(userRegistration.getEmail(), "email must not be null");
        Assert.isTrue(userRegistration.getEmail().matches(EMAIL_REGEX), "email is not correct");

        List<Account> accounts = accountRepository.findUsersByEmail(userRegistration.getEmail(), AccountStatusEnum.ACTIVE);

        if (accounts != null && accounts.size() > 0) {
            String msg = String.format("account with the email already exists");
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_ACCOUNT_WITH_THE_EMAIL_ALREADY_EXISTS);
        }

        if (userRegistration.getNet() == null) {
            Assert.notNull(userRegistration.getPassword(), "password must not be null");
            Assert.isTrue(userRegistration.getPassword().length() >= PASSWORD_MIN_LENGTH,
                    "length password must be at least 8 characters and maximum length of 32");
            Assert.isTrue(userRegistration.getPassword().length() <= PASSWORD_MAX_LENGTH,
                    "length password must be at least 8 characters and maximum length of 32");
            Assert.isTrue(!userRegistration.getPassword().matches(PASSWORD_REGEX),
                    "password must contains 0-9, lowercase characters a-z and uppercase characters A-Z");
            userRegistration.setExtImageStore(SocialNetworkEnum.NONE.toString());
        } else {
            Assert.notEmpty(userRegistration.getNet(), "the array social network must have one element");
            SocialNetwork[] net = userRegistration.getNet();
            Assert.isTrue(net.length == 1, "the array social network must have one element");
            Assert.notNull(net[0].getId(), "id network must not be null");
            Assert.notNull(net[0].getUid(), "uid must not be null");

            if (isImage != null) {

                Image image = imageService.addImage(userRegistration.getId(), ImageEnum.ACCOUNT_IMAGE, net[0].getId() + net[0].getUid(), isImage);
                userRegistration.setImage(image);

                byte[] d = DigestUtils.md5(isImage);
                Byte[] digest = ArrayUtils.toObject(d);

                userRegistration.setImageCRC(digest);
                userRegistration.setExtImageStore(net[0].getId());
            }
        }

        String[] roles = {"ROLE_USER"};
        userRegistration.setRoles(roles);

        userRegistration.setRating(AccountRating.getDefaultValue());
        UserRegistration user = userRepository.save(userRegistration);

        MailTemplate mt = mailTemplateStorage.getMailTemplate(MailEnum.CONFIRM_REGISTRATION);

        StringBuilder str = new StringBuilder();
        str.append(requestScopeService.getCurrentContextPath());
        str.append("/mail/registration/confirm?");
        str.append("uid=").append(user.getId());
        str.append("&value=").append(user.getId().hashCode());

        String msg = String.format(mt.getText(locale), str.toString());
        mailService.sendNoreplyMessage(user.getEmail(), mt.getSubject(locale), msg);

        return user;
    }

    @Override
    public void changePassword(String userId, String currentPassword, String newPassword) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(newPassword, "password must not be null");

        if (newPassword.length() < PASSWORD_MIN_LENGTH || newPassword.length() > PASSWORD_MAX_LENGTH || newPassword.matches(PASSWORD_REGEX)) {
            String msg = String.format("password is not correct");
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_PSWD_IS_NOT_CORRECT);
        }

        UserRegistration user = (UserRegistration) accountRepository.findOne(userId);

        if (user == null) {
            String msg = String.format("user not find by id %s", userId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
        } else if (user.getPassword() != null && !user.getPassword().equals(currentPassword)) {
            String msg = String.format("wrong current password");
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_CURRENT_PSWD_WRONG);
        } else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
        } else {
            userRepository.changePassword(userId, newPassword);
        }
    }

    @Override
    public void profileImageFrom(String userId, SocialNetworkEnum socialNetwork) {
        Assert.notNull(userId, "userId must not be null");
        User user = userRepository.findOne(userId);

        if (user == null) {
            String msg = String.format("user not find by userId: %s", userId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
        } else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
        } else {
            boolean findNet = false;

            if (socialNetwork.equals(SocialNetworkEnum.NONE)) {
                findNet = true;
            } else if (user.getNet() != null) {
                // TODO dikma: 'for' statement does not loop
                for (SocialNetwork net : user.getNet()) {
                    // TODO dikma: Result of 'String.equals()' is ignored. 'equals()' between objects of inconvertible types 'SocialNetworkEnum' and 'String'
                    net.getId().equals(socialNetwork);
                    findNet = true;
                    break;
                }
            }

            if (findNet) {
                userRepository.profileImageFrom(userId, socialNetwork);
            } else {
                String msg = String.format("user id=%s don`t have social net %s", userId, socialNetwork);
                logger.debug(msg);
                throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_DONT_HAVE_SOCIAL_NET);
            }
        }
    }

    @Override
    public void unlinkSocialNetwork(String userId, SocialNetworkEnum socialNetwork) {
        Assert.notNull(userId, "userId must not be null");
        Assert.isTrue(!socialNetwork.equals(SocialNetworkEnum.NONE), "socialNetwork must not be NONE");
        User user = userRepository.findOne(userId);

        if (user == null) {
            String msg = String.format("user not find by id %s", userId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
        } else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
        } else {
            SocialNetwork unlinkNet = null;

            if (user.getNet() != null) {
                if (user.getNet().length == 1 && userRepository.isEmptyPassword(userId)) {
                    String msg = String.format("user id=%s have one social network and empty password", userId);
                    logger.debug(msg);
                    throw errorService.createException(AccountException.class,
                            ErrorEnum.ERROR_USER_HAVE_ONE_SOCIAL_NET_AND_EMPTY_PSWD);
                }

                for (SocialNetwork net : user.getNet()) {
                    if (net.getId().equals(socialNetwork.toString())) {
                        unlinkNet = net;
                        break;
                    }
                }
            }

            if (unlinkNet == null) {
                String msg = String.format("user id=%s don`t have social net %s", userId, socialNetwork.toString());
                logger.debug(msg);
                throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_DONT_HAVE_SOCIAL_NET);
            } else {
                userRepository.unlinkSocialNetwork(userId, socialNetwork);
            }
        }
    }

    @Override
    public void linkSocialNetwork(String userId, SocialNetwork socialNetwork) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(socialNetwork, "socialNetwork must not be null");
        Assert.notNull(socialNetwork.getId(), "socialNetwork id must not be null");
        Assert.notNull(socialNetwork.getUid(), "socialNetwork uid must not be null");

        User user = userRepository.findOne(userId);

        if (user == null) {
            String msg = String.format("user not find by id %s", userId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
        } else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
        } else {
            boolean existsNet = false;

            if (user.getNet() != null) {
                for (SocialNetwork net : user.getNet()) {
                    if (net.getId().equals(socialNetwork.getId())) {
                        existsNet = true;
                        break;
                    }
                }
            }

            if (existsNet) {
                String msg = String.format("user id=%s have social net %s", userId, socialNetwork.toString());
                logger.debug(msg);
                throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_ALREADY_HAS_SOCIAL_NET);
            } else {
                userRepository.linkSocialNetwork(userId, socialNetwork);
            }
        }
    }
}

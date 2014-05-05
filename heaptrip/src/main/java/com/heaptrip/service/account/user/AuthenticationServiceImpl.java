package com.heaptrip.service.account.user;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.entity.mail.MailEnum;
import com.heaptrip.domain.entity.mail.MailTemplate;
import com.heaptrip.domain.entity.mail.MailTemplateStorage;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.AccountRepository;
import com.heaptrip.domain.repository.account.user.UserRepository;
import com.heaptrip.domain.service.account.AccountStoreService;
import com.heaptrip.domain.service.account.user.AuthenticationService;
import com.heaptrip.domain.service.account.user.UserService;
import com.heaptrip.domain.service.image.ImageService;
import com.heaptrip.domain.service.system.ErrorService;
import com.heaptrip.domain.service.system.MailService;
import com.heaptrip.util.stream.StreamUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private MailTemplateStorage mailTemplateStorage;

    @Autowired
    private MailService mailService;

    @Autowired
    private ErrorService errorService;

    @Autowired
    private AccountStoreService accountStoreService;

    @Autowired
    private UserService userService;

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        Assert.notNull(email, "email must not be null");
        Assert.notNull(password, "password must not be null");
        User result = null;

        List<User> users = userRepository.findUsersByEmail(email);

        if (users != null && users.size() > 0) {
            for (User user : users) {
                if (user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
                    if (user.getPwd() != null && userService.checkPassword(password, user)) {
                        return user;
                    } else {
                        String msg = String.format("Password is wrong, user email: %s", email);
                        logger.debug(msg);
                        throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_PSWD_IS_NOT_CORRECT);
                    }
                } else {
                    result = user;
                }
            }
        }

        return result;
    }

    @Override
    public User getUserBySocNetUID(String socNetName, String uid, InputStream isImage) throws IOException, NoSuchAlgorithmException {
        Assert.notNull(socNetName, "socNetName must not be null");
        Assert.isTrue(!socNetName.equals(SocialNetworkEnum.NONE.toString()), "socNetName must not be NONE");
        Assert.notNull(uid, "uid must not be null");
        Assert.notNull(isImage, "isImage must not be null");

        User user = userRepository.findUserBySocNetUID(socNetName, uid);

        if (user != null) {
            if (socNetName.equals(user.getExternalImageStore())) {
                isImage = StreamUtils.getResetableInputStream(isImage);
                byte[] d = DigestUtils.md5(isImage);
                Byte[] digest = ArrayUtils.toObject(d);

                if (!Arrays.equals(user.getImageCRC(), digest)) {
                    isImage.reset();
                    Image image = imageService.addImage(user.getId(), ImageEnum.ACCOUNT_IMAGE, socNetName + uid, isImage);
                    accountStoreService.changeImage(user.getId(), image);
                    user.setImageCRC(digest);
                    userRepository.save(user);
                }
            }
        }

        return user;
    }

    @Override
    public void resetPassword(String email, Locale locale) {
        Assert.notNull(email, "email must not be null");

        List<User> users = userRepository.findUsersByEmailAndStatus(email, AccountStatusEnum.ACTIVE);

        if (users == null || users.size() == 0) {
            String msg = String.format("user not find by email: %s", email);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND_BY_EMAIL);
        } else if (users.size() > 1) {
            String msg = String.format("more one user have status: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_MORE_THAN_ONE_USER_HAVE_ACTIVE_STATUS);
        } else {
            try {
                users.get(0).setSendValue(userService.byteToBase64(userService.generateSalt()));
            } catch (NoSuchAlgorithmException e) {
                String msg = String.format("Error salt generation: %s", e.getMessage());
                logger.debug(msg);
                throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_RESET_PASSWORD);
            }
            accountRepository.changeSendValue(users.get(0).getId(), users.get(0).getSendValue());

            MailTemplate mt = mailTemplateStorage.getMailTemplate(MailEnum.RESET_PASSWORD);

            StringBuilder str = new StringBuilder();
            str.append("http://")
                    .append("heaptrip.com")
                    .append("/mail/password/reset?")
                    .append("uid=").append(users.get(0).getId())
                    .append("&value=").append(users.get(0).getSendValue());

            String msg = String.format(mt.getText(locale), str.toString());
            mailService.sendNoreplyMessage(email, mt.getSubject(locale), msg);
        }
    }

    @Override
    public void sendNewPassword(String accountId, String value, Locale locale) throws MessagingException {
        Assert.notNull(accountId, "accountId must not be null");
        Assert.notNull(value, "value must not be null");

        User user = userRepository.findOne(accountId);

        if (user == null) {
            String msg = String.format("user not find by id %s", accountId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
        } else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
        } else if (user.getSendValue().equals(value)) {
            String newPassword = RandomStringUtils.randomAlphanumeric(8);
            String pwd;

            try {
                pwd = userService.byteToBase64(userService.generatePassword(newPassword, userService.base64ToByte(user.getSalt())));
            } catch (Exception e) {
                String msg = String.format("Error password generation: %s", e.getMessage());
                logger.debug(msg);
                throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_CHANGE_PASSWORD);
            }

            userRepository.changePassword(accountId, pwd);

            MailTemplate mt = mailTemplateStorage.getMailTemplate(MailEnum.SEND_NEW_PASSWORD);
            String msg = String.format(mt.getText(locale), newPassword);
            mailService.sendNoreplyMessage(user.getEmail(), mt.getSubject(locale), msg);
        } else {
            String msg = String.format("value not correct, no password has been sent to id: %s", accountId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_PSWD_VALUE_IS_WRONG);
        }
    }
}

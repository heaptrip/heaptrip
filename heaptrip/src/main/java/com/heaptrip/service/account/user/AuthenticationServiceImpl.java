package com.heaptrip.service.account.user;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Locale;

import javax.mail.MessagingException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.entity.mail.MessageEnum;
import com.heaptrip.domain.entity.mail.MessageTemplate;
import com.heaptrip.domain.entity.mail.MessageTemplateStorage;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.AccountRepository;
import com.heaptrip.domain.repository.account.user.UserRepository;
import com.heaptrip.domain.service.account.user.AuthenticationService;
import com.heaptrip.domain.service.image.ImageService;
import com.heaptrip.domain.service.system.ErrorService;
import com.heaptrip.domain.service.system.MailService;
import com.heaptrip.util.stream.StreamUtils;

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
    private MessageTemplateStorage messageTemplateStorage;

    @Autowired
    private MailService mailService;

    @Autowired
    private ErrorService errorService;

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        Assert.notNull(email, "email must not be null");
        Assert.notNull(password, "password must not be null");
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User getUserBySocNetUID(String socNetName, String uid, InputStream isImage) throws IOException, NoSuchAlgorithmException {
        Assert.notNull(socNetName, "socNetName must not be null");
        Assert.isTrue(!socNetName.equals(SocialNetworkEnum.NONE), "socNetName must not be NONE");
        Assert.notNull(uid, "uid must not be null");
        Assert.notNull(isImage, "isImage must not be null");

        User user = userRepository.findUserBySocNetUID(socNetName, uid);

        if (user != null) {
            if (socNetName.equals(user.getExternalImageStore())) {

                isImage = StreamUtils.getResetableInputStream(isImage);

                MessageDigest md;
                md = MessageDigest.getInstance("MD5");
                byte[] image = new byte[isImage.available()];
                isImage.read(image);
                byte[] d = md.digest(image);
                Byte[] digest = ArrayUtils.toObject(d);

                if (!Arrays.equals(user.getImageCRC(), digest)) {

                    isImage.reset();
                    user.setImageContentId(imageService.saveImage(socNetName + uid, ImageEnum.USER_CONTENT_PHOTO, isImage));
                    isImage.reset();
                    user.setImageProfileId(imageService.saveImage(socNetName + uid, ImageEnum.USER_PHOTO_PROFILE, isImage));

                    user.setImageCRC(digest);

                    userRepository.save(user);
                }
            }
        }

        return user;
    }

    @Override
    public void resetPassword(String email, Locale locale) throws MessagingException {
        Assert.notNull(email, "email must not be null");

        Account account = accountRepository.findUserByEmail(email);

        if (account == null) {
            String msg = String.format("user not find by email: %s", email);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND_BY_EMAIL);
        } else if (!account.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
        } else {
            MessageTemplate mt = messageTemplateStorage.getMessageTemplate(MessageEnum.RESET_PASSWORD);

            StringBuilder str = new StringBuilder();
            str.append("http://")
                    .append("heaptrip.com")
                    .append("/mail/password/reset?")
                    .append("uid=").append(account.getId())
                    .append("&value=").append(account.getId().hashCode());

            String msg = String.format(mt.getText(locale), str.toString());
            mailService.sendNoreplyMessage(email, mt.getSubject(locale), msg);
        }
    }

    @Override
    public void sendNewPassword(String accountId, String value, Locale locale) throws MessagingException {
        Assert.notNull(accountId, "accountId must not be null");
        Assert.notNull(value, "value must not be null");

        Account account = accountRepository.findOne(accountId);

        if (account == null) {
            String msg = String.format("user not find by id %s", accountId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
        } else if (!account.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
        } else if (account.getId().hashCode() == Integer.valueOf(value).intValue()) {
            String newPassword = RandomStringUtils.randomAlphanumeric(8);
            userRepository.changePassword(accountId, newPassword);

            MessageTemplate mt = messageTemplateStorage.getMessageTemplate(MessageEnum.SEND_NEW_PASSWORD);
            String msg = String.format(mt.getText(locale), newPassword);
            mailService.sendNoreplyMessage(account.getEmail(), mt.getSubject(locale), msg);
        } else {
            String msg = String.format("value not correct, no password has been sent to id: %s", accountId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_PSWD_VALUE_IS_WRONG);
        }
    }
}

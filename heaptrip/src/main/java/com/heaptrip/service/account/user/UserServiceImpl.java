package com.heaptrip.service.account.user;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.relation.Relation;
import com.heaptrip.domain.entity.account.relation.RelationTypeEnum;
import com.heaptrip.domain.entity.account.user.SocialNetwork;
import com.heaptrip.domain.entity.account.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.image.Image;
import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.entity.mail.MailEnum;
import com.heaptrip.domain.entity.mail.MailTemplate;
import com.heaptrip.domain.entity.mail.MailTemplateStorage;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.AccountRepository;
import com.heaptrip.domain.repository.account.relation.RelationRepository;
import com.heaptrip.domain.repository.account.user.UserRepository;
import com.heaptrip.domain.repository.content.ContentRepository;
import com.heaptrip.domain.service.account.AccountStoreService;
import com.heaptrip.domain.service.account.criteria.relation.UserRelationCriteria;
import com.heaptrip.domain.service.account.relation.RelationService;
import com.heaptrip.domain.service.account.user.UserService;
import com.heaptrip.domain.service.system.ErrorService;
import com.heaptrip.domain.service.system.MailService;
import com.heaptrip.domain.service.system.RequestScopeService;
import com.heaptrip.service.account.AccountServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;

@Service
public class UserServiceImpl extends AccountServiceImpl implements UserService {

    static String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]))";
    static int PASSWORD_MIN_LENGTH = 8;
    static int PASSWORD_MAX_LENGTH = 32;

    @Value("${global.salt:m9vbmxudSbDcM/f8yCqbng==}")
    private String globalSalt;

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

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private RelationRepository relationRepository;

    @Autowired
    private RelationService relationService;

    @Override
    public void delete(String accountId) {
        Assert.notNull(accountId, "accountId must not be null");

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
            if (contentRepository.haveActiveContent(accountId)) {
                String msg = String.format("account have active content: %s", accountId);
                logger.debug(msg);
                throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_HAVE_ACTIVE_CONTENT);
            }

            String[] typeRelations = new String[1];
            typeRelations[0] = RelationTypeEnum.OWNER.toString();

            List<Relation> relations = relationRepository.findByUserRelationCriteria(new UserRelationCriteria(accountId, typeRelations));

            if (relations != null && relations.size() > 0) {
                for (Relation relation : relations) {
                    relationService.deleteOwner(accountId, relation.getFromId());
                }
            }

            accountRepository.changeStatus(accountId, AccountStatusEnum.DELETED);
            accountStoreService.remove(accountId);
        }
    }

    @Override
    public User registration(User user, String password, InputStream isImage, Locale locale) throws IOException,
            NoSuchAlgorithmException, MessagingException {
        Assert.notNull(user, "userRegistration must not be null");
        nameIsCorrectly(user.getName());
        Assert.notNull(user.getEmail(), "email must not be null");
        Assert.isTrue(user.getEmail().matches(EMAIL_REGEX), "email is not correct");

        List<Account> accounts = accountRepository.findAccountsByEmailAndStatus(user.getEmail(), AccountStatusEnum.ACTIVE);

        if (accounts != null && accounts.size() > 0) {
            String msg = String.format("account with the email already exists");
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_ACCOUNT_WITH_THE_EMAIL_ALREADY_EXISTS);
        }

        String[] roles = {"ROLE_USER"};
        user.setRoles(roles);
        user.setRating(AccountRating.getDefaultValue());
        user.setSendValue(byteToBase64(generateSalt()));
        byte[] salt = generateSalt();
        user.setSalt(byteToBase64(salt));


        if (user.getNet() == null) {
            Assert.notNull(password, "password must not be null");
            Assert.isTrue(password.length() >= PASSWORD_MIN_LENGTH,
                    "length password must be at least 8 characters and maximum length of 32");
            Assert.isTrue(password.length() <= PASSWORD_MAX_LENGTH,
                    "length password must be at least 8 characters and maximum length of 32");
            Assert.isTrue(!password.matches(PASSWORD_REGEX),
                    "password must contains 0-9, lowercase characters a-z and uppercase characters A-Z");

            user.setPwd(byteToBase64(generatePassword(password, salt)));
            user.setExtImageStore(SocialNetworkEnum.NONE.toString());
        } else {
            Assert.notEmpty(user.getNet(), "the array social network must have one element");
            SocialNetwork[] net = user.getNet();
            Assert.isTrue(net.length == 1, "the array social network must have one element");
            Assert.notNull(net[0].getId(), "id network must not be null");
            Assert.notNull(net[0].getUid(), "uid must not be null");

            if(isImage!=null){
                byte[] d = DigestUtils.md5(isImage);
                Byte[] digest = ArrayUtils.toObject(d);
                user.setImageCRC(digest);
                user.setExtImageStore(net[0].getId());
            }

        }

        User savedUser = userRepository.save(user);

        if (isImage != null) {
            Image image = imageService.addImage(savedUser.getId(), ImageEnum.ACCOUNT_IMAGE, savedUser.getId() + ImageEnum.ACCOUNT_IMAGE.name(), isImage);
            accountStoreService.changeImage(savedUser.getId(), image);

            user.setImage(image);



        } else {
            // TODO voronenko : картинку надо сетить не только если сс, но дефаултовую, если нет никакой
        }


        MailTemplate mt = mailTemplateStorage.getMailTemplate(MailEnum.CONFIRM_REGISTRATION);

        StringBuilder str = new StringBuilder();
        str.append(requestScopeService.getCurrentContextPath());
        str.append("/mail/registration/confirm?");
        str.append("uid=").append(savedUser.getId());
        str.append("&value=").append(user.getSendValue());

        String msg = String.format(mt.getText(locale), str.toString());
        mailService.sendNoreplyMessage(savedUser.getEmail(), mt.getSubject(locale), msg);

        return savedUser;
    }

    @Override
    public void changePassword(String userId, String currentPassword, String newPassword) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(newPassword, "password must not be null");
        Assert.isTrue(newPassword.length() >= PASSWORD_MIN_LENGTH, "length password must be at least 8 characters and maximum length of 32");
        Assert.isTrue(newPassword.length() <= PASSWORD_MAX_LENGTH, "length password must be at least 8 characters and maximum length of 32");
        Assert.isTrue(!newPassword.matches(PASSWORD_REGEX), "password must contains 0-9, lowercase characters a-z and uppercase characters A-Z");

        User user = userRepository.findOne(userId);

        if (user == null) {
            String msg = String.format("user not find by id %s", userId);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_FOUND);
        } else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
            String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_NOT_ACTIVE);
        } else if (user.getPwd() != null && !checkPassword(currentPassword, user)) {
            String msg = String.format("wrong current password");
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_CURRENT_PSWD_WRONG);
        } else {
            String pwd;

            try {
                pwd = byteToBase64(generatePassword(newPassword, base64ToByte(user.getSalt())));
            } catch (Exception e) {
                String msg = String.format("Error password generation: %s", e.getMessage());
                logger.debug(msg);
                throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_CHANGE_PASSWORD);
            }

            userRepository.changePassword(userId, pwd);
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
                for (SocialNetwork net : user.getNet()) {
                    if (net.getId().equals(socialNetwork.toString())) {
                        findNet = true;
                        break;
                    }
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

    @Override
    public boolean checkPassword(String password, User user) {
        String temp;
        try {
            temp = byteToBase64(generatePassword(password, base64ToByte(user.getSalt())));
        } catch (Exception e) {
            String msg = String.format("Error password validation: %s", e.getMessage());
            logger.debug(msg);
            throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_CHECK_PASSWORD);
        }
        return user.getPwd().equals(temp);
    }

    @Override
    public byte[] generatePassword(String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        digest.update(globalSalt.getBytes("UTF-8"));
        byte[] temp = digest.digest(password.getBytes("UTF-8"));

        digest.reset();
        digest.update(salt);
        return digest.digest(temp);
    }

    @Override
    public byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * From a base 64 representation, returns the corresponding byte[]
     *
     * @param data String The base64 representation
     * @return byte[]
     * @throws IOException
     */
    @Override
    public byte[] base64ToByte(String data) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(data);
    }

    /**
     * From a byte[] returns a base 64 representation
     *
     * @param data byte[]
     * @return String
     */
    @Override
    public String byteToBase64(byte[] data) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
}

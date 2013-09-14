package com.heaptrip.service.account.user;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.mail.MessagingException;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.account.Account;
import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.user.SocialNetwork;
import com.heaptrip.domain.entity.account.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.account.user.UserRegistration;
import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.entity.mail.MessageEnum;
import com.heaptrip.domain.entity.mail.MessageTemplate;
import com.heaptrip.domain.entity.mail.MessageTemplateStorage;
import com.heaptrip.domain.entity.rating.AccountRating;
import com.heaptrip.domain.exception.ErrorEnum;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.user.UserRepository;
import com.heaptrip.domain.service.account.AccountSearchService;
import com.heaptrip.domain.service.account.user.UserService;
import com.heaptrip.domain.service.image.ImageService;
import com.heaptrip.domain.service.system.ErrorService;
import com.heaptrip.domain.service.system.MailService;
import com.heaptrip.domain.service.system.RequestScopeService;
import com.heaptrip.service.account.AccountServiceImpl;
import com.heaptrip.util.stream.StreamUtils;

@Service
public class UserServiceImpl extends AccountServiceImpl implements UserService {

	static String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]))";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private MessageTemplateStorage messageTemplateStorage;

	@Autowired
	private RequestScopeService requestScopeService;

	@Autowired
	private MailService mailService;
	
	@Autowired
	private ErrorService errorService;
	
	@Autowired
	private AccountSearchService accountSearchService;

	@Override
	public void delete(String accountId) {
		Assert.notNull(accountId, "accountId must not be null");
		// TODO dikma после реализации сообществ, добавть проверку их наличия,
		// если есть, удалить профиль нельзя если пользователь единственный
		// владелец

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
		}
	}

	@Override
	public User registration(UserRegistration userRegistration, InputStream isImage, Locale locale) throws IOException,
			NoSuchAlgorithmException, MessagingException {
		Assert.notNull(userRegistration, "userRegistration must not be null");
		Assert.notNull(userRegistration.getEmail(), "email must not be null");
		Assert.isTrue(userRegistration.getEmail().matches(EMAIL_REGEX), "email is not correct");

		if (userRegistration.getNet() == null) {
			Assert.notNull(userRegistration.getPassword(), "password must not be null");
			Assert.isTrue(userRegistration.getPassword().length() >= 8,
					"length password must be at least 8 characters and maximum length of 32");
			Assert.isTrue(userRegistration.getPassword().length() <= 32,
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

				isImage = StreamUtils.getResetableInputStream(isImage);

				userRegistration.setImageContentId(imageService.saveImage(net[0].getId() + net[0].getUid(),
						ImageEnum.USER_CONTENT_PHOTO, isImage));
				isImage.reset();
				userRegistration.setImageProfileId(imageService.saveImage(net[0].getId() + net[0].getUid(),
						ImageEnum.USER_PHOTO_PROFILE, isImage));
				isImage.reset();

				MessageDigest md;
				md = MessageDigest.getInstance("MD5");
				byte[] image = new byte[isImage.available()];
				isImage.read(image);
				byte[] d = md.digest(image);
				Byte[] digest = ArrayUtils.toObject(d);

				userRegistration.setImageCRC(digest);
				userRegistration.setExtImageStore(net[0].getId());
			}
		}

		String[] roles = { "ROLE_USER" };
		userRegistration.setRoles(roles);
		
		UserRegistration user = userRepository.save(userRegistration);

		MessageTemplate mt = messageTemplateStorage.getMessageTemplate(MessageEnum.CONFIRM_REGISTRATION);

		StringBuilder str = new StringBuilder();
		str.append(requestScopeService.getCurrentContextPath());
		str.append("/mail/registration/confirm?");
		str.append("uid=").append(user.getId());
		str.append("&value=").append(user.getId().hashCode());

		String msg = String.format(mt.getText(locale), str.toString());
		mailService.sendNoreplyMessage(user.getEmail(), mt.getSubject(locale), msg);

		// TODO dikma: подключить поиск когда...
//		accountSearchService.saveAccount(user);
		
		return user;
	}

	@Override
	public void changePassword(String userId, String currentPassword, String newPassword) {
		Assert.notNull(userId, "userId must not be null");
		Assert.notNull(newPassword, "password must not be null");
		
		if (newPassword.length() < 8 || newPassword.length() > 32 || newPassword.matches(PASSWORD_REGEX)) {
			String msg = String.format("email is not correct");
			logger.debug(msg);
			throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_PSWD_IS_NOT_CORRECT);
		}
		
//		Assert.isTrue(newPassword.length() > 8,
//				"length password must be at least 8 characters and maximum length of 32");
//		Assert.isTrue(newPassword.length() < 32,
//				"length password must be at least 8 characters and maximum length of 32");
//		Assert.isTrue(!newPassword.matches(PASSWORD_REGEX),
//				"password must contains 0-9, lowercase characters a-z and uppercase characters A-Z");

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
		User user = (User) userRepository.findOne(userId);

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
		User user = (User) userRepository.findOne(userId);

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
					throw errorService.createException(AccountException.class, ErrorEnum.ERROR_USER_HAVE_ONE_SOCIAL_NET_AND_EMPTY_PSWD);
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

		User user = (User) userRepository.findOne(userId);

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

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
import com.heaptrip.domain.entity.account.AccountEnum;
import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.user.SocialNetwork;
import com.heaptrip.domain.entity.account.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.account.user.UserProfile;
import com.heaptrip.domain.entity.account.user.UserRegistration;
import com.heaptrip.domain.entity.account.user.UserSetting;
import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.entity.mail.MessageEnum;
import com.heaptrip.domain.entity.mail.MessageTemplate;
import com.heaptrip.domain.entity.mail.MessageTemplateStorage;
import com.heaptrip.domain.repository.account.user.UserRepository;
import com.heaptrip.domain.service.account.user.UserService;
import com.heaptrip.domain.service.image.ImageService;
import com.heaptrip.domain.service.mail.MailService;
import com.heaptrip.service.account.AccountServiceImpl;
import com.heaptrip.util.StreamUtils;

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
	private MailService mailService;
	
	@Override
	public void delete(String accountId) {
		Assert.notNull(accountId, "accountId must not be null");
		// TODO dikma после реализации сообществ, добавть проверку их наличия, если есть, удалить профиль нельзя если пользователь единственный владелец 
		
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
			accountRepository.changeStatus(accountId, AccountStatusEnum.DELETED);
		}
	}

	@Override
	public User registration(UserRegistration userRegistration, InputStream isImage, Locale locale) throws IOException, NoSuchAlgorithmException, MessagingException {
		Assert.notNull(userRegistration, "userRegistration must not be null");
		Assert.notNull(userRegistration.getEmail(), "email must not be null");
		Assert.isTrue(userRegistration.getEmail().matches(EMAIL_REGEX), "email is not correct");
		
		if (userRegistration.getNet() == null) {
			Assert.notNull(userRegistration.getPassword(), "password must not be null");
			Assert.isTrue(userRegistration.getPassword().length() > 8, "length password must be at least 8 characters and maximum length of 32");
			Assert.isTrue(userRegistration.getPassword().length() < 32, "length password must be at least 8 characters and maximum length of 32");
			Assert.isTrue(!userRegistration.getPassword().matches(PASSWORD_REGEX), "password must contains 0-9, lowercase characters a-z and uppercase characters A-Z");
			userRegistration.setExtImageStore(SocialNetworkEnum.NONE.toString());
		} else {
			Assert.notEmpty(userRegistration.getNet(), "the array social network must have one element");
			SocialNetwork[] net = userRegistration.getNet();
			Assert.isTrue(net.length == 1, "the array social network must have one element");
			Assert.notNull(net[0].getId(), "id network must not be null");
			Assert.notNull(net[0].getUid(), "uid must not be null");
			
			if (isImage != null) {
				
				isImage = StreamUtils.getResetableInputStream(isImage);
				
				userRegistration.setImageContentId(imageService.saveImage(net[0].getId() + net[0].getUid(), ImageEnum.USER_CONTENT_PHOTO, isImage));
				isImage.reset();
				userRegistration.setImageProfileId(imageService.saveImage(net[0].getId() + net[0].getUid(), ImageEnum.USER_PHOTO_PROFILE, isImage));
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
		
		userRegistration.setProfile(new UserProfile());
		userRegistration.setSetting(new UserSetting());
		String[] roles = { "ROLE_USER" };
		userRegistration.setRoles(roles);
		userRegistration.setStatus(AccountStatusEnum.NOTCONFIRMED);
		userRegistration.setTypeAccount(AccountEnum.USER);
		String[] frends = new String[0];
		userRegistration.setFrends(frends);
		String[] signed = new String[0];
		userRegistration.setSigned(signed);
		
		UserRegistration user = userRepository.save(userRegistration);

		MessageTemplate mt = messageTemplateStorage.getMessageTemplate(MessageEnum.CONFIRM_REGISTRATION);
		
		StringBuilder str = new StringBuilder();
		str.append("http://")
			.append("heaptrip.com")
			.append("/mail/registration/confirm?")
			.append("uid=").append(user.getId())
			.append("&value=").append(user.getId().hashCode());
		
		String msg = String.format(mt.getText(locale), str.toString());
		mailService.sendNoreplyMessage(user.getEmail(), mt.getSubject(locale), msg);		
		
		return user;
	}

	@Override
	public void changePassword(String userId, String currentPassword, String newPassword) {
		Assert.notNull(userId, "userId must not be null");
		Assert.notNull(newPassword, "password must not be null");
		Assert.isTrue(newPassword.length() > 8, "length password must be at least 8 characters and maximum length of 32");
		Assert.isTrue(newPassword.length() < 32, "length password must be at least 8 characters and maximum length of 32");
		Assert.isTrue(!newPassword.matches(PASSWORD_REGEX), "password must contains 0-9, lowercase characters a-z and uppercase characters A-Z");
		
		UserRegistration user = (UserRegistration) accountRepository.findOne(userId);
		
		if (user == null) {
			String msg = String.format("user not find by id %s", userId);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (user.getPassword() != null && !user.getPassword().equals(currentPassword)) {
			String msg = String.format("wrong current password");
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			// TODO dikma: заменить бизнес исключение
			throw new IllegalArgumentException(msg);
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
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
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
				throw new IllegalArgumentException(String.format("user id=%s don`t have social net ", userId, socialNetwork));
			}
		}
	}

	@Override
	public void unlinkSocialNetwork(String userId, SocialNetworkEnum socialNetwork) {
		Assert.notNull(userId, "userId must not be null");
		Assert.isTrue(!socialNetwork.equals(SocialNetworkEnum.NONE), "socialNetwork must not be NONE");
		User user = (User) userRepository.findOne(userId);
		
		if (user == null) {
			String msg = String.format("user with id=%s is not found", userId);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else {
			SocialNetwork unlinkNet = null;
			
			if (user.getNet() != null) {
				if (user.getNet().length == 1 && userRepository.isEmptyPassword(userId)) {
					String msg = String.format("user id=%s have one social network and empty password", userId);
					logger.debug(msg);
					// TODO dikma: создать бизнес исключение
					throw new IllegalArgumentException(msg);
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
				// TODO dikma: создать бизнес исключение
				throw new IllegalArgumentException(msg);
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
			String msg = String.format("user with id=%s is not found", userId);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
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
				// TODO dikma: создать бизнес исключение
				throw new IllegalArgumentException(msg);
			} else {
				userRepository.linkSocialNetwork(userId, socialNetwork);
			}
		}
	}
}

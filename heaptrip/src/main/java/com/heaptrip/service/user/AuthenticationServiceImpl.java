package com.heaptrip.service.user;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Locale;

import javax.mail.MessagingException;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.image.ImageEnum;
import com.heaptrip.domain.entity.mail.MessageEnum;
import com.heaptrip.domain.entity.mail.MessageTemplate;
import com.heaptrip.domain.entity.mail.MessageTemplateStorage;
import com.heaptrip.domain.entity.user.SocialNetwork;
import com.heaptrip.domain.entity.user.SocialNetworkEnum;
import com.heaptrip.domain.entity.user.User;
import com.heaptrip.domain.entity.user.UserProfile;
import com.heaptrip.domain.entity.user.UserRegistration;
import com.heaptrip.domain.entity.user.UserSetting;
import com.heaptrip.domain.repository.user.AuthenticationRepository;
import com.heaptrip.domain.service.image.ImageService;
import com.heaptrip.domain.service.mail.MailService;
import com.heaptrip.domain.service.user.AuthenticationService;
import com.heaptrip.util.StreamUtils;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	static String EMAIL_REGEX = "^[-!#$%&'*+/0-9=?A-Z^_a-z{|}~](\\.?[-!#$%&'*+/0-9=?A-Z^_a-z{|}~])*@[a-zA-Z](-?[a-zA-Z0-9])*(\\.[a-zA-Z](-?[a-zA-Z0-9])*)+$";
	static String PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]))";
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
	
	@Autowired
	private AuthenticationRepository authenticationRepository;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private MessageTemplateStorage messageTemplateStorage;
	
	@Override
	public User getUserByEmail(String email, String password) {
		Assert.notNull(email, "email must not be null");
		Assert.notNull(password, "password must not be null");
		return authenticationRepository.findByEmailAndPassword(email, password);
	}

	@Override
	public User getUserBySocNetUID(String socNetName, String uid, InputStream isImage) throws IOException, NoSuchAlgorithmException {
		Assert.notNull(socNetName, "socNetName must not be null");
		Assert.isTrue(!socNetName.equals(SocialNetworkEnum.NONE), "socNetName must not be NONE");
		Assert.notNull(uid, "uid must not be null");
		Assert.notNull(isImage, "isImage must not be null");
		
		User user = authenticationRepository.findUserBySocNetUID(socNetName, uid);
		
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
					
					authenticationRepository.save(user);
				}
			}
		}
		
		return user;
	}

	@Override
	public User registration(UserRegistration userRegistration, InputStream isImage) throws IOException, NoSuchAlgorithmException {
		Assert.notNull(userRegistration, "userRegistration must not be null");
		Assert.notNull(userRegistration.getEmail(), "email must not be null");
		Assert.isTrue(userRegistration.getEmail().matches(EMAIL_REGEX), "email is not correct");
		
		userRegistration.setProfile(new UserProfile());
		userRegistration.setSetting(new UserSetting());
		
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
		
		userRegistration.setStatus(AccountStatusEnum.NOTCONFIRMED);
		
		return authenticationRepository.save(userRegistration);
	}

	@Override
	public void hardRemoveUser(String userId) {
		Assert.notNull(userId, "userId must not be null");
		authenticationRepository.remove(userId);
	}

	@Override
	public void confirmRegistration(String email) {
		Assert.notNull(email, "email must not be null");
		
		User user = authenticationRepository.findByEmail(email);
		
		if (user == null) {
			String msg = String.format("user not find by email: %s", email);
			logger.debug(msg);			
			// TODO dikma: заменить на бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!user.getStatus().equals(AccountStatusEnum.NOTCONFIRMED)) {
			String msg = String.format("user status must be: %s", AccountStatusEnum.NOTCONFIRMED);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else {
			authenticationRepository.confirmRegistration(user.getId());
		}
	}

	@Override
	public void resetPassword(String email, Locale locale) throws MessagingException {
		Assert.notNull(email, "email must not be null");
		
		User user = authenticationRepository.findByEmail(email);
		
		if (user == null) {
			String msg = String.format("user not find by email: %s", email);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else {	
			MessageTemplate mt = messageTemplateStorage.getMessageTemplate(MessageEnum.RESET_PASSWORD);
			// TODO dikma: сгенерировать правильную ссылку, закодировать параметр base64
			String link = "http://heaptrip.com/?value=" + user.getId().hashCode();
			String msg = String.format(mt.getText(locale), link);
//			mailService.sendNoreplyMessage(email, mt.getSubject(locale), msg);
		}
	}

	@Override
	public void sendNewPassword(String email, String value, Locale locale) throws MessagingException {
		Assert.notNull(value, "value must not be null");
		Assert.notNull(email, "email must not be null");
		
		User user = authenticationRepository.findByEmail(email);
		
		if (user == null) {
			String msg = String.format("user not find by email: %s", email);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (user.getId().hashCode() == Integer.valueOf(value).intValue()) {
			MessageTemplate mt = messageTemplateStorage.getMessageTemplate(MessageEnum.SEND_NEW_PASSWORD);
//			mailService.sendNoreplyMessage(email, mt.getSubject(locale), mt.getText(locale));
		} else {
			String msg = String.format("value not correct, no password has been sent to email: %s", email);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		}
	}

	@Override
	public void changePassword(String userId, String currentPassword, String newPassword) {
		Assert.notNull(userId, "userId must not be null");
		Assert.notNull(newPassword, "password must not be null");
		Assert.isTrue(newPassword.length() > 8, "length password must be at least 8 characters and maximum length of 32");
		Assert.isTrue(newPassword.length() < 32, "length password must be at least 8 characters and maximum length of 32");
		Assert.isTrue(!newPassword.matches(PASSWORD_REGEX), "password must contains 0-9, lowercase characters a-z and uppercase characters A-Z");
		
		UserRegistration user = (UserRegistration) authenticationRepository.findOne(userId);
		
		if (user == null) {
			String msg = String.format("user not find by id %s", userId);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (user.getPassword() != null && !user.getPassword().equals(currentPassword)) {
			String msg = String.format("wrong current password");
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else {
			authenticationRepository.changePassword(userId, newPassword);
		}
	}

	@Override
	public void changeEmail(String userId, String currentEmail, String newEmail) {
		Assert.notNull(newEmail, "email must not be null");
		Assert.isTrue(newEmail.matches(EMAIL_REGEX), "email is not correct");
		
		User user = authenticationRepository.findOne(userId);
		
		if (user == null) {
			String msg = String.format("user not find by id %s", userId);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!user.getStatus().equals(AccountStatusEnum.ACTIVE)) {
			String msg = String.format("user status must be: %s", AccountStatusEnum.ACTIVE);
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else if (!user.getEmail().equals(currentEmail)) {
			String msg = String.format("current email not equals");
			logger.debug(msg);
			// TODO dikma: создать бизнес исключение
			throw new IllegalArgumentException(msg);
		} else {
			authenticationRepository.changeEmail(userId, newEmail);
		}
	}
}
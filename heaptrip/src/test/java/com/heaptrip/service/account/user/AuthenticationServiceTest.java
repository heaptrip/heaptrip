package com.heaptrip.service.account.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.heaptrip.domain.entity.account.AccountStatusEnum;
import com.heaptrip.domain.entity.account.user.SocialNetwork;
import com.heaptrip.domain.entity.account.user.User;
import com.heaptrip.domain.entity.account.user.UserRegistration;
import com.heaptrip.domain.exception.account.AccountException;
import com.heaptrip.domain.repository.account.user.UserRepository;
import com.heaptrip.domain.service.account.user.AuthenticationService;
import com.heaptrip.domain.service.account.user.UserService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class AuthenticationServiceTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	private ResourceLoader loader;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;

	@Test(enabled = true, priority = 1)
	public void getUserByEmail() {
		User user;
		
		user = authenticationService.getUserByEmailAndPassword(UserDataProvider.EMAIL_USER_EMAIL, UserDataProvider.EMAIL_USER_PSWD);
		Assert.assertNotNull(user);
		Assert.assertTrue(user.getStatus().equals(AccountStatusEnum.ACTIVE));
		
		user = authenticationService.getUserByEmailAndPassword(UserDataProvider.NOT_CONFIRMED_USER_EMAIL, UserDataProvider.NOT_CONFIRMED_USER_PSWD);
		Assert.assertNotNull(user);
		Assert.assertTrue(user.getStatus().equals(AccountStatusEnum.NOTCONFIRMED));
		
		user = authenticationService.getUserByEmailAndPassword(UserDataProvider.NET_USER_EMAIL, UserDataProvider.NET_USER_PSWD);
		Assert.assertNull(user);
		
		user = authenticationService.getUserByEmailAndPassword(UserDataProvider.FAKE_USER_EMAIL, UserDataProvider.FAKE_USER_PSWD);
		Assert.assertNull(user);
	}
	
	@Test(enabled = true, priority = 5)
	public void getUserBySocNetUID() throws IOException, NoSuchAlgorithmException {
		
		User user;
		SocialNetwork net;
		Byte[] imageCRC;
		
		Resource resource;
		File file;
		InputStream is;
		
		// load image 1
		resource = loader.getResource(UserDataProvider.IMAGE_1);
		Assert.assertNotNull(resource);
		file = resource.getFile();
		is = new FileInputStream(file);
		
		// fake user
		net = UserDataProvider.getFakeNet();
		Assert.assertNull(authenticationService.getUserBySocNetUID(net.getId(), net.getUid(), is));
		
		// load image 1
		resource = loader.getResource(UserDataProvider.IMAGE_1);
		Assert.assertNotNull(resource);
		file = resource.getFile();
		is = new FileInputStream(file);
		
		// net user
		net = UserDataProvider.getVK();
		user = authenticationService.getUserBySocNetUID(net.getId(), net.getUid(), is);
		Assert.assertNotNull(user);
		imageCRC = user.getImageCRC();
		
		// load image 2
		resource = loader.getResource(UserDataProvider.IMAGE_2);
		Assert.assertNotNull(resource);
		file = resource.getFile();
		is = new FileInputStream(file);
		
		// net user with new image
		user = authenticationService.getUserBySocNetUID(net.getId(), net.getUid(), is);
		Assert.assertFalse(user.getImageCRC().equals(imageCRC));
	}
	
	@Test(enabled = true, priority = 6, expectedExceptions = {AccountException.class, MessagingException.class})
	public void resetPasswordFakeUser() throws MessagingException {
		Locale locale = new Locale("ru");
		authenticationService.resetPassword(UserDataProvider.FAKE_USER_EMAIL, locale);
	}
	
	@Test(enabled = true, priority = 7, expectedExceptions = {AccountException.class, MessagingException.class})
	public void resetPasswordNotConfirmed() throws MessagingException {
		Locale locale = new Locale("ru");
		authenticationService.resetPassword(UserDataProvider.NOT_CONFIRMED_USER_EMAIL, locale);
	}
	
	@Test(enabled = true, priority = 8)
	public void resetPasswordEmailUser() throws MessagingException {
		Locale locale = new Locale("ru");
		authenticationService.resetPassword(UserDataProvider.EMAIL_USER_EMAIL, locale);
	}
	
	@Test(enabled = true, priority = 9, expectedExceptions = {AccountException.class, MessagingException.class})
	public void sendNewPasswordFakeUser() throws MessagingException {
		Locale locale = new Locale("ru");
		authenticationService.sendNewPassword(UserDataProvider.FAKE_USER_ID, String.valueOf(UserDataProvider.FAKE_USER_ID.hashCode()), locale);
	}
	
	@Test(enabled = true, priority = 10, expectedExceptions = {AccountException.class, MessagingException.class})
	public void sendNewPasswordNotConfirmed() throws MessagingException {
		Locale locale = new Locale("ru");
		authenticationService.sendNewPassword(UserDataProvider.NOT_CONFIRMED_USER_ID, String.valueOf(UserDataProvider.NOT_CONFIRMED_USER_ID.hashCode()), locale);
	}
	
	@Test(enabled = true, priority = 11, expectedExceptions = {AccountException.class, MessagingException.class})
	public void sendNewPasswordWrongValue() throws MessagingException {
		Locale locale = new Locale("ru");
		authenticationService.sendNewPassword(UserDataProvider.NOT_CONFIRMED_USER_ID, "1234567890", locale);
	}
	
	@Test(enabled = true, priority = 12)
	public void sendNewPassword() throws MessagingException {
		Locale locale = new Locale("ru");
		authenticationService.sendNewPassword(UserDataProvider.EMAIL_USER_ID, String.valueOf(UserDataProvider.EMAIL_USER_ID.hashCode()), locale);
	}
	
	@Test(enabled = true, priority = 13, expectedExceptions = {AccountException.class, MessagingException.class})
	public void changePasswordFakeUser() {
		userService.changePassword(UserDataProvider.FAKE_USER_ID, 
																UserDataProvider.FAKE_USER_PSWD, 
																UserDataProvider.FAKE_USER_PSWD);
	}
	
	@Test(enabled = true, priority = 14, expectedExceptions = {AccountException.class, MessagingException.class})
	public void changePasswordNotConfirmedUser() {
		userService.changePassword(UserDataProvider.NOT_CONFIRMED_USER_ID,
                                    UserDataProvider.NOT_CONFIRMED_USER_PSWD,
									UserDataProvider.NOT_CONFIRMED_USER_PSWD);
	}
	
	@Test(enabled = true, priority = 15, expectedExceptions = {AccountException.class, MessagingException.class})
	public void changePasswordEmptyNewPassword() {
		userService.changePassword(UserDataProvider.EMAIL_USER_ID, UserDataProvider.EMAIL_USER_PSWD, "");
	}
	
	@Test(enabled = true, priority = 16, expectedExceptions = {AccountException.class, MessagingException.class})
	public void changePasswordWorngCurrentPassword() {
		userService.changePassword(UserDataProvider.EMAIL_USER_ID, "", UserDataProvider.EMAIL_USER_PSWD_NEW);
	}
	
	@Test(enabled = true, priority = 17, expectedExceptions = {AccountException.class})
	public void changePasswordIncorrectNewPassword() {
		UserRegistration user = (UserRegistration) userRepository.findOne(UserDataProvider.EMAIL_USER_ID);
		
		userService.changePassword(UserDataProvider.EMAIL_USER_ID, 
									user.getPassword(), 
									UserDataProvider.INCORRECT_EMAIL);
	}
	
	@Test(enabled = true, priority = 18)
	public void changePassword() {
		UserRegistration user = (UserRegistration) userRepository.findOne(UserDataProvider.EMAIL_USER_ID);
		
		userService.changePassword(UserDataProvider.EMAIL_USER_ID, 
									user.getPassword(), 
									UserDataProvider.EMAIL_USER_PSWD_NEW);
	}
	
	@Test(enabled = true, priority = 19, expectedExceptions = {AccountException.class, MessagingException.class})
	public void changeEmailFakeUser() {
		userService.changeEmail(UserDataProvider.FAKE_USER_ID, 
																UserDataProvider.FAKE_USER_EMAIL, 
																UserDataProvider.FAKE_USER_EMAIL);
	}
	
	@Test(enabled = true, priority = 20, expectedExceptions = {AccountException.class, MessagingException.class})
	public void changeEmailNotConfirmed() {
		userService.changeEmail(UserDataProvider.NOT_CONFIRMED_USER_ID,
                                UserDataProvider.NOT_CONFIRMED_USER_EMAIL,
                                UserDataProvider.NOT_CONFIRMED_USER_EMAIL);
	}
	
	@Test(enabled = true, priority = 21, expectedExceptions = {AccountException.class})
	public void changeEmailIncorrectEmail() {
		userService.changeEmail(UserDataProvider.EMAIL_USER_ID, 
								UserDataProvider.EMAIL_USER_EMAIL, 
								UserDataProvider.INCORRECT_EMAIL);
	}
	
	@Test(enabled = true, priority = 22, expectedExceptions = {AccountException.class})
	public void changeEmailWrongEmail() {
		userService.changeEmail(UserDataProvider.EMAIL_USER_ID, 
								UserDataProvider.EMAIL_USER_EMAIL_NEW, 
								UserDataProvider.EMAIL_USER_EMAIL_NEW);
	}
	
	@Test(enabled = true, priority = 23)
	public void changeEmail() {
		userService.changeEmail(UserDataProvider.EMAIL_USER_ID, 
								UserDataProvider.EMAIL_USER_EMAIL, 
								UserDataProvider.EMAIL_USER_EMAIL_NEW);
	}
}

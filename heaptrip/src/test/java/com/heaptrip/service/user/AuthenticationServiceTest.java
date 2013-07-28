package com.heaptrip.service.user;

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
import com.heaptrip.domain.entity.user.SocialNetwork;
import com.heaptrip.domain.entity.user.User;
import com.heaptrip.domain.service.user.AuthenticationService;

@ContextConfiguration("classpath*:META-INF/spring/test-context.xml")
public class AuthenticationServiceTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	private ResourceLoader loader;
	
	@Autowired
	private AuthenticationService authenticationService;

	@Test(enabled = true, priority = 1)
	public void confirmRegistrationEmailUser() {
		authenticationService.confirmRegistration(InitUserTest.EMAIL_USER_EMAIL);
	}
	
	@Test(enabled = true, priority = 2)
	public void confirmRegistrationNetUser() {
		authenticationService.confirmRegistration(InitUserTest.NET_USER_EMAIL);
	}
	
	@Test(enabled = true, priority = 3, expectedExceptions = IllegalArgumentException.class)
	public void confirmRegistrationFakeUser() {
		authenticationService.confirmRegistration(InitUserTest.FAKE_USER_EMAIL);
	}
	
	@Test(enabled = true, priority = 4)
	public void getUserByEmail() {
		User user;
		
		user = authenticationService.getUserByEmail(InitUserTest.EMAIL_USER_EMAIL, InitUserTest.EMAIL_USER_PSWD);
		Assert.assertNotNull(user);
		Assert.assertTrue(user.getStatus().equals(AccountStatusEnum.ACTIVE));
		
		user = authenticationService.getUserByEmail(InitUserTest.NOTCONFIRMED_USER_EMAIL, InitUserTest.NOTCONFIRMED_USER_PSWD);
		Assert.assertNotNull(user);
		Assert.assertTrue(user.getStatus().equals(AccountStatusEnum.NOTCONFIRMED));
		
		user = authenticationService.getUserByEmail(InitUserTest.NET_USER_EMAIL, InitUserTest.NET_USER_PSWD);
		Assert.assertNull(user);
		
		user = authenticationService.getUserByEmail(InitUserTest.FAKE_USER_EMAIL, InitUserTest.FAKE_USER_PSWD);
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
		resource = loader.getResource(InitUserTest.IMAGE_1);
		Assert.assertNotNull(resource);
		file = resource.getFile();
		is = new FileInputStream(file);
		
		// fake user
		net = InitUserTest.getFakeNet();
		Assert.assertNull(authenticationService.getUserBySocNetUID(net.getId(), net.getUid(), is));
		
		// load image 1
		resource = loader.getResource(InitUserTest.IMAGE_1);
		Assert.assertNotNull(resource);
		file = resource.getFile();
		is = new FileInputStream(file);
		
		// net user
		net = InitUserTest.getVK();
		user = authenticationService.getUserBySocNetUID(net.getId(), net.getUid(), is);
		Assert.assertNotNull(user);
		imageCRC = user.getImageCRC();
		
		// load image 2
		resource = loader.getResource(InitUserTest.IMAGE_2);
		Assert.assertNotNull(resource);
		file = resource.getFile();
		is = new FileInputStream(file);
		
		// net user with new image
		user = authenticationService.getUserBySocNetUID(net.getId(), net.getUid(), is);
		Assert.assertFalse(user.getImageCRC().equals(imageCRC));
	}
	
	@Test(enabled = true, priority = 6, expectedExceptions = RuntimeException.class)
	public void resetPasswordFakeUser() throws MessagingException {
		Locale locale = new Locale("ru");
		authenticationService.resetPassword(InitUserTest.FAKE_USER_EMAIL, locale);
	}
	
	@Test(enabled = true, priority = 7, expectedExceptions = RuntimeException.class)
	public void resetPasswordNotConfirmed() throws MessagingException {
		Locale locale = new Locale("ru");
		authenticationService.resetPassword(InitUserTest.NOTCONFIRMED_USER_EMAIL, locale);
	}
	
	@Test(enabled = true, priority = 8)
	public void resetPasswordEmailUser() throws MessagingException {
		Locale locale = new Locale("ru");
		authenticationService.resetPassword(InitUserTest.EMAIL_USER_EMAIL, locale);
	}
	
	@Test(enabled = true, priority = 9, expectedExceptions = RuntimeException.class)
	public void sendNewPasswordFakeUser() throws MessagingException {
		Locale locale = new Locale("ru");
		authenticationService.sendNewPassword(InitUserTest.FAKE_USER_EMAIL, String.valueOf(InitUserTest.FAKE_USER_ID.hashCode()), locale);
	}
	
	@Test(enabled = true, priority = 10, expectedExceptions = RuntimeException.class)
	public void sendNewPasswordNotConfirmed() throws MessagingException {
		Locale locale = new Locale("ru");
		authenticationService.sendNewPassword(InitUserTest.NOTCONFIRMED_USER_EMAIL, String.valueOf(InitUserTest.NOTCONFIRMED_USER_ID.hashCode()), locale);
	}
	
	@Test(enabled = true, priority = 11, expectedExceptions = RuntimeException.class)
	public void sendNewPasswordWrongValue() throws MessagingException {
		Locale locale = new Locale("ru");
		authenticationService.sendNewPassword(InitUserTest.EMAIL_USER_EMAIL, String.valueOf(InitUserTest.NOTCONFIRMED_USER_ID.hashCode()), locale);
	}
	
	@Test(enabled = true, priority = 12)
	public void sendNewPassword() throws MessagingException {
		Locale locale = new Locale("ru");
		authenticationService.sendNewPassword(InitUserTest.EMAIL_USER_EMAIL, String.valueOf(InitUserTest.EMAIL_USER_ID.hashCode()), locale);
	}
	
	@Test(enabled = true, priority = 13, expectedExceptions = RuntimeException.class)
	public void changePasswordFakeUser() {
		authenticationService.changePassword(InitUserTest.FAKE_USER_ID, 
																InitUserTest.FAKE_USER_PSWD, 
																InitUserTest.FAKE_USER_PSWD);
	}
	
	@Test(enabled = true, priority = 14, expectedExceptions = RuntimeException.class)
	public void changePasswordNotConfirmedUser() {
		authenticationService.changePassword(InitUserTest.NOTCONFIRMED_USER_ID, 
																InitUserTest.NOTCONFIRMED_USER_PSWD, 
																InitUserTest.NOTCONFIRMED_USER_PSWD);
	}
	
	@Test(enabled = true, priority = 15, expectedExceptions = RuntimeException.class)
	public void changePasswordEmptyNewPassword() {
		authenticationService.changePassword(InitUserTest.EMAIL_USER_ID, 
																InitUserTest.EMAIL_USER_PSWD, 
																"");
	}
	
	@Test(enabled = true, priority = 16, expectedExceptions = RuntimeException.class)
	public void changePasswordWorngCurrentPassword() {
		authenticationService.changePassword(InitUserTest.EMAIL_USER_ID, 
																"", 
																InitUserTest.EMAIL_USER_PSWD_NEW);
	}
	
	@Test(enabled = true, priority = 17)
	public void changePassword() {
		authenticationService.changePassword(InitUserTest.EMAIL_USER_ID, 
																InitUserTest.EMAIL_USER_PSWD, 
																InitUserTest.EMAIL_USER_PSWD_NEW);
	}
	
	@Test(enabled = true, priority = 18, expectedExceptions = RuntimeException.class)
	public void changeEmailFakeUser() {
		authenticationService.changeEmail(InitUserTest.FAKE_USER_ID, 
																InitUserTest.FAKE_USER_EMAIL, 
																InitUserTest.FAKE_USER_EMAIL);
	}
	
	@Test(enabled = true, priority = 19, expectedExceptions = RuntimeException.class)
	public void changeEmailNotConfirmed() {
		authenticationService.changeEmail(InitUserTest.NOTCONFIRMED_USER_ID, 
																InitUserTest.NOTCONFIRMED_USER_EMAIL, 
																InitUserTest.NOTCONFIRMED_USER_EMAIL);
	}
	
	@Test(enabled = true, priority = 20, expectedExceptions = RuntimeException.class)
	public void changeEmailIncorrectEmail() {
		authenticationService.changeEmail(InitUserTest.EMAIL_USER_ID, 
											InitUserTest.EMAIL_USER_EMAIL, 
											InitUserTest.INCORRECT_EMAIL);
	}
	
	@Test(enabled = true, priority = 21, expectedExceptions = RuntimeException.class)
	public void changeEmailWrongEmail() {
		authenticationService.changeEmail(InitUserTest.EMAIL_USER_ID, 
																InitUserTest.EMAIL_USER_EMAIL_NEW, 
																InitUserTest.EMAIL_USER_EMAIL_NEW);
	}
	
	@Test(enabled = true, priority = 22)
	public void changeEmail() {
		authenticationService.changeEmail(InitUserTest.EMAIL_USER_ID, 
																InitUserTest.EMAIL_USER_EMAIL, 
																InitUserTest.EMAIL_USER_EMAIL_NEW);
	}
}
